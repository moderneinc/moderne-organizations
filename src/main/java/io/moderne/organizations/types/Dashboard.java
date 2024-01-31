package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class Dashboard {
  /**
   * Available/recommended upgrades and migrations.
   */
  private List<DashboardRecipe> upgradesAndMigrations;

  /**
   * Recommended visualizations to run on this organization.
   */
  private List<DashboardVisualization> visualizations;

  /**
   * Security recipes to run for this organization.
   */
  private List<DashboardRecipe> security;

  public Dashboard() {
  }

  public Dashboard(List<DashboardRecipe> upgradesAndMigrations,
      List<DashboardVisualization> visualizations, List<DashboardRecipe> security) {
    this.upgradesAndMigrations = upgradesAndMigrations;
    this.visualizations = visualizations;
    this.security = security;
  }

  /**
   * Available/recommended upgrades and migrations.
   */
  public List<DashboardRecipe> getUpgradesAndMigrations() {
    return upgradesAndMigrations;
  }

  public void setUpgradesAndMigrations(List<DashboardRecipe> upgradesAndMigrations) {
    this.upgradesAndMigrations = upgradesAndMigrations;
  }

  /**
   * Recommended visualizations to run on this organization.
   */
  public List<DashboardVisualization> getVisualizations() {
    return visualizations;
  }

  public void setVisualizations(List<DashboardVisualization> visualizations) {
    this.visualizations = visualizations;
  }

  /**
   * Security recipes to run for this organization.
   */
  public List<DashboardRecipe> getSecurity() {
    return security;
  }

  public void setSecurity(List<DashboardRecipe> security) {
    this.security = security;
  }

  @Override
  public String toString() {
    return "Dashboard{" + "upgradesAndMigrations='" + upgradesAndMigrations + "'," +"visualizations='" + visualizations + "'," +"security='" + security + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dashboard that = (Dashboard) o;
        return java.util.Objects.equals(upgradesAndMigrations, that.upgradesAndMigrations) &&
                            java.util.Objects.equals(visualizations, that.visualizations) &&
                            java.util.Objects.equals(security, that.security);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(upgradesAndMigrations, visualizations, security);
  }

  public static io.moderne.organizations.types.Dashboard.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    /**
     * Available/recommended upgrades and migrations.
     */
    private List<DashboardRecipe> upgradesAndMigrations;

    /**
     * Recommended visualizations to run on this organization.
     */
    private List<DashboardVisualization> visualizations;

    /**
     * Security recipes to run for this organization.
     */
    private List<DashboardRecipe> security;

    public Dashboard build() {
                  io.moderne.organizations.types.Dashboard result = new io.moderne.organizations.types.Dashboard();
                      result.upgradesAndMigrations = this.upgradesAndMigrations;
          result.visualizations = this.visualizations;
          result.security = this.security;
                      return result;
    }

    /**
     * Available/recommended upgrades and migrations.
     */
    public io.moderne.organizations.types.Dashboard.Builder upgradesAndMigrations(
        List<DashboardRecipe> upgradesAndMigrations) {
      this.upgradesAndMigrations = upgradesAndMigrations;
      return this;
    }

    /**
     * Recommended visualizations to run on this organization.
     */
    public io.moderne.organizations.types.Dashboard.Builder visualizations(
        List<DashboardVisualization> visualizations) {
      this.visualizations = visualizations;
      return this;
    }

    /**
     * Security recipes to run for this organization.
     */
    public io.moderne.organizations.types.Dashboard.Builder security(
        List<DashboardRecipe> security) {
      this.security = security;
      return this;
    }
  }
}
