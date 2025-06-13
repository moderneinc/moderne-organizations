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
                    assertThat(next.getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                    assertThat(next.getParent()).isNull();
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Default");
                    assertThat(next.getName()).isEqualTo("Default");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Open source");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Open source/Netflix");
                    assertThat(next.getParent().getId()).isEqualTo("ALL/Open source");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Open source/OpenRewrite");
                    assertThat(next.getParent().getId()).isEqualTo("ALL/Open source");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Open source/OpenRewrite/Bitbucket");
                    assertThat(next.getParent().getId()).isEqualTo("ALL/Open source/OpenRewrite");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Test");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL/Test/WebGoat");
                    assertThat(next.getParent().getId()).isEqualTo("ALL/Test");
                    assertThat(next.getCommitOptions()).containsExactly(CommitOption.values());
                })
                .verifyComplete();
    }
}
