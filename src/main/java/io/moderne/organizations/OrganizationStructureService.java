package io.moderne.organizations;

import org.jspecify.annotations.Nullable;
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

    @Nullable
    private final Path reposCsvPath;

    public OrganizationStructureService(ModerneConfiguration moderneConfiguration) {
        this.reposCsvPath = moderneConfiguration.getReposCsvPath();
    }

    OrganizationTree readOrganizationStructure() {
        Organization<?> parsedOrganization = new OrganizationReader().fromCsv(loadReposCsvInputStream());
        return new OrganizationTree(findAllOrganizations(parsedOrganization));
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

    private Collection<Organization<?>> findAllOrganizations(Organization<?> organization) {
        Collection<Organization<?>> organizations = new ArrayList<>();
        if(!organization.isRoot()) {
            organizations.add(organization);
        }
        for (Organization<?> child : organization.getChildren()) {
            organizations.addAll(findAllOrganizations(child));
        }
        return organizations;
    }
}
