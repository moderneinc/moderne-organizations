package io.moderne.organizations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import io.moderne.organizations.types.Organization;
import io.moderne.organizations.types.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DgsComponent
public class OrganizationDataFetcher {
    OrganizationTree organizationTree;

    public OrganizationDataFetcher(OrganizationStructureService organizationStructureService) {
        this.organizationTree = organizationStructureService.readOrganizationStructure();
    }

    @DgsQuery
    Mono<Commit> commitMessage(@InputArgument CommitInput commitInput, @InputArgument RepositoryInput repository) {
        return Mono.fromCallable(() ->
            // here is where you would put custom logic to reach out to, e.g. JIRA
            new Commit(
                    commitInput.getMessage(),
                    commitInput.getExtendedMessage()
            ));
    }

    @DgsQuery
    Mono<Connection<Organization>> userOrganizationsPages(@InputArgument User user, @InputArgument OffsetDateTime at, DataFetchingEnvironment dfe) {
        return Mono.fromCallable(() -> {
            // Here we need to return at least the top level organizations that a user has access too.
            // A user automatically gets access to all the children of the organizations returned here.
            List<Organization> allAccessibleOrgs = new ArrayList<>();
            for (io.moderne.organizations.Organization<?> org : organizationTree.roots()) {
                allAccessibleOrgs.addAll(findAccessibleRootOrganizations(org, user, at));
            }
            return new SimpleListConnection<>(allAccessibleOrgs).get(dfe);
        });
    }

    private Collection<Organization> findAccessibleRootOrganizations(
            io.moderne.organizations.Organization<?> root,
            User user,
            OffsetDateTime at ) {
        if (allowAccess(user, at, root.getId())) {
            return List.of(mapOrganization(root));
        }
        return organizationTree.findChildren(root.getId()).stream()
                .flatMap(child -> findAccessibleRootOrganizations(child, user, at).stream())
                .toList();
    }

    private boolean allowAccess(User user, OffsetDateTime at, String orgId) {
        // Determine if a user should have access to the organization
        return true;
    }

    private Organization mapOrganization(io.moderne.organizations.Organization<?> org) {
        return Organization.newBuilder()
                .id(org.getId())
                .build();
    }
}
