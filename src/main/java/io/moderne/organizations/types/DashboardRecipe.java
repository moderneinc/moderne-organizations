package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DashboardRecipe {
  private String recipeId;

  private List<Option> recipeOptions;

  public DashboardRecipe() {
  }

  public DashboardRecipe(String recipeId, List<Option> recipeOptions) {
    this.recipeId = recipeId;
    this.recipeOptions = recipeOptions;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    this.recipeId = recipeId;
  }

  public List<Option> getRecipeOptions() {
    return recipeOptions;
  }

  public void setRecipeOptions(List<Option> recipeOptions) {
    this.recipeOptions = recipeOptions;
  }

  @Override
  public String toString() {
    return "DashboardRecipe{" + "recipeId='" + recipeId + "'," +"recipeOptions='" + recipeOptions + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardRecipe that = (DashboardRecipe) o;
        return java.util.Objects.equals(recipeId, that.recipeId) &&
                            java.util.Objects.equals(recipeOptions, that.recipeOptions);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(recipeId, recipeOptions);
  }

  public static io.moderne.organizations.types.DashboardRecipe.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String recipeId;

    private List<Option> recipeOptions;

    public DashboardRecipe build() {
                  io.moderne.organizations.types.DashboardRecipe result = new io.moderne.organizations.types.DashboardRecipe();
                      result.recipeId = this.recipeId;
          result.recipeOptions = this.recipeOptions;
                      return result;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder recipeId(String recipeId) {
      this.recipeId = recipeId;
      return this;
    }

    public io.moderne.organizations.types.DashboardRecipe.Builder recipeOptions(
        List<Option> recipeOptions) {
      this.recipeOptions = recipeOptions;
      return this;
    }
  }
}
