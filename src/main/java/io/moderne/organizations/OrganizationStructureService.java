package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.RepositoryInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This service could also directly call your SCM api to determine the available repositories
 */
@Service
public class OrganizationStructureService {
    private static final Pattern GITHUB_PATTERN = Pattern.compile("github.com/(.*)");
    private static final String REPOS_CSV = "repos.csv";
    private static final Logger log = LoggerFactory.getLogger(OrganizationStructureService.class.getName());

    public Map<String, OrganizationRepositories> readOrganizationStructure() {
        LinkedHashMap<String, OrganizationRepositories> organizations = new LinkedHashMap<>();
        Set<RepositoryInput> allRepositories = new LinkedHashSet<>();

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

                        Matcher matcher = GITHUB_PATTERN.matcher(cloneUrl);
                        if (!matcher.find()) {
                            return;
                        }
                        String origin = "github.com";
                        String path = matcher.group(1);
                        RepositoryInput repository = new RepositoryInput(path, origin, branch);
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
                                    v = new OrganizationRepositories(organization, new LinkedHashSet<>(), List.of(CommitOption.values()), parent);
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
        organizations.put("ALL", new OrganizationRepositories("ALL", allRepositories, List.of(CommitOption.values()), null));
        logStructure(organizations);
        return organizations;
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
}
