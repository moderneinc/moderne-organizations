package io.moderne.organizations;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import graphql.ExecutionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureObservability
@SpringBootTest
class ModerneContractTest {

    @Autowired
    DgsQueryExecutor queryExecutor;

    @Test
    @SuppressWarnings("unchecked")
    void userOrganizationsPages() {
        ExecutionResult result = queryExecutor.execute("""
                        query OrganizationAccess($user: User!, $after: String, $first: Int) {
                          userOrganizationsPages(user: $user, after: $after, first: $first) {
                            pageInfo { hasNextPage endCursor }
                            edges { node { id } }
                          }
                        }
                        """,
                Map.of("user", Map.of("email", "test@moderne.io"), "first", 1000));

        assertThat(result.getErrors()).isEmpty();
        Map<String, Object> pages = result.<Map<String, Map<String, Object>>>getData()
                .get("userOrganizationsPages");
        assertThat((Map<String, Object>) pages.get("pageInfo"))
                .containsEntry("hasNextPage", false)
                .containsKey("endCursor");
        assertThat(pages.get("edges")).isEqualTo(List.of(Map.of("node", Map.of("id", "ALL"))));
    }

    @Test
    void commitMessage() {
        assertCommitMessageUnchanged(Map.of("path", "openrewrite/rewrite", "origin", "github.com", "branch", "main"));
    }

    @Test
    void commitMessageWithoutOriginOrBranch() {
        assertCommitMessageUnchanged(Map.of("path", "openrewrite/rewrite"));
    }

    private void assertCommitMessageUnchanged(Map<String, Object> repository) {
        ExecutionResult result = queryExecutor.execute("""
                        query CommitMessage($commitInput: CommitInput!, $repository: RepositoryInput!) {
                          commitMessage(commitInput: $commitInput, repository: $repository) {
                            message
                          }
                        }
                        """,
                Map.of("commitInput", Map.of("message", "Upgrade Spring Boot"), "repository", repository));

        assertThat(result.getErrors()).isEmpty();
        assertThat(result.<Map<String, Object>>getData())
                .isEqualTo(Map.of("commitMessage", Map.of("message", "Upgrade Spring Boot")));
    }
}
