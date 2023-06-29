package io.moderne.orgs;

import io.moderne.orgs.types.RepositoryInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationRepositoryTest {
    private static final RepositoryInput OPENREWRITE_ORGANIZATIONS_INPUT = new RepositoryInput("openrewrite/rewrite", "github.com", "main");
    private static final RepositoryInput JUNIT_4_INPUT = new RepositoryInput("junit-team/junit4", "github.com", "main");
    public static final String PATH = "openrewrite/rewrite";

    @Test
    void noAllStarAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrganizationRepository("*", "*", "*"));
    }

    @Test
    void matchesExactly() {
        OrganizationRepository repository = new OrganizationRepository(PATH, "github.com", "main");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertFalse(repository.matches(JUNIT_4_INPUT));
    }

    @Test
    void matchesWildCardBranch() {
        OrganizationRepository repository = new OrganizationRepository(PATH, "github.com", "*");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertFalse(repository.matches(JUNIT_4_INPUT));
    }

    @Test
    void matchesWildCardOrigin() {
        OrganizationRepository repository = new OrganizationRepository(PATH, "*", "main");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertFalse(repository.matches(JUNIT_4_INPUT));
    }

    @Test
    void matchesWildCardPath() {
        OrganizationRepository repository = new OrganizationRepository("*", "github.com", "main");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertTrue(repository.matches(JUNIT_4_INPUT));
    }

    @Test
    void matchesWildCardPathAndBranch() {
        OrganizationRepository repository = new OrganizationRepository("*", "github.com", "*");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertTrue(repository.matches(JUNIT_4_INPUT));
    }

    @Test
    void matchesWildcardPathWithPrefix() {
        OrganizationRepository repository = new OrganizationRepository("openrewrite/*", "github.com", "main");
        assertTrue(repository.matches(OPENREWRITE_ORGANIZATIONS_INPUT));
        assertTrue(repository.matches(new RepositoryInput("openrewrite/rewrite", "github.com", "main")));
        assertFalse(repository.matches(JUNIT_4_INPUT));
    }
}
