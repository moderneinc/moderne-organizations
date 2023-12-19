package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DashboardRecipe {
  private String id;

  private List<Option> options;

  public DashboardRecipe() {
  }

  public DashboardRecipe(String id, List<Option> options) {
    this.id = id;
    this.options = options;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return "DashboardRecipe{" + "id='" + id + "'," +"options='" + options + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardRecipe that = (DashboardRecipe) o;
        return java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, options);
  }

  public static io.moderne.organizations.types.DashboardRecipe.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private List<Option> options;

    public DashboardRecipe build() {
                  io.moderne.organizations.types.DashboardRecipe result = new io.moderne.organizations.types.DashboardRecipe();
                      result.id = this.id;
          result.options = this.options;
                      return result;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder id(String id) {
      this.id = id;
      return this;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder options(List<Option> options) {
      this.options = options;
      return this;
    }
  }
}
