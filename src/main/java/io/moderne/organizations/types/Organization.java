package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Organization {
  private String id;

  private String name;

  public Organization() {
  }

  public Organization(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Organization{" + "id='" + id + "'," +"name='" + name + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, name);
  }

  public static io.moderne.organizations.types.Organization.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private String name;

    public Organization build() {
                  io.moderne.organizations.types.Organization result = new io.moderne.organizations.types.Organization();
                      result.id = this.id;
          result.name = this.name;
                      return result;
    }

    public io.moderne.organizations.types.Organization.Builder id(String id) {
      this.id = id;
      return this;
    }

    public io.moderne.organizations.types.Organization.Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
