package io.moderne.organizations;

import java.util.List;

public record DashboardVisualization(String recipeId,
                                     List<Option> recipeOptions,
                                     String visualizationId,
                                     List<Option> visualizationOptions) {
}
