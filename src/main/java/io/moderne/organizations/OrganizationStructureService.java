package io.moderne.organizations;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.moderne.organizations.types.RepositoryInput;
import org.jspecify.annotations.Nullable;
import org.openrewrite.GitRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * This service could also directly call your SCM api to determine the available repositories
 */
@Service
public class OrganizationStructureService {
    private static final String DEFAULT_REPOS_CSV = "repos.csv";
    private static final String NAME_MAPPING = "id-mapping.txt";
    private static final String COMMIT_OPTIONS = "commitOptions.txt";
    private static final Logger log = LoggerFactory.getLogger(OrganizationStructureService.class.getName());

    private final ScmConfiguration scmConfiguration;
    private final GitRemote.Parser gitRemoteParser;

    @Nullable
    private final Path reposCsvPath;

    public OrganizationStructureService(ModerneConfiguration moderneConfiguration, GitRemote.Parser gitRemoteParser) {
        this.gitRemoteParser = gitRemoteParser;
        this.scmConfiguration = moderneConfiguration.getScm();
        this.reposCsvPath = moderneConfiguration.getReposCsvPath();
    }

    OrganizationTree readOrganizationStructure() {
        LinkedHashMap<String, OrganizationRepositories> organizations = new LinkedHashMap<>();
        Set<RepositoryInput> allRepositories = new LinkedHashSet<>();

        final Map<String, String> idToNameMapping = readIdToNameMapping();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema bootstrapSchema = CsvSchema.emptySchema()
                .withComments()
                .withHeader();

        try (InputStream reposCsvInputStream = loadReposCsvInputStream();
             InputStream commitOptionsInputStream = loadCommitOptionsInputStream();
             MappingIterator<ReposCsvRow> iterator = csvMapper.readerFor(ReposCsvRow.class)
                     .with(bootstrapSchema)
                     .readValues(reposCsvInputStream)) {
            CommitOptions commitOptions = CommitOptions.fromInputStream(commitOptionsInputStream);
            while (iterator.hasNextValue()) {
                ReposCsvRow row = iterator.nextValue();
                String cloneUrl = row.getCloneUrl();
                RepositoryInput repository = determineRepository(cloneUrl, row.getBranch());
                if (repository == null) {
                    if (scmConfiguration.isAllowMissingScmOrigins()) {
                        log.warn("No scm origin found for %s. Consider adding it to scm-origins.txt".formatted(cloneUrl));
                        continue;
                    } else {
                        throw new IllegalStateException("No scm origin found for %s. Add it to moderne.scm.repositories or set moderne.scm.allow-missing-scm-origins to true in application.yaml".formatted(cloneUrl));
                    }
                }

                Queue<String> orgNames = new LinkedList<>(row.getOrganizations());
                if (orgNames.isEmpty()) {
                    throw new IllegalStateException("repos.csv lines should have at least 1 organization");
                } else if (orgNames.contains("")) {
                    throw new IllegalStateException("Invalid organization \"\" for %s. \nCheck your repos.csv for extra commas, e.g. ,,,ALL or lines ending in ,".formatted(cloneUrl));
                }

                boolean first = true;
                do {
                    String organization = orgNames.poll();
                    String parent = orgNames.peek();
                    OrganizationRepositories organizationRepositories = organizations.compute(organization, (k, v) -> {
                        String name = organization;
                        if (idToNameMapping.containsKey(organization)) {
                            name = idToNameMapping.get(organization);
                        }
                        if (v == null) {
                            v = new OrganizationRepositories(organization, name, new LinkedHashSet<>(), commitOptions.find(organization), parent);
                        }
                        if (!Objects.equals(v.parent(), parent)) {
                            throw new IllegalStateException("An organization parent must be the same for each repository. %s has parent %s and %s".formatted(organization, v.parent(), parent));
                        }

                        return v;
                    });
                    if (first) {
                        first = false;
                        organizationRepositories.repositories().add(repository);
                    }
                } while (!orgNames.isEmpty());
            }
            organizations.put("ALL", new OrganizationRepositories("ALL", "ALL", allRepositories, commitOptions.find("ALL"), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logStructure(organizations);
        return new OrganizationTree(organizations.values());
    }

    public InputStream loadReposCsvInputStream() {
        try {
            if (reposCsvPath != null && reposCsvPath.toFile().exists()) {
                return new FileInputStream(reposCsvPath.toFile());
            } else {
                return new ClassPathResource(reposCsvPath != null ? reposCsvPath.toString() : DEFAULT_REPOS_CSV).getInputStream();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static InputStream loadNameMappingInputStream() {
        try {
            return new ClassPathResource(NAME_MAPPING).getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public InputStream loadCommitOptionsInputStream() {
        try {
            return new ClassPathResource(COMMIT_OPTIONS).getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Map<String, String> readIdToNameMapping() {
        Map<String, String> idToNameMapping = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(loadNameMappingInputStream()))) {
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
        return new RepositoryInput(gitRemote.getPath(), gitRemote.getOrigin(), branch);
    }
}
