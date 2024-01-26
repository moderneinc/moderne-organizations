package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DashboardRecipe {
  private String recipeId;

  private List<Option> options;

  public DashboardRecipe() {
  }

  public DashboardRecipe(String recipeId, List<Option> options) {
    this.recipeId = recipeId;
    this.options = options;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    this.recipeId = recipeId;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return "DashboardRecipe{" + "recipeId='" + recipeId + "'," +"options='" + options + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardRecipe that = (DashboardRecipe) o;
        return java.util.Objects.equals(recipeId, that.recipeId) &&
                            java.util.Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(recipeId, options);
  }

  public static io.moderne.organizations.types.DashboardRecipe.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String recipeId;

    private List<Option> options;

    public DashboardRecipe build() {
                  io.moderne.organizations.types.DashboardRecipe result = new io.moderne.organizations.types.DashboardRecipe();
                      result.recipeId = this.recipeId;
          result.options = this.options;
                      return result;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder recipeId(String recipeId) {
      this.recipeId = recipeId;
      return this;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder options(List<Option> options) {
      this.options = options;
      return this;
    }
  }
}
