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
                .map(org -> Organization.newBuilder()
                        .id(org.name())
                        .name(org.name())
                        .allCommitOptions()
                        .build())
                .concatWith(Flux.just(createForOrigin(repository)))
//                .concatWith(Flux.just(createForOrganizationName(repository))) // if you want a group per organization
//                .concatWith(Flux.just(Organization.newBuilder().id("ALL").name("ALL").allCommitOptions().build())) // if you want an "ALL" group
                ;
    }

    /**
     * Automatically puts the repository into a group based on the organization name.
     */
    private static Organization createForOrganizationName(RepositoryInput repositoryInput) {
        String organizationName = repositoryInput.getPath().split("/")[0];
        String id = repositoryInput.getOrigin() + "/" + organizationName;
        return Organization.newBuilder()
                .id(id)
                .name(repositoryInput.getOrigin() + ": " + organizationName)
                .allCommitOptions()
                .build();
    }

    /**
     * Automatically puts the repository into a group based on the origin.
     */
    private static Organization createForOrigin(RepositoryInput repositoryInput) {
        String id = repositoryInput.getOrigin();
        return Organization.newBuilder()
                .id(id)
                .name(repositoryInput.getOrigin())
                .allCommitOptions()
                .build();
    }

    @DgsQuery
    Mono<Organization> defaultOrganization(@InputArgument String email) {
        return Mono.just(ownership.iterator().next())
                .map(org -> Organization.newBuilder()
                        .id(org.name())
                        .name(org.name())
                        .allCommitOptions()
                        .build());
    }

}
