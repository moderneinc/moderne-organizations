package io.moderne.organizations;

∑import jakarta.annotation.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "moderne.scm")
public class ScmConfiguration {
    private List<ScmRepository> repositories;
    private boolean allowMissingScmOrigins;

    @Nullable
    private Path reposCsvPath;

    public static class ScmRepository {
        String origin;
        Type type;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public enum Type {
            GITHUB, BITBUCKET_CLOUD, GITLAB, BITBUCKET, AZURE_DEVOPS
        }
    }

    public List<ScmRepository> getRepositories() {
        if (repositories == null) {
            repositories = new ArrayList<>();
        }
        return repositories;
    }

    public void setRepositories(List<ScmRepository> repositories) {
        this.repositories = repositories;
    }

    public boolean isAllowMissingScmOrigins() {
        return allowMissingScmOrigins;
    }

    public void setAllowMissingScmOrigins(boolean allowMissingScmOrigins) {
        this.allowMissingScmOrigins = allowMissingScmOrigins;
    }

    @Nullable
    public Path getReposCsvPath() {
        return reposCsvPath;
    }

    public void setReposCsvPath(@Nullable Path reposCsvPath) {
        this.reposCsvPath = reposCsvPath;
    }
}
