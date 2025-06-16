package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Organization {
  private String id;

  public Organization() {
  }

  public Organization(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Organization{" + "id='" + id +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()){
      return false;
    }
    Organization that = (Organization) o;
    return java.util.Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id);
  }

  public static io.moderne.organizations.types.Organization.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    public Organization build() {
                  io.moderne.organizations.types.Organization result = new io.moderne.organizations.types.Organization();
                      result.id = this.id;
                      return result;
    }

    public io.moderne.organizations.types.Organization.Builder id(String id) {
      this.id = id;
      return this;
    }
  }
}
