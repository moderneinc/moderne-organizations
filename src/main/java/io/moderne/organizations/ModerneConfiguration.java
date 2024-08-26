package io.moderne.organizations;

import org.openrewrite.internal.lang.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "moderne")
public class ModerneConfiguration {

    @Nullable
    private Path reposCsvPath;

    private ScmConfiguration scm;

    public @Nullable Path getReposCsvPath() {
        return reposCsvPath;
    }

    public void setReposCsvPath(@Nullable Path reposCsvPath) {
        this.reposCsvPath = reposCsvPath;
    }

    public ScmConfiguration getScm() {
        return scm;
    }

    public void setScm(ScmConfiguration scm) {
        this.scm = scm;
    }
}
