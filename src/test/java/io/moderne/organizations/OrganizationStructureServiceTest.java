package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationStructureServiceTest {

    @Test
    void canReadAllOrganizations() {
        new OrganizationStructureService(false).readOrganizationStructure();
    }

    @Test
    void removeScmFromBitbucketCloneUrl() {
        OrganizationRepositories organizationRepositories = new OrganizationStructureService(false)
                .readOrganizationStructure()
                .get("Bitbucket");
        assertThat(organizationRepositories.repositories())
                .extracting(RepositoryInput::getOrigin)
                .containsExactly("bitbucket.example.com/stash");
        assertThat(organizationRepositories.repositories())
                .extracting(RepositoryInput::getPath)
                .containsExactly("openrewrite/rewrite");
    }
}