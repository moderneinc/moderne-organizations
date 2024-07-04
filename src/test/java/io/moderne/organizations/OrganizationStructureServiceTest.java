package io.moderne.organizations;

import org.junit.jupiter.api.Test;

class OrganizationStructureServiceTest {

    @Test
    void canReadAllOrganizations() {
        new OrganizationStructureService(false).readOrganizationStructure();
    }

}