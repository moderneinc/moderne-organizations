package io.moderne.orgs;

import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import io.moderne.orgs.types.CommitOption;
import io.moderne.orgs.types.Organization;
import io.moderne.orgs.types.RepositoryInput;
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
                .create(organizationDataFetcher.orgs(new RepositoryInput("openrewrite/rewrite", "github.com", "main")))
                .expectNext(
                        Organization.newBuilder()
                                .id("OpenRewrite")
                                .name("OpenRewrite")
                                .commitOptions(List.of(CommitOption.PullRequest, CommitOption.Branch, CommitOption.ForkAndPullRequest, CommitOption.Fork))
                                .build()) // From the ownership.json file
                .expectNext(Organization.newBuilder().id("ALL").name("ALL").commitOptions(List.of(CommitOption.values())).build())
                .verifyComplete();
    }
}
