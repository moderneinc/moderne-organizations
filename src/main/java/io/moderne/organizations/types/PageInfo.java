package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class PageInfo {
  private boolean hasPreviousPage;

  private boolean hasNextPage;

  private String startCursor;

  private String endCursor;

  public PageInfo() {
  }

  public PageInfo(boolean hasPreviousPage, boolean hasNextPage, String startCursor,
      String endCursor) {
    this.hasPreviousPage = hasPreviousPage;
    this.hasNextPage = hasNextPage;
    this.startCursor = startCursor;
    this.endCursor = endCursor;
  }

  public boolean getHasPreviousPage() {
    return hasPreviousPage;
  }

  public void setHasPreviousPage(boolean hasPreviousPage) {
    this.hasPreviousPage = hasPreviousPage;
  }

  public boolean getHasNextPage() {
    return hasNextPage;
  }

  public void setHasNextPage(boolean hasNextPage) {
    this.hasNextPage = hasNextPage;
  }

  public String getStartCursor() {
    return startCursor;
  }

  public void setStartCursor(String startCursor) {
    this.startCursor = startCursor;
  }

  public String getEndCursor() {
    return endCursor;
  }

  public void setEndCursor(String endCursor) {
    this.endCursor = endCursor;
  }

  @Override
  public String toString() {
    return "PageInfo{" + "hasPreviousPage='" + hasPreviousPage + "'," +"hasNextPage='" + hasNextPage + "'," +"startCursor='" + startCursor + "'," +"endCursor='" + endCursor + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        PageInfo that = (PageInfo) o;
        return hasPreviousPage == that.hasPreviousPage &&
                            hasNextPage == that.hasNextPage &&
                            java.util.Objects.equals(startCursor, that.startCursor) &&
                            java.util.Objects.equals(endCursor, that.endCursor);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(hasPreviousPage, hasNextPage, startCursor, endCursor);
  }

  public static io.moderne.organizations.types.PageInfo.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private boolean hasPreviousPage;

    private boolean hasNextPage;

    private String startCursor;

    private String endCursor;

    public PageInfo build() {
                  io.moderne.organizations.types.PageInfo result = new io.moderne.organizations.types.PageInfo();
                      result.hasPreviousPage = this.hasPreviousPage;
          result.hasNextPage = this.hasNextPage;
          result.startCursor = this.startCursor;
          result.endCursor = this.endCursor;
                      return result;
    }

    public io.moderne.organizations.types.PageInfo.Builder hasPreviousPage(
        boolean hasPreviousPage) {
      this.hasPreviousPage = hasPreviousPage;
      return this;
    }

    public io.moderne.organizations.types.PageInfo.Builder hasNextPage(boolean hasNextPage) {
      this.hasNextPage = hasNextPage;
      return this;
    }

    public io.moderne.organizations.types.PageInfo.Builder startCursor(String startCursor) {
      this.startCursor = startCursor;
      return this;
    }

    public io.moderne.organizations.types.PageInfo.Builder endCursor(String endCursor) {
      this.endCursor = endCursor;
      return this;
    }
  }
}
