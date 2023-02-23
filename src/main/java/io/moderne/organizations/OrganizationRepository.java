package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;

public record OrganizationRepository(String path, String origin, String branch) {

    public OrganizationRepository(String path, String origin, String branch) {
        this.origin = origin;
        this.path = path;
        this.branch = branch;
        if (origin.equals("*") && path.equals("*") && branch.equals("*"))
            throw new IllegalArgumentException(
                    "Cannot have a repository with origin=* and branch=* and path=* as this would match all repositories"
            );
    }

    public boolean matches(RepositoryInput repositoryInput) {
        return asRepositoryInput().equals(repositoryInput) ||
                (origin.equals("*") || origin.equals(repositoryInput.getOrigin())) &&
                        pathMatches(repositoryInput.getPath()) &&
                        (branch.equals("*") || branch.equals(repositoryInput.getBranch()));
    }

    private boolean pathMatches(String path) {
        if (this.path.equals("*") || this.path.equals(path)) {
            return true;
        }
        if (this.path.endsWith("*")) {
            return path.startsWith(this.path.substring(0, this.path.length() - 1));
        }
        return false;
    }

    RepositoryInput asRepositoryInput() {
        return new RepositoryInput(path, origin, branch);
    }
}
