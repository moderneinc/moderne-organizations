package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.DevCenter;
import io.moderne.organizations.types.RepositoryInput;
import org.openrewrite.internal.lang.Nullable;

import java.util.List;

/**
 * @param name         The name of the organization
 * @param repositories The set of repositories that this organization owns.
 */
public record OrganizationRepositories(String name, List<OrganizationRepository> repositories,
                                       DevCenter devCenter,
                                       @Nullable List<CommitOption> commitOptions,
                                       @Nullable String parent) {
    boolean matches(RepositoryInput toMatchRepositoryInput) {
        return repositories != null && repositories.stream().anyMatch(repository -> repository.matches(toMatchRepositoryInput));
    }
}
