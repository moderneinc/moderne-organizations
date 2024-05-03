package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.RepositoryInput;
import org.openrewrite.internal.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @param name         The name of the organization
 * @param repositories The set of repositories that this organization owns.
 */
public record OrganizationRepositories(String name,
                                       Set<RepositoryInput> repositories,
                                       @Nullable List<CommitOption> commitOptions,
                                       @Nullable String parent) {
    public boolean contains(RepositoryInput repository) {
        return repositories != null && repositories.contains(repository);
    }
}
