package io.moderne.organizations;

import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import io.moderne.organizations.types.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomScalarsConfiguration.class, OrganizationDataFetcher.class})
public class OrganizationDataFetcherTest {

    @Autowired
    OrganizationDataFetcher organizationDataFetcher;

    @Test
    void organizationForThisRepository() {
        StepVerifier
                .create(organizationDataFetcher.organizations(new RepositoryInput("openrewrite/rewrite", "github.com", "main")))
                .expectNext(
                        Organization.newBuilder()
                                .id("OpenRewrite")
                                .name("OpenRewrite")
                                .commitOptions(List.of(CommitOption.PullRequest, CommitOption.Branch, CommitOption.ForkAndPullRequest, CommitOption.Fork))
                                .dashboard(DashboardConfiguration.newBuilder()
                                        .upgradesAndMigrations(List.of(
                                                new RecipeRunRequest("org.openrewrite.java.migrate.UpgradeToJava21", null),
                                                new RecipeRunRequest("org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2", null),
                                                new RecipeRunRequest("org.openrewrite.java.testing.junit5.JUnit4to5Migration", null)
                                        ))
                                        .security(List.of(
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA01", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA02", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA03", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA04", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA05", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA06", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA08", null),
                                                new RecipeRunRequest("org.openrewrite.java.security.OwaspA10", null)
                                        ))
                                        .visualizations(List.of(
                                                new VisualizationRequest(
                                                        "org.openrewrite.LanguageComposition",
                                                        null,
                                                        "io.moderne.LanguageComposition",
                                                        null
                                                ),
                                                new VisualizationRequest(
                                                        "org.openrewrite.sql.FindSql",
                                                        null,
                                                        "io.moderne.SqlCrud",
                                                        null
                                                )
                                        ))
                                        .build())
                                .build()) // From the ownership.json file
                .expectNext(Organization.newBuilder().id("ALL").name("ALL").commitOptions(List.of(CommitOption.values())).build())
                .verifyComplete();
    }
}
