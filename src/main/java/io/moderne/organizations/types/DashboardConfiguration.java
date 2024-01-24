package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DashboardConfiguration {
  /**
   * Available/recommended upgrades and migrations.
   */
  private List<RecipeRunRequest> upgradesAndMigrations;

  /**
   * Recommended visualizations to run on this organization.
   */
  private List<VisualizationRequest> visualizations;

  /**
   * Security recipes to run for this organization.
   */
  private List<RecipeRunRequest> security;

  public DashboardConfiguration() {
  }

  public DashboardConfiguration(List<RecipeRunRequest> upgradesAndMigrations,
      List<VisualizationRequest> visualizations, List<RecipeRunRequest> security) {
    this.upgradesAndMigrations = upgradesAndMigrations;
    this.visualizations = visualizations;
    this.security = security;
  }

  /**
   * Available/recommended upgrades and migrations.
   */
  public List<RecipeRunRequest> getUpgradesAndMigrations() {
    return upgradesAndMigrations;
  }

  public void setUpgradesAndMigrations(List<RecipeRunRequest> upgradesAndMigrations) {
    this.upgradesAndMigrations = upgradesAndMigrations;
  }

  /**
   * Recommended visualizations to run on this organization.
   */
  public List<VisualizationRequest> getVisualizations() {
    return visualizations;
  }

  public void setVisualizations(List<VisualizationRequest> visualizations) {
    this.visualizations = visualizations;
  }

  /**
   * Security recipes to run for this organization.
   */
  public List<RecipeRunRequest> getSecurity() {
    return security;
  }

  public void setSecurity(List<RecipeRunRequest> security) {
    this.security = security;
  }

  @Override
  public String toString() {
    return "DashboardConfiguration{" + "upgradesAndMigrations='" + upgradesAndMigrations + "'," +"visualizations='" + visualizations + "'," +"security='" + security + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardConfiguration that = (DashboardConfiguration) o;
        return java.util.Objects.equals(upgradesAndMigrations, that.upgradesAndMigrations) &&
                            java.util.Objects.equals(visualizations, that.visualizations) &&
                            java.util.Objects.equals(security, that.security);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(upgradesAndMigrations, visualizations, security);
  }

  public static io.moderne.organizations.types.DashboardConfiguration.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    /**
     * Available/recommended upgrades and migrations.
     */
    private List<RecipeRunRequest> upgradesAndMigrations;

    /**
     * Recommended visualizations to run on this organization.
     */
    private List<VisualizationRequest> visualizations;

    /**
     * Security recipes to run for this organization.
     */
    private List<RecipeRunRequest> security;

    public DashboardConfiguration build() {
                  io.moderne.organizations.types.DashboardConfiguration result = new io.moderne.organizations.types.DashboardConfiguration();
                      result.upgradesAndMigrations = this.upgradesAndMigrations;
          result.visualizations = this.visualizations;
          result.security = this.security;
                      return result;
    }

    /**
     * Available/recommended upgrades and migrations.
     */
    public io.moderne.organizations.types.DashboardConfiguration.Builder upgradesAndMigrations(
        List<RecipeRunRequest> upgradesAndMigrations) {
      this.upgradesAndMigrations = upgradesAndMigrations;
      return this;
    }

    /**
     * Recommended visualizations to run on this organization.
     */
    public io.moderne.organizations.types.DashboardConfiguration.Builder visualizations(
        List<VisualizationRequest> visualizations) {
      this.visualizations = visualizations;
      return this;
    }

    /**
     * Security recipes to run for this organization.
     */
    public io.moderne.organizations.types.DashboardConfiguration.Builder security(
        List<RecipeRunRequest> security) {
      this.security = security;
      return this;
    }
  }
}
