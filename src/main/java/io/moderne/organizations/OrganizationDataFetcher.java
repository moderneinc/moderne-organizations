package io.moderne.organizations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.moderne.organizations.types.*;
import org.openrewrite.internal.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@DgsComponent
public class OrganizationDataFetcher {
    Map<String, OrganizationRepositories> organizations;

    public OrganizationDataFetcher(OrganizationStructureService organizationStructureService) {
        this.organizations = organizationStructureService.readOrganizationStructure();
    }

    @DgsQuery
    Flux<Organization> organizations(@InputArgument RepositoryInput repository) {
        return Flux.fromIterable(organizations.values())
                .filter(org -> org.contains(repository))
                .map(this::mapOrganization);
    }

    @DgsQuery
    Flux<Organization> allOrganizations() {
        return Flux.fromIterable(organizations.values())
                .map(this::mapOrganization);
    }

    @DgsQuery
    Mono<Organization> organization(@InputArgument String id) {
        return Mono.justOrEmpty(organizations.get(id))
                .map(this::mapOrganization);
    }


    @DgsQuery
    Flux<Organization> userOrganizations(@InputArgument User user, @InputArgument OffsetDateTime at) {
        // everybody belongs to every organization, and the "default" organization is listed
        // first in the json that this list is based on, so it will be selected by default in the UI
        return Flux.fromIterable(organizations.values())
                .filter(org -> allowAccess(user, at, org.name()))
                .map(this::mapOrganization);
    }

    private boolean allowAccess(User user, OffsetDateTime at, String orgName) {
        // Determine if a user should have access sto the organization
        return true;
    }

    private Organization mapOrganization(OrganizationRepositories org) {
        return Organization.newBuilder()
                .id(org.name())
                .name(org.name())
                .commitOptions(org.commitOptions() == null ?
                        List.of(CommitOption.values()) :
                        org.commitOptions())
                .repositories(org.repositories().stream().map(this::mapRepository).toList())
                ._parent(org.parent() != null ? getOrganizationByName(org.parent()) : null)
                .build();
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
        OrganizationRepositories org = organizations.get(name);
        if (org == null) {
            return null;
        }
        return mapOrganization(org);
    }
}
