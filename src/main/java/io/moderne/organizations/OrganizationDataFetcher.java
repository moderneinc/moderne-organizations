package io.moderne.organizations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import io.moderne.organizations.types.*;
import org.openrewrite.internal.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@DgsComponent
public class OrganizationDataFetcher {
    OrganizationTree organizations;

    public OrganizationDataFetcher(OrganizationStructureService organizationStructureService) {
        this.organizations = organizationStructureService.readOrganizationStructure();
    }

    @DgsQuery
    Flux<Organization> allOrganizations() {
        return Flux.fromIterable(organizations.all())
                .map(this::mapOrganization);
    }


    @DgsQuery
    Mono<Connection<Organization>> organizationsPages(DataFetchingEnvironment dfe) {
        return Mono.fromCallable(() -> {
                    List<Organization> allOrganizations = organizations.all()
                            .stream()
                            .map(this::mapOrganization)
                            .toList();
                    return new SimpleListConnection<>(allOrganizations).get(dfe);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @DgsQuery
    Mono<Organization> organization(@InputArgument String id) {
        return Mono.justOrEmpty(organizations.findOrganization(id))
                .map(this::mapOrganization);
    }

    @DgsQuery
    Flux<Organization> userOrganizations(@InputArgument User user, @InputArgument OffsetDateTime at) {
        // Here we need to return at least the top level organizations that a user has access too.
        // A user automatically gets access to all the children of the organizations returned here.
        List<Organization> allAccessibleOrgs = new ArrayList<>();
        for (OrganizationRepositories org : organizations.roots()) {
            allAccessibleOrgs.addAll(findAccessibleRootOrganizations(org, user, at));
        }
        return Flux.fromIterable(allAccessibleOrgs);
    }

    @DgsQuery
    Mono<Connection<Organization>> userOrganizationsPages(@InputArgument User user, @InputArgument OffsetDateTime at, DataFetchingEnvironment dfe) {
        return Mono.fromCallable(() -> {
            // Here we need to return at least the top level organizations that a user has access too.
            // A user automatically gets access to all the children of the organizations returned here.
            List<Organization> allAccessibleOrgs = new ArrayList<>();
            for (OrganizationRepositories org : organizations.roots()) {
                allAccessibleOrgs.addAll(findAccessibleRootOrganizations(org, user, at));
            }
            return new SimpleListConnection<>(allAccessibleOrgs).get(dfe);
        });
    }

    Collection<Organization> findAccessibleRootOrganizations(OrganizationRepositories root, User user, OffsetDateTime at) {
        if (allowAccess(user, at, root.name())) {
            return List.of(mapOrganization(root));
        }
        return organizations.findChildren(root.id()).stream()
                .flatMap(child -> findAccessibleRootOrganizations(child, user, at).stream())
                .toList();
    }

    private boolean allowAccess(User user, OffsetDateTime at, String orgName) {
        // Determine if a user should have access to the organization
        return true;
    }

    private Organization mapOrganization(OrganizationRepositories org) {
        return Organization.newBuilder()
                .id(org.id())
                .name(org.name())
                .commitOptions(org.commitOptions() == null ?
                        List.of(CommitOption.values()) :
                        org.commitOptions())
                ._parent(org.parent() != null ? getOrganizationByName(org.parent()) : null)
                .build();
    }

    @DgsData(parentType = DgsConstants.ORGANIZATION.TYPE_NAME)
    public Mono<Connection<Repository>> repositories(DataFetchingEnvironment dfe) {
        Organization organization = dfe.getSource();
        if (organization == null) {
            return Mono.empty();
        }
        return Mono.fromCallable(() -> {
            OrganizationRepositories org = organizations.findOrganization(organization.getName());
            if (org != null) {
                List<Repository> repositories = org
                        .repositories()
                        .stream()
                        .map(this::mapRepository)
                        .toList();
                return new SimpleListConnection<>(repositories).get(dfe);
            }
            return new SimpleListConnection<>(Collections.<Repository>emptyList()).get(dfe);
        });
    }

    private Repository mapRepository(RepositoryInput repositoryInput) {
        return Repository.newBuilder()
                .origin(repositoryInput.getOrigin())
                .path(repositoryInput.getPath())
                .branch(repositoryInput.getBranch())
                .build();
    }

    @Nullable
    private Organization getOrganizationByName(String name) {
        OrganizationRepositories org = organizations.findOrganization(name);
        if (org == null) {
            return null;
        }
        return mapOrganization(org);
    }
}
