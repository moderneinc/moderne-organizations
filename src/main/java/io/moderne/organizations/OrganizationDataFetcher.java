package io.moderne.organizations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.moderne.organizations.types.*;
import io.moderne.organizations.types.User;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@DgsComponent
public class OrganizationDataFetcher {
    List<OrganizationRepositories> ownership;

    public OrganizationDataFetcher(ObjectMapper mapper) throws IOException {
        this.ownership = mapper.readValue(
                getClass().getResourceAsStream("/ownership.json"),
                new TypeReference<>() {
                }
        );
    }

    @DgsQuery
    Flux<Organization> organizations(@InputArgument RepositoryInput repository) {
        return Flux.fromIterable(ownership)
                .filter(org -> org.matches(repository))
                .map(OrganizationDataFetcher::mapOrganization)
                .concatWithValues(Organization.newBuilder().id("ALL").name("ALL").commitOptions(List.of(CommitOption.values())).build()); // if you want an "ALL" group
    }

    @DgsQuery
    Flux<Organization> userOrganizations(@InputArgument User user, @InputArgument OffsetDateTime at) {
        // everybody belongs to every organization, and the "default" organization is listed
        // first in the json that this list is based on, so it will be selected by default in the UI
        return Flux.fromIterable(ownership)
                .map(OrganizationDataFetcher::mapOrganization)
                .concatWith(
                        Flux.just(Organization.newBuilder().id("ALL").name("ALL").commitOptions(List.of(CommitOption.values())).build())
                                .filter(__ -> true) // give "ALL" organization to all users
                );
    }

    private static Organization mapOrganization(OrganizationRepositories org) {
        return Organization.newBuilder()
                .id(org.name())
                .name(org.name())
                .dashboard(mapDashboard(org.dashboard()))
                .commitOptions(org.commitOptions() == null ?
                        List.of(CommitOption.values()) :
                        org.commitOptions())
                .build();
    }

    private static io.moderne.organizations.types.Dashboard mapDashboard(io.moderne.organizations.Dashboard dashboard) {
        if (dashboard == null) {
            return null;
        }

        return io.moderne.organizations.types.Dashboard.newBuilder()
                .upgradesAndMigrations(dashboard.upgradesAndMigrations())
                .visualizations(dashboard.visualizations()
                        .stream()
                        .map(OrganizationDataFetcher::mapVisualization)
                        .toList())
                .security(dashboard.security())
                .build();
    }

    private static io.moderne.organizations.types.DashboardVisualization mapVisualization(DashboardVisualization dashboardVisualization) {
        if (dashboardVisualization == null) {
            return null;
        }

        return io.moderne.organizations.types.DashboardVisualization.newBuilder()
                .visualizationId(dashboardVisualization.visualizationId())
                .recipeId(dashboardVisualization.recipeId())
                .visualizationOptions(dashboardVisualization.visualizationOptions() == null ? null :
                        dashboardVisualization.visualizationOptions().stream()
                                .map(OrganizationDataFetcher::mapOption)
                                .toList())
                .recipeOptions(dashboardVisualization.recipeOptions() == null ? null :
                        dashboardVisualization.recipeOptions().stream()
                                .map(OrganizationDataFetcher::mapOption)
                                .toList())
                .build();
    }

    private static io.moderne.organizations.types.Option mapOption(Option option) {
        return io.moderne.organizations.types.Option.newBuilder()
                .name(option.name())
                .value(option.value())
                .build();
    }
}
