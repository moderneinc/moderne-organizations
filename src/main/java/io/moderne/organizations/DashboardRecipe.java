package io.moderne.organizations;

import java.util.List;

public record DashboardRecipe(String recipeId,
                              List<Option> options) {
}
