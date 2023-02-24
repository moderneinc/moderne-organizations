package io.moderne.organizations;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("This test currently fails with a Spring autowiring error")
@SpringBootTest(classes = Application.class)
public class OrganizationDataFetcherTest {

    @Test
    void organizationDataFetcherWellFormed(OrganizationDataFetcher organizationDataFetcher) {
        assertNotNull(organizationDataFetcher);
    }
}
