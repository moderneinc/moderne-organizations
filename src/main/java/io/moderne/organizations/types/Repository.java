package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Repository {
  private String path;

  private String origin;

  private String branch;

  public Repository() {
  }

  public Repository(String path, String origin, String branch) {
    this.path = path;
    this.origin = origin;
    this.branch = branch;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  @Override
  public String toString() {
    return "Repository{" + "path='" + path + "'," +"origin='" + origin + "'," +"branch='" + branch + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        Repository that = (Repository) o;
        return java.util.Objects.equals(path, that.path) &&
                            java.util.Objects.equals(origin, that.origin) &&
                            java.util.Objects.equals(branch, that.branch);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(path, origin, branch);
  }

  public static io.moderne.organizations.types.Repository.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String path;

    private String origin;

    private String branch;

    public Repository build() {
                  io.moderne.organizations.types.Repository result = new io.moderne.organizations.types.Repository();
                      result.path = this.path;
          result.origin = this.origin;
          result.branch = this.branch;
                      return result;
    }

    public io.moderne.organizations.types.Repository.Builder path(String path) {
      this.path = path;
      return this;
    }

    public io.moderne.organizations.types.Repository.Builder origin(String origin) {
      this.origin = origin;
      return this;
    }

    public io.moderne.organizations.types.Repository.Builder branch(String branch) {
      this.branch = branch;
      return this;
    }
  }
}
