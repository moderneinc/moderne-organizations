package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.RepositoryInput;
import org.openrewrite.GitRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This service could also directly call your SCM api to determine the available repositories
 */
@Service
public class OrganizationStructureService {
    private static final String REPOS_CSV = "repos.csv";
    private static final String NAME_MAPPING = "id-mapping.txt";
    private static final Logger log = LoggerFactory.getLogger(OrganizationStructureService.class.getName());

    private final ScmConfiguration scmConfiguration;
    private final GitRemote.Parser gitRemoteParser;

    OrganizationStructureService(ScmConfiguration scmConfiguration, GitRemote.Parser gitRemoteParser) {
        this.scmConfiguration = scmConfiguration;
        this.gitRemoteParser = gitRemoteParser;
    }

    public Map<String, OrganizationRepositories> readOrganizationStructure() {
        LinkedHashMap<String, OrganizationRepositories> organizations = new LinkedHashMap<>();
        Set<RepositoryInput> allRepositories = new LinkedHashSet<>();

        final Map<String, String> idToNameMapping = readIdToNameMapping();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(REPOS_CSV).getInputStream()))) {
            reader.readLine(); // skip header
            reader.lines()
                    .forEach(line -> {
                        String[] fields = line.split(",", Integer.MAX_VALUE);
                        if (fields.length < 3) {
                            throw new IllegalStateException("repos.csv lines should have at least 1 organization");
                        }
                        String cloneUrl = fields[0].trim();
                        String branch = fields[1].trim();
                        RepositoryInput repository = determineRepository(cloneUrl, branch);
                        if (repository == null) {
                            if (scmConfiguration.isAllowMissingScmOrigins()) {
                                log.warn("No scm origin found for %s. Consider adding it to scm-origins.txt".formatted(cloneUrl));
                                return;
                            } else {
                                throw new IllegalStateException("No scm origin found for %s. Add it to moderne.scm.repositories or set moderne.scm.allow-missing-scm-origins to true in application.yaml".formatted(cloneUrl));
                            }
                        }
                        allRepositories.add(repository);

                        OrganizationRepositories first = null;
                        for (int i = fields.length - 1; i > 1; i--) {
                            String organization = fields[i].trim();
                            if (organization.isEmpty()) {
                                break;
                            }
                            String parent = fields.length > i + 1 ? fields[i + 1].trim() : null;
                            first = organizations.compute(organization, (k, v) -> {
                                if (v == null) {
                                    String name = organization;
                                    if (idToNameMapping.containsKey(organization)) {
                                        name = idToNameMapping.get(organization);
                                    }
                                    v = new OrganizationRepositories(
                                            organization,
                                            name,
                                            new LinkedHashSet<>(),
                                            List.of(CommitOption.values()), parent);
                                }
                                if (!Objects.equals(v.parent(), parent)) {
                                    throw new IllegalStateException("An organization parent must be the same for each repository. %s has parent %s and %s".formatted(organization, v.parent(), parent));
                                }
                                return v;
                            });
                        }
                        if (first != null) {
                            first.repositories().add(repository);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        organizations.put("ALL", new OrganizationRepositories("ALL", "ALL", allRepositories, List.of(CommitOption.values()), null));
        logStructure(organizations);
        return organizations;
    }

    private static Map<String, String> readIdToNameMapping() {
        Map<String, String> idToNameMapping = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(NAME_MAPPING).getInputStream()))) {
            reader.lines()
                    .forEach(line -> {
                        String[] fields = line.split("=", 2);
                        if (fields.length != 2) {
                            throw new IllegalStateException("id-mapping.txt lines should have exactly one =");
                        }
                        idToNameMapping.put(fields[0].trim(), fields[1].trim());
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return idToNameMapping;
    }

    void logStructure(Map<String, OrganizationRepositories> organizations) {
        Map<String, List<OrganizationRepositories>> tree = new HashMap<>();

        for (OrganizationRepositories org : organizations.values()) {
            if (!tree.containsKey(org.parent())) {
                tree.put(org.parent(), new ArrayList<>());
            }
            tree.get(org.parent()).add(org);
        }
        if (!log.isDebugEnabled())
            return;
        log.debug("Organization structure: name [repositories]");
        printTree(tree, null, 0);
    }

    public static void printTree(Map<String, List<OrganizationRepositories>> tree, String parentName, int level) {
        for (OrganizationRepositories org : tree.getOrDefault(parentName, Collections.emptyList())) {
            String indent = "\t".repeat(level);
            log.debug("{}{} [{}]", indent, org.name(), org.repositories().size());
            printTree(tree, org.name(), level + 1);
        }
    }

    RepositoryInput determineRepository(String cloneUrl, String branch) {
        GitRemote gitRemote = gitRemoteParser.parse(cloneUrl);
        return new RepositoryInput(gitRemote.getOrigin(), gitRemote.getPath(), branch);
    }
}
