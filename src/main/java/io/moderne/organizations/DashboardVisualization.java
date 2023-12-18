package io.moderne.organizations;

import java.util.List;

public record DashboardVisualization(DashboardRecipe recipe,
                                     String visualizationId,
                                     List<Option> visualizationOptions) {
}
