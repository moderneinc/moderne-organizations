package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;
import org.openrewrite.internal.StringUtils;

public record OrganizationRepository(String path, String origin, String branch) {

  public OrganizationRepository(String path, String origin, String branch) {
    this.origin = origin;
    this.path = path;
    this.branch = branch;
    if (origin.equals("*") && path.equals("*") && branch.equals("*")) {
      throw new IllegalArgumentException("Cannot have a repository with origin=* and branch=* and path=* as this would match all repositories");
    }
  }

  public boolean matches(RepositoryInput repositoryInput) {
    return StringUtils.matchesGlob(repositoryInput.getOrigin().toLowerCase(), origin.toLowerCase()) &&
                            StringUtils.matchesGlob(repositoryInput.getPath().toLowerCase(), path.toLowerCase()) &&
                            StringUtils.matchesGlob(repositoryInput.getBranch().toLowerCase(), branch.toLowerCase());
  }
}
