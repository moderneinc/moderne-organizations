package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationRepositoryTest {
    private static final RepositoryInput MODERNE_ORGANIZATIONS_INPUT = new RepositoryInput("moderneinc/moderne-organizations", "github.com", "main");
    private static final RepositoryInput JUNIT_4_INPUT = new RepositoryInput("junit-team/junit4", "github.com", "main");
    @Test
    void noAllStarAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrganizationRepository("*", "*", "*"));
    }

    @Test
    void matchesExactly() {
        OrganizationRepository repository = new OrganizationRepository("moderneinc/moderne-organizations", "github.com", "main");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertFalse(repository.matches(JUNIT_4_INPUT))
        );

    }

    @Test
    void matchesWildCardBranch() {
        OrganizationRepository repository = new OrganizationRepository("moderneinc/moderne-organizations", "github.com", "*");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertFalse(repository.matches(JUNIT_4_INPUT))
        );
    }

    @Test
    void matchesWildCardOrigin() {
        OrganizationRepository repository = new OrganizationRepository("moderneinc/moderne-organizations", "*", "main");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertFalse(repository.matches(JUNIT_4_INPUT))
        );
    }

    @Test
    void matchesWildCardPath() {
        OrganizationRepository repository = new OrganizationRepository("*", "github.com", "main");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertTrue(repository.matches(JUNIT_4_INPUT))
        );
    }

    @Test
    void matchesWildCardPathAndBranch() {
        OrganizationRepository repository = new OrganizationRepository("*", "github.com", "*");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertTrue(repository.matches(JUNIT_4_INPUT))
        );
    }

    @Test
    void matchesWildcardPathWithPrefix() {
        OrganizationRepository repository = new OrganizationRepository("moderneinc/*", "github.com", "main");
        assertAll(
                () -> assertTrue(repository.matches(MODERNE_ORGANIZATIONS_INPUT)),
                () -> assertTrue(repository.matches(new RepositoryInput("moderneinc/moderne-client-java", "github.com", "main"))),
                () -> assertFalse(repository.matches(JUNIT_4_INPUT))
        );
    }
}
