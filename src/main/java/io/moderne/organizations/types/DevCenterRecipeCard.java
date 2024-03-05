package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DevCenterRecipeCard {
  private String title;

  private List<Measure> measures;

  private DevCenterRecipe fix;

  public DevCenterRecipeCard() {
  }

  public DevCenterRecipeCard(String title, List<Measure> measures, DevCenterRecipe fix) {
    this.title = title;
    this.measures = measures;
    this.fix = fix;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Measure> getMeasures() {
    return measures;
  }

  public void setMeasures(List<Measure> measures) {
    this.measures = measures;
  }

  public DevCenterRecipe getFix() {
    return fix;
  }

  public void setFix(DevCenterRecipe fix) {
    this.fix = fix;
  }

  @Override
  public String toString() {
    return "DevCenterRecipeCard{" + "title='" + title + "'," +"measures='" + measures + "'," +"fix='" + fix + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevCenterRecipeCard that = (DevCenterRecipeCard) o;
        return java.util.Objects.equals(title, that.title) &&
                            java.util.Objects.equals(measures, that.measures) &&
                            java.util.Objects.equals(fix, that.fix);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(title, measures, fix);
  }

  public static io.moderne.organizations.types.DevCenterRecipeCard.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String title;

    private List<Measure> measures;

    private DevCenterRecipe fix;

    public DevCenterRecipeCard build() {
                  io.moderne.organizations.types.DevCenterRecipeCard result = new io.moderne.organizations.types.DevCenterRecipeCard();
                      result.title = this.title;
          result.measures = this.measures;
          result.fix = this.fix;
                      return result;
    }

    public io.moderne.organizations.types.DevCenterRecipeCard.Builder title(String title) {
      this.title = title;
      return this;
    }

    public io.moderne.organizations.types.DevCenterRecipeCard.Builder measures(
        List<Measure> measures) {
      this.measures = measures;
      return this;
    }

    public io.moderne.organizations.types.DevCenterRecipeCard.Builder fix(DevCenterRecipe fix) {
      this.fix = fix;
      return this;
    }
  }
}
