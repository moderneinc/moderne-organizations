package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureObservability
@SpringBootTest(properties = {
        "spring.config.location=classpath:/application-test.yaml"
})
class OrganizationStructureServiceCustomTest {

    @Autowired
    OrganizationStructureService structureService;

    @Test
    void canReadAllOrganizations() {
        structureService.readOrganizationStructure();
    }

    @Test
    void removeScmFromBitbucketCloneUrl() {
        OrganizationRepositories organizationRepositories = structureService.readOrganizationStructure().findOrganization("ALL/Open source/OpenRewrite/Bitbucket TEST ORG");
        assertThat(organizationRepositories.repositories())
                .extracting(RepositoryInput::getOrigin)
                .containsExactly("bitbucket.example.com/stash");
        assertThat(organizationRepositories.repositories())
                .extracting(RepositoryInput::getPath)
                .containsExactly("openrewrite/rewrite");
    }
}