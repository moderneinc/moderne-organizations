package io.moderne.organizations;

import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.Organization;
import io.moderne.organizations.types.RepositoryInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomScalarsConfiguration.class, OrganizationDataFetcher.class})
public class OrganizationDataFetcherTest {

    @Autowired
    OrganizationDataFetcher organizationDataFetcher;

    @Test
    void organizationForThisRepository() {
        StepVerifier
                .create(
                        organizationDataFetcher.organizations(
                                new RepositoryInput("moderneinc/moderne-organizations", "github.com", "main"),
                                10000
                        )
                )
                .expectNext(
                        Organization.newBuilder()
                                .id("Moderne")
                                .name("Moderne")
                                .commitOptions(List.of(CommitOption.PullRequest, CommitOption.Branch, CommitOption.ForkAndPullRequest, CommitOption.Fork))
                                .build()) // From the ownership.json file
                .expectNext(
                        Organization.newBuilder()
                                .id("All GitHub")
                                .name("All GitHub")
                                .commitOptions(List.of(CommitOption.Direct, CommitOption.Branch, CommitOption.Fork, CommitOption.PullRequest, CommitOption.ForkAndPullRequest))
                                .build() // from the JL file
                )
                .expectNext(Organization.newBuilder().id("ALL").name("ALL").commitOptions(List.of(CommitOption.values())).build())
                .verifyComplete();
    }
}
