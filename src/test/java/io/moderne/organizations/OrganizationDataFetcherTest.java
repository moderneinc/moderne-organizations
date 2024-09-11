package io.moderne.organizations;

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
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL");
                    assertThat(next.getParent()).isNull();
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("OpenRewrite");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Open source");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("WebGoat");
                    assertThat(next.getParent().getId()).isEqualTo("Test");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Test");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Netflix");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Bitbucket");
                    assertThat(next.getParent().getId()).isEqualTo("OpenRewrite");
                })
                .verifyComplete();
    }
}
