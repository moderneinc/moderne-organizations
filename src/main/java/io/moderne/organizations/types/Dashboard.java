package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class Dashboard {
  /**
   * List of recipe IDs to show upgrades and migrations for.
   */
  private List<DashboardRecipe> upgradesAndMigrations;

  private List<DashboardVisualization> visualizations;

  /**
   * List of recipe IDs to show for security.
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
   * List of recipe IDs to show upgrades and migrations for.
   */
  public List<DashboardRecipe> getUpgradesAndMigrations() {
    return upgradesAndMigrations;
  }

  public void setUpgradesAndMigrations(List<DashboardRecipe> upgradesAndMigrations) {
    this.upgradesAndMigrations = upgradesAndMigrations;
  }

  public List<DashboardVisualization> getVisualizations() {
    return visualizations;
  }

  public void setVisualizations(List<DashboardVisualization> visualizations) {
    this.visualizations = visualizations;
  }

  /**
   * List of recipe IDs to show for security.
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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
     * List of recipe IDs to show upgrades and migrations for.
     */
    private List<DashboardRecipe> upgradesAndMigrations;

    private List<DashboardVisualization> visualizations;

    /**
     * List of recipe IDs to show for security.
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
     * List of recipe IDs to show upgrades and migrations for.
     */
    public io.moderne.organizations.types.Dashboard.Builder upgradesAndMigrations(
        List<DashboardRecipe> upgradesAndMigrations) {
      this.upgradesAndMigrations = upgradesAndMigrations;
      return this;
    }

    public io.moderne.organizations.types.Dashboard.Builder visualizations(
        List<DashboardVisualization> visualizations) {
      this.visualizations = visualizations;
      return this;
    }

    /**
     * List of recipe IDs to show for security.
     */
    public io.moderne.organizations.types.Dashboard.Builder security(
        List<DashboardRecipe> security) {
      this.security = security;
      return this;
    }
  }
}
