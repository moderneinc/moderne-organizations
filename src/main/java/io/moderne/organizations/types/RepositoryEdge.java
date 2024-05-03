package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class RepositoryEdge {
  private Repository node;

  private String cursor;

  public RepositoryEdge() {
  }

  public RepositoryEdge(Repository node, String cursor) {
    this.node = node;
    this.cursor = cursor;
  }

  public Repository getNode() {
    return node;
  }

  public void setNode(Repository node) {
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
    return "RepositoryEdge{" + "node='" + node + "'," +"cursor='" + cursor + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryEdge that = (RepositoryEdge) o;
        return java.util.Objects.equals(node, that.node) &&
                            java.util.Objects.equals(cursor, that.cursor);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(node, cursor);
  }

  public static io.moderne.organizations.types.RepositoryEdge.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private Repository node;

    private String cursor;

    public RepositoryEdge build() {
                  io.moderne.organizations.types.RepositoryEdge result = new io.moderne.organizations.types.RepositoryEdge();
                      result.node = this.node;
          result.cursor = this.cursor;
                      return result;
    }

    public io.moderne.organizations.types.RepositoryEdge.Builder node(Repository node) {
      this.node = node;
      return this;
    }

    public io.moderne.organizations.types.RepositoryEdge.Builder cursor(String cursor) {
      this.cursor = cursor;
      return this;
    }
  }
}
