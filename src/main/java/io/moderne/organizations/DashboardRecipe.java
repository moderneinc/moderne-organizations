package io.moderne.organizations;

import org.openrewrite.internal.lang.Nullable;

import java.util.List;

public record DashboardRecipe(String id,
                              @Nullable List<Option> options) {
}
