package io.moderne.organizations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.Organization;
import io.moderne.organizations.types.RepositoryInput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Comparator;
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
                .filter(org -> org.repositories().contains(repository))
                .map(this::mapOrganization)
                //.concatWith(Flux.just(new Organization("ALL", "ALL"))) // if you want an "ALL" group
                ;
    }

    @DgsQuery
    Mono<Organization> defaultOrganization(@InputArgument String email) {
        return Mono.just(ownership.iterator().next())
                .map(this::mapOrganization);
    }

    @DgsQuery
    Flux<Organization> userOrganizations(@InputArgument String email) {
        return Flux.fromIterable(ownership)
                .filter(org -> email != null) // <== Replace with custom filtering logic
                .sort(Comparator.comparing(OrganizationRepositories::name))  // <== Replace with custom sort logic
                .map(this::mapOrganization);
    }

    private Organization mapOrganization(OrganizationRepositories org) {
        return Organization.newBuilder()
                .id(org.name())
                .name(org.name())
                .commitOptions(List.of(CommitOption.values()))
                .build();
    }
}
