package io.moderne.organizations;

import java.util.List;

public record Dashboard(List<String> upgradesAndMigrations,
                        List<String> security,
                        List<DashboardVisualization> visualizations) {
}
