package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DevCenterRecipe {
  /**
   * Optional name used for displaying on the DevCenter. Defaults to the recipe display name.
   */
  private String name;

  private String recipeId;

  private List<Option> options;

  public DevCenterRecipe() {
  }

  public DevCenterRecipe(String name, String recipeId, List<Option> options) {
    this.name = name;
    this.recipeId = recipeId;
    this.options = options;
  }

  /**
   * Optional name used for displaying on the DevCenter. Defaults to the recipe display name.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    return "DevCenterRecipe{" + "name='" + name + "'," +"recipeId='" + recipeId + "'," +"options='" + options + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevCenterRecipe that = (DevCenterRecipe) o;
        return java.util.Objects.equals(name, that.name) &&
                            java.util.Objects.equals(recipeId, that.recipeId) &&
                            java.util.Objects.equals(options, that.options);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(name, recipeId, options);
  }

  public static io.moderne.organizations.types.DevCenterRecipe.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    /**
     * Optional name used for displaying on the DevCenter. Defaults to the recipe display name.
     */
    private String name;

    private String recipeId;

    private List<Option> options;

    public DevCenterRecipe build() {
                  io.moderne.organizations.types.DevCenterRecipe result = new io.moderne.organizations.types.DevCenterRecipe();
                      result.name = this.name;
          result.recipeId = this.recipeId;
          result.options = this.options;
                      return result;
    }

    /**
     * Optional name used for displaying on the DevCenter. Defaults to the recipe display name.
     */
    public io.moderne.organizations.types.DevCenterRecipe.Builder name(String name) {
      this.name = name;
      return this;
    }

    public io.moderne.organizations.types.DevCenterRecipe.Builder recipeId(String recipeId) {
      this.recipeId = recipeId;
      return this;
    }

    public io.moderne.organizations.types.DevCenterRecipe.Builder options(List<Option> options) {
      this.options = options;
      return this;
    }
  }
}
