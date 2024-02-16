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
                                .dashboard(Dashboard.newBuilder()
                                        .upgradesAndMigrations(List.of(
                                                new DashboardRecipe("org.openrewrite.java.migrate.UpgradeToJava21", null),
                                                new DashboardRecipe("org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2", null),
                                                new DashboardRecipe("org.openrewrite.java.testing.junit5.JUnit4to5Migration", null)
                                        ))
                                        .security(List.of(
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA01", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA02", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA03", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA04", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA05", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA06", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA08", null),
                                                new DashboardRecipe("org.openrewrite.java.security.OwaspA10", null)
                                        ))
                                        .visualizations(List.of(
                                                new DashboardVisualization(
                                                        "io.moderne.LanguageComposition",
                                                        null
                                                ),
                                                new DashboardVisualization(
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
