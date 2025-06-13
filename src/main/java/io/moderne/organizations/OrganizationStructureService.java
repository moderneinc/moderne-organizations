package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
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
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * This service could also directly call your SCM api to determine the available repositories
 */
@Service
public class OrganizationStructureService {
    private static final String DEFAULT_REPOS_CSV = "repos.csv";
    private static final Logger log = LoggerFactory.getLogger(OrganizationStructureService.class.getName());

    private final GitRemote.Parser gitRemoteParser;

    @Nullable
    private final Path reposCsvPath;

    public OrganizationStructureService(ModerneConfiguration moderneConfiguration, GitRemote.Parser gitRemoteParser) {
        this.gitRemoteParser = gitRemoteParser;
        this.reposCsvPath = moderneConfiguration.getReposCsvPath();
    }

    OrganizationTree readOrganizationStructure() {
        LinkedHashMap<String, OrganizationRepositories> organizations = new LinkedHashMap<>();

        Organization<Object> parsedOrganization = new OrganizationReader().fromCsv(loadReposCsvInputStream());
        for (Organization<Object> child : parsedOrganization.getChildren()) {
            determineRepositoriesForOrganization(child, organizations, null);
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

    public static void printTree(Map<String, List<OrganizationRepositories>> tree, @Nullable String parentName, int level) {
        for (OrganizationRepositories org : tree.getOrDefault(parentName, Collections.emptyList())) {
            String indent = "\t".repeat(level);
            log.debug("{}{} [{}]", indent, org.name(), org.repositories().size());
            printTree(tree, org.name(), level + 1);
        }
    }

    private void determineRepositoriesForOrganization(
            Organization<Object> organization,
            Map<String, OrganizationRepositories> organizations,
            @Nullable String parentId
        ) {
        organizations.put(organization.getName(), new OrganizationRepositories(
                organization.getId(),
                organization.getName(),
                mapRepositories(organization.getRepositories()),
                List.of(CommitOption.values()),
                requireNonNull(organization.getParent()).isRoot() ? null : organization.getParent().getId()
        ));
        for (Organization<Object> child : organization.getChildren()) {
            determineRepositoriesForOrganization(child, organizations, organization.getName());
        }
    }

    private Set<RepositoryInput> mapRepositories(NavigableSet<? extends RepositorySpec<?>> repositories) {
        return repositories.stream().map(this::mapRepository).collect(Collectors.toUnmodifiableSet());
    }

    private RepositoryInput mapRepository(RepositorySpec<?> r) {
        GitRemote gitRemote = gitRemoteParser.parse(r.getCloneUri());
        return new RepositoryInput(
                gitRemote.getPath(),
                gitRemote.getOrigin(),
                r.getId().getBranch()
        );
    }
}
