package io.moderne.organizations;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "moderne.scm")
public class ScmConfiguration {
    private List<ScmRepository> repositories;
    private boolean allowMissingScmOrigins;

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
            GITHUB, BITBUCKET_CLOUD, GITLAB, BITBUCKET
        }
    }

    public List<ScmRepository> getRepositories() {
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
}
