package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DevCenter {
  /**
   * Available/recommended upgrades and migrations.
   */
  private List<DevCenterRecipeCard> upgradesAndMigrations;

  /**
   * Recommended visualizations to run on this organization.
   */
  private List<DevCenterVisualizationCard> visualizations;

  /**
   * Security recipes to run for this organization.
   */
  private List<DevCenterRecipe> security;

  /**
   * A version number for the DevCenter API to use the latest features (without breaking the old)
   */
  private int version;

  public DevCenter() {
  }

  public DevCenter(List<DevCenterRecipeCard> upgradesAndMigrations,
      List<DevCenterVisualizationCard> visualizations, List<DevCenterRecipe> security,
      int version) {
    this.upgradesAndMigrations = upgradesAndMigrations;
    this.visualizations = visualizations;
    this.security = security;
    this.version = version;
  }

  /**
   * Available/recommended upgrades and migrations.
   */
  public List<DevCenterRecipeCard> getUpgradesAndMigrations() {
    return upgradesAndMigrations;
  }

  public void setUpgradesAndMigrations(List<DevCenterRecipeCard> upgradesAndMigrations) {
    this.upgradesAndMigrations = upgradesAndMigrations;
  }

  /**
   * Recommended visualizations to run on this organization.
   */
  public List<DevCenterVisualizationCard> getVisualizations() {
    return visualizations;
  }

  public void setVisualizations(List<DevCenterVisualizationCard> visualizations) {
    this.visualizations = visualizations;
  }

  /**
   * Security recipes to run for this organization.
   */
  public List<DevCenterRecipe> getSecurity() {
    return security;
  }

  public void setSecurity(List<DevCenterRecipe> security) {
    this.security = security;
  }

  /**
   * A version number for the DevCenter API to use the latest features (without breaking the old)
   */
  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "DevCenter{" + "upgradesAndMigrations='" + upgradesAndMigrations + "'," +"visualizations='" + visualizations + "'," +"security='" + security + "'," +"version='" + version + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevCenter that = (DevCenter) o;
        return java.util.Objects.equals(upgradesAndMigrations, that.upgradesAndMigrations) &&
                            java.util.Objects.equals(visualizations, that.visualizations) &&
                            java.util.Objects.equals(security, that.security) &&
                            version == that.version;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(upgradesAndMigrations, visualizations, security, version);
  }

  public static io.moderne.organizations.types.DevCenter.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    /**
     * Available/recommended upgrades and migrations.
     */
    private List<DevCenterRecipeCard> upgradesAndMigrations;

    /**
     * Recommended visualizations to run on this organization.
     */
    private List<DevCenterVisualizationCard> visualizations;

    /**
     * Security recipes to run for this organization.
     */
    private List<DevCenterRecipe> security;

    /**
     * A version number for the DevCenter API to use the latest features (without breaking the old)
     */
    private int version;

    public DevCenter build() {
                  io.moderne.organizations.types.DevCenter result = new io.moderne.organizations.types.DevCenter();
                      result.upgradesAndMigrations = this.upgradesAndMigrations;
          result.visualizations = this.visualizations;
          result.security = this.security;
          result.version = this.version;
                      return result;
    }

    /**
     * Available/recommended upgrades and migrations.
     */
    public io.moderne.organizations.types.DevCenter.Builder upgradesAndMigrations(
        List<DevCenterRecipeCard> upgradesAndMigrations) {
      this.upgradesAndMigrations = upgradesAndMigrations;
      return this;
    }

    /**
     * Recommended visualizations to run on this organization.
     */
    public io.moderne.organizations.types.DevCenter.Builder visualizations(
        List<DevCenterVisualizationCard> visualizations) {
      this.visualizations = visualizations;
      return this;
    }

    /**
     * Security recipes to run for this organization.
     */
    public io.moderne.organizations.types.DevCenter.Builder security(
        List<DevCenterRecipe> security) {
      this.security = security;
      return this;
    }

    /**
     * A version number for the DevCenter API to use the latest features (without breaking the old)
     */
    public io.moderne.organizations.types.DevCenter.Builder version(int version) {
      this.version = version;
      return this;
    }
  }
}
