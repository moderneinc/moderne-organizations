package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;

import java.util.List;

/**
 * @param name         The name of the organization
 * @param repositories The set of repositories that this organization owns.
 */
public record OrganizationRepositories(String name, List<OrganizationRepository> repositories) {

    boolean matches(RepositoryInput toMatchRepositoryInput) {
        return repositories.stream().anyMatch(repository -> repository.matches(toMatchRepositoryInput));
    }
}
