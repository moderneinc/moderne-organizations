package io.moderne.organizations;

import org.jspecify.annotations.Nullable;
import org.openrewrite.GitRemote;
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
        @Deprecated
        @Nullable
        private String origin;

        private GitRemote.Service type;

        @Nullable
        private URI baseUri;

        @Nullable
        private List<URI> alternativeUris;

        public List<URI> getAlternativeUris() {
            if (baseUri != null) {
                return Objects.requireNonNullElse(alternativeUris, Collections.emptyList());
            }
            {
                // backwards compatibility
                if (alternativeUris != null) {
                    throw new IllegalStateException("Using alternativeUris without baseUri is not supported");
                }
                if (originHasProtocol()) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(URI.create("ssh://" + origin));
            }
        }

        public URI getBaseUri() {
            if (baseUri != null) {
                return baseUri;
            }
            {
                // backwards compatibility
                if (origin == null) {
                    throw new IllegalStateException("Either baseUri or origin must be set");
                }
                if (originHasProtocol()) {
                    return URI.create(origin);
                }
                return URI.create("https://" + origin);
            }
        }

        private boolean originHasProtocol() {
            return origin != null && (origin.startsWith("ssh://") || origin.startsWith("https:/") || origin.startsWith("http://"));
        }

        public void setOrigin(@Nullable String origin) {
            this.origin = origin;
        }

        public GitRemote.Service getType() {
            return type;
        }

        public void setType(GitRemote.Service type) {
            this.type = type;
        }

        public void setAlternativeUris(@Nullable List<URI> alternativeUris) {
            this.alternativeUris = alternativeUris;
        }
    }


}