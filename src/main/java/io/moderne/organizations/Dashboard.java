package io.moderne.organizations;

import java.util.List;

public record Dashboard(List<DashboardRecipe> upgradesAndMigrations,
                        List<DashboardRecipe> security,
                        List<DashboardVisualization> visualizations) {
}
