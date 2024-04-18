package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Measure {
  private String name;

  private DevCenterRecipe recipe;

  public Measure() {
  }

  public Measure(String name, DevCenterRecipe recipe) {
    this.name = name;
    this.recipe = recipe;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DevCenterRecipe getRecipe() {
    return recipe;
  }

  public void setRecipe(DevCenterRecipe recipe) {
    this.recipe = recipe;
  }

  @Override
  public String toString() {
    return "Measure{" + "name='" + name + "'," +"recipe='" + recipe + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        Measure that = (Measure) o;
        return java.util.Objects.equals(name, that.name) &&
                            java.util.Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(name, recipe);
  }

  public static io.moderne.organizations.types.Measure.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String name;

    private DevCenterRecipe recipe;

    public Measure build() {
                  io.moderne.organizations.types.Measure result = new io.moderne.organizations.types.Measure();
                      result.name = this.name;
          result.recipe = this.recipe;
                      return result;
    }

    public io.moderne.organizations.types.Measure.Builder name(String name) {
      this.name = name;
      return this;
    }

    public io.moderne.organizations.types.Measure.Builder recipe(DevCenterRecipe recipe) {
      this.recipe = recipe;
      return this;
    }
  }
}
