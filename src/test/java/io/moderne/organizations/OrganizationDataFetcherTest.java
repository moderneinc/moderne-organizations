package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureObservability
@SpringBootTest
public class OrganizationDataFetcherTest {

    @Autowired
    OrganizationDataFetcher organizationDataFetcher;

    @Test
    void organizationsStructure() {
        StepVerifier
                .create(organizationDataFetcher.allOrganizations())
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Default");
                    assertThat(next.getName()).isEqualTo("Default Organization");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.Branch,CommitOption.PullRequest);
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                    assertThat(next.getParent()).isNull();
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("OpenRewrite");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.PullRequest,CommitOption.Branch,CommitOption.Direct);
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Open source");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.Fork,CommitOption.ForkAndPullRequest);
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("WebGoat");
                    assertThat(next.getParent().getId()).isEqualTo("Test");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Test");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Netflix");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Bitbucket");
                    assertThat(next.getParent().getId()).isEqualTo("OpenRewrite");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .verifyComplete();
    }
}
