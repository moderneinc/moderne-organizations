package io.moderne.organizations;

import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomScalarsConfiguration.class, OrganizationDataFetcher.class, OrganizationStructureService.class})
public class OrganizationDataFetcherTest {

    @Autowired
    OrganizationDataFetcher organizationDataFetcher;

    @Test
    void organizationsStructure() {
        StepVerifier
                .create(organizationDataFetcher.allOrganizations())
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("ALL");
                    assertThat(next.getParent()).isNull();
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Default");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("Open source");
                    assertThat(next.getParent().getId()).isEqualTo("ALL");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("OpenRewrite");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                })
                .assertNext(next -> {
                    assertThat(next.getId()).isEqualTo("WebGoat");
                    assertThat(next.getParent().getId()).isEqualTo("Open source");
                })
                .verifyComplete();
    }
}
