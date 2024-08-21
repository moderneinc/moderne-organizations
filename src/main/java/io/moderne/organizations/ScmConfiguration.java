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

        @Deprecated // Use baseUrl instead
        @Nullable
        private String origin;

        @Deprecated // Use gitRemoteService in stead
        @Nullable
        Type type;

        @Nullable
        private Service gitRemoteService;

        @Nullable
        private URI baseUrl;

        @Nullable
        private List<URI> alternativeUrls;

        public List<URI> getAlternativeUrls() {
            if (baseUrl != null) {
                return Objects.requireNonNullElse(alternativeUrls, Collections.emptyList());
            }
            // backwards compatibility
            if (alternativeUrls != null) {
                throw new IllegalStateException("Using alternativeUrls without baseUrl is not supported");
            }
            if (originHasProtocol()) {
                return Collections.emptyList();
            }
            return List.of(URI.create("ssh://" + origin));
        }

        public URI getBaseUrl() {
            if (baseUrl != null) {
                return baseUrl;
            }
            // backwards compatibility
            if (origin == null) {
                throw new IllegalStateException("Either baseUrl or origin must be set");
            }
            if (originHasProtocol()) {
                return URI.create(origin);
            }
            return URI.create("https://" + origin);

        }

        private boolean originHasProtocol() {
            return origin != null && (origin.startsWith("ssh://") || origin.startsWith("https:/") || origin.startsWith("http://"));
        }

        public void setOrigin(@Nullable String origin) {
            this.origin = origin;
        }

        public void setAlternativeUrls(@Nullable List<URI> alternativeUrls) {
            this.alternativeUrls = alternativeUrls;
        }

        public void setBaseUrl(@Nullable URI baseUrl) {
            this.baseUrl = baseUrl;
        }

        public @Nullable String getOrigin() {
            return origin;
        }

        public @Nullable Type getType() {
            return type;
        }

        public void setType(@Nullable Type type) {
            this.type = type;
        }

        public Service getGitRemoteService() {
            if (gitRemoteService == null && type != null) {
                return switch (type) {
                    case GITHUB -> Service.GitHub;
                    case BITBUCKET_CLOUD -> Service.BitbucketCloud;
                    case GITLAB -> Service.GitLab;
                    case BITBUCKET -> Service.Bitbucket;
                    case AZURE_DEVOPS -> Service.AzureDevOps;
                };
            }
            return gitRemoteService;
        }

        public void setGitRemoteService(@Nullable Service gitRemoteService) {
            this.gitRemoteService = gitRemoteService;
        }
    }

    enum Type {
        GITHUB, BITBUCKET_CLOUD, GITLAB, BITBUCKET, AZURE_DEVOPS
    }

}