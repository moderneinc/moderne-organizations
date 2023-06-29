package io.moderne.orgs;

import io.moderne.orgs.types.CommitOption;
import io.moderne.orgs.types.RepositoryInput;
import org.openrewrite.internal.lang.Nullable;

import java.util.List;

/**
 * @param name         The name of the organization
 * @param repositories The set of repositories that this organization owns.
 */
public record OrganizationRepositories(String name, List<OrganizationRepository> repositories,
                                       @Nullable List<CommitOption> commitOptions) {

    boolean matches(RepositoryInput toMatchRepositoryInput) {
        return repositories.stream().anyMatch(repository -> repository.matches(toMatchRepositoryInput));
    }
}
