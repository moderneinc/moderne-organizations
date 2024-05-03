package io.moderne.organizations.types;

import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class RepositoryConnection {
  private List<RepositoryEdge> edges;

  private PageInfo pageInfo;

  private Integer count;

  public RepositoryConnection() {
  }

  public RepositoryConnection(List<RepositoryEdge> edges, PageInfo pageInfo, Integer count) {
    this.edges = edges;
    this.pageInfo = pageInfo;
    this.count = count;
  }

  public List<RepositoryEdge> getEdges() {
    return edges;
  }

  public void setEdges(List<RepositoryEdge> edges) {
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
    return "RepositoryConnection{" + "edges='" + edges + "'," +"pageInfo='" + pageInfo + "'," +"count='" + count + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryConnection that = (RepositoryConnection) o;
        return java.util.Objects.equals(edges, that.edges) &&
                            java.util.Objects.equals(pageInfo, that.pageInfo) &&
                            java.util.Objects.equals(count, that.count);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(edges, pageInfo, count);
  }

  public static io.moderne.organizations.types.RepositoryConnection.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private List<RepositoryEdge> edges;

    private PageInfo pageInfo;

    private Integer count;

    public RepositoryConnection build() {
                  io.moderne.organizations.types.RepositoryConnection result = new io.moderne.organizations.types.RepositoryConnection();
                      result.edges = this.edges;
          result.pageInfo = this.pageInfo;
          result.count = this.count;
                      return result;
    }

    public io.moderne.organizations.types.RepositoryConnection.Builder edges(
        List<RepositoryEdge> edges) {
      this.edges = edges;
      return this;
    }

    public io.moderne.organizations.types.RepositoryConnection.Builder pageInfo(PageInfo pageInfo) {
      this.pageInfo = pageInfo;
      return this;
    }

    public io.moderne.organizations.types.RepositoryConnection.Builder count(Integer count) {
      this.count = count;
      return this;
    }
  }
}
