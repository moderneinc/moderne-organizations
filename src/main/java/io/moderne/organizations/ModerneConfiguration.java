package io.moderne.organizations;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "moderne")
public class ModerneConfiguration {

    @Nullable
    private Path reposCsvPath;

    public @Nullable Path getReposCsvPath() {
        return reposCsvPath;
    }

    public void setReposCsvPath(@Nullable Path reposCsvPath) {
        this.reposCsvPath = reposCsvPath;
    }
}
