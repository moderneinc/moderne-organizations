package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.RepositoryInput;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @param name         The name of the organization
 * @param repositories The set of repositories that this organization owns.
 */
public record OrganizationRepositories(String id,
                                       String name,
                                       Set<RepositoryInput> repositories,
                                       @Nullable List<CommitOption> commitOptions,
                                       @Nullable String parent) {
}
