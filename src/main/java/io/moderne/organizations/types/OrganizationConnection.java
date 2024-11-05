package io.moderne.organizations.types;

import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class OrganizationConnection {
  private List<OrganizationEdge> edges;

  private PageInfo pageInfo;

  private Integer count;

  public OrganizationConnection() {
  }

  public OrganizationConnection(List<OrganizationEdge> edges, PageInfo pageInfo, Integer count) {
    this.edges = edges;
    this.pageInfo = pageInfo;
    this.count = count;
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

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "OrganizationConnection{" + "edges='" + edges + "'," +"pageInfo='" + pageInfo + "'," +"count='" + count + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationConnection that = (OrganizationConnection) o;
        return java.util.Objects.equals(edges, that.edges) &&
                            java.util.Objects.equals(pageInfo, that.pageInfo) &&
                            java.util.Objects.equals(count, that.count);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(edges, pageInfo, count);
  }

  public static io.moderne.organizations.types.OrganizationConnection.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private List<OrganizationEdge> edges;

    private PageInfo pageInfo;

    private Integer count;

    public OrganizationConnection build() {
                  io.moderne.organizations.types.OrganizationConnection result = new io.moderne.organizations.types.OrganizationConnection();
                      result.edges = this.edges;
          result.pageInfo = this.pageInfo;
          result.count = this.count;
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

    public io.moderne.organizations.types.OrganizationConnection.Builder count(Integer count) {
      this.count = count;
      return this;
    }
  }
}
