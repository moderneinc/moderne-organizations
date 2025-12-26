package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class OrganizationEdge {
  private Organization node;

  private String cursor;

  public OrganizationEdge() {
  }

  public OrganizationEdge(Organization node, String cursor) {
    this.node = node;
    this.cursor = cursor;
  }

  public Organization getNode() {
    return node;
  }

  public void setNode(Organization node) {
    this.node = node;
  }

  public String getCursor() {
    return cursor;
  }

  public void setCursor(String cursor) {
    this.cursor = cursor;
  }

  @Override
  public String toString() {
    return "OrganizationEdge{" + "node='" + node + "'," +"cursor='" + cursor + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        OrganizationEdge that = (OrganizationEdge) o;
        return java.util.Objects.equals(node, that.node) &&
                            java.util.Objects.equals(cursor, that.cursor);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(node, cursor);
  }

  public static io.moderne.organizations.types.OrganizationEdge.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private Organization node;

    private String cursor;

    public OrganizationEdge build() {
                  io.moderne.organizations.types.OrganizationEdge result = new io.moderne.organizations.types.OrganizationEdge();
                      result.node = this.node;
          result.cursor = this.cursor;
                      return result;
    }

    public io.moderne.organizations.types.OrganizationEdge.Builder node(Organization node) {
      this.node = node;
      return this;
    }

    public io.moderne.organizations.types.OrganizationEdge.Builder cursor(String cursor) {
      this.cursor = cursor;
      return this;
    }
  }
}
