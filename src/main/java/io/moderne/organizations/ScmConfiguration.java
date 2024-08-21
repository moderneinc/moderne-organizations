package io.moderne.organizations;

import org.jspecify.annotations.Nullable;
import org.openrewrite.GitRemote.Service;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ConfigurationProperties(prefix = "moderne.scm")
class ScmConfiguration {
    private boolean allowMissingScmOrigins;
    private List<ScmRepository> repositories;

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

    static class ScmRepository {
        private Service type;
        private URI baseUrl;

        @Nullable
        private List<URI> alternativeUrls;

        public List<URI> getAlternativeUrls() {
            return Objects.requireNonNullElse(alternativeUrls, Collections.emptyList());
        }

        public URI getBaseUrl() {
            return baseUrl;
        }

        public void setAlternativeUrls(@Nullable List<URI> alternativeUrls) {
            this.alternativeUrls = alternativeUrls;
        }

        public void setBaseUrl(@Nullable URI baseUrl) {
            this.baseUrl = baseUrl;
        }

        public Service getType() {
            return type;
        }

        public void setType(@Nullable Service type) {
            this.type = type;
        }
    }
}