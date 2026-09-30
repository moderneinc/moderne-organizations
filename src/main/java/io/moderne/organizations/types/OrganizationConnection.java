package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class OrganizationConnection {
  private List<OrganizationEdge> edges;

  private PageInfo pageInfo;

  public OrganizationConnection() {
  }

  public OrganizationConnection(List<OrganizationEdge> edges, PageInfo pageInfo) {
    this.edges = edges;
    this.pageInfo = pageInfo;
  }

  public List<OrganizationEdge> getEdges() {
    return edges;
  }

  public void setEdges(List<OrganizationEdge> edges) {
    this.edges = edges;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  @Override
  public String toString() {
    return "OrganizationConnection{" + "edges='" + edges + "'," +"pageInfo='" + pageInfo + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationConnection that = (OrganizationConnection) o;
        return java.util.Objects.equals(edges, that.edges) &&
                            java.util.Objects.equals(pageInfo, that.pageInfo);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(edges, pageInfo);
  }

  public static io.moderne.organizations.types.OrganizationConnection.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private List<OrganizationEdge> edges;

    private PageInfo pageInfo;

    public OrganizationConnection build() {
                  io.moderne.organizations.types.OrganizationConnection result = new io.moderne.organizations.types.OrganizationConnection();
                      result.edges = this.edges;
          result.pageInfo = this.pageInfo;
                      return result;
    }

    public io.moderne.organizations.types.OrganizationConnection.Builder edges(
        List<OrganizationEdge> edges) {
      this.edges = edges;
      return this;
    }

    public io.moderne.organizations.types.OrganizationConnection.Builder pageInfo(
        PageInfo pageInfo) {
      this.pageInfo = pageInfo;
      return this;
    }
  }
}
