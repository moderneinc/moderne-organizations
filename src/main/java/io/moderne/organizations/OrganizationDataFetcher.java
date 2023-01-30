package io.moderne.organizations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.moderne.organizations.types.Organization;
import io.moderne.organizations.types.RepositoryInput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
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
                .map(org -> new Organization(org.name(), org.name()))
//                .concatWith(Flux.just(new Organization("ALL", "ALL"))) // if you want an "ALL" group
                ;
    }

    @DgsQuery
    Mono<Organization> defaultOrganization(@InputArgument String email) {
        return Mono.just(ownership.iterator().next())
                .map(org -> new Organization(org.name(), org.name()));
    }
}
