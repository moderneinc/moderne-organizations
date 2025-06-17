package io.moderne.organizations;

import graphql.schema.DataFetchingEnvironment;
import io.moderne.organizations.types.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureObservability
@SpringBootTest
public class OrganizationDataFetcherTest {

    @Autowired
    OrganizationDataFetcher organizationDataFetcher;

    @Test
    void organizationsStructure() {
        User user = User.newBuilder().email("test@moderne.io").build();
        OffsetDateTime currentDateTime = OffsetDateTime.now();

        DataFetchingEnvironment mockEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        Mockito.when(mockEnvironment.getArgument(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String argumentName = invocation.getArgument(0);
                    return "first".equals(argumentName) ? 10 : null;
                });

        StepVerifier
                .create(organizationDataFetcher.userOrganizationsPages(user, currentDateTime, mockEnvironment))
                .assertNext(connection -> {
                    assertThat(connection.getEdges()).hasSize(1);
                    assertThat(connection.getEdges().get(0).getNode().getId()).isEqualTo("ALL");
                })
                .verifyComplete();
    }
}
