package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DashboardVisualization {
  private DashboardRecipe recipe;

  private String id;

  private List<Option> options;

  public DashboardVisualization() {
  }

  public DashboardVisualization(DashboardRecipe recipe, String id, List<Option> options) {
    this.recipe = recipe;
    this.id = id;
    this.options = options;
  }

  public DashboardRecipe getRecipe() {
    return recipe;
  }

  public void setRecipe(DashboardRecipe recipe) {
    this.recipe = recipe;
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
    return "DashboardVisualization{" + "recipe='" + recipe + "'," +"id='" + id + "'," +"options='" + options + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        DashboardVisualization that = (DashboardVisualization) o;
        return java.util.Objects.equals(recipe, that.recipe) &&
                            java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(recipe, id, options);
  }

  public static io.moderne.organizations.types.DashboardVisualization.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private DashboardRecipe recipe;

    private String id;

    private List<Option> options;

    public DashboardVisualization build() {
                  io.moderne.organizations.types.DashboardVisualization result = new io.moderne.organizations.types.DashboardVisualization();
                      result.recipe = this.recipe;
          result.id = this.id;
          result.options = this.options;
                      return result;
    }

    public io.moderne.organizations.types.DashboardVisualization.Builder recipe(
        DashboardRecipe recipe) {
      this.recipe = recipe;
      return this;
    }

    public io.moderne.organizations.types.DashboardVisualization.Builder id(String id) {
      this.id = id;
      return this;
    }

    public io.moderne.organizations.types.DashboardVisualization.Builder options(
        List<Option> options) {
      this.options = options;
      return this;
    }
  }
}
