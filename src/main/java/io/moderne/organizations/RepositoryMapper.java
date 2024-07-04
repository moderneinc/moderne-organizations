package io.moderne.organizations;

import io.moderne.organizations.types.RepositoryInput;
import org.openrewrite.internal.lang.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RepositoryMapper {
    @Nullable
    RepositoryInput determineRepository(String cloneUrl, String branch);

    class OriginBasedRepositoryMapper implements RepositoryMapper {

        Map<String, Pattern> urlPatterns;

        OriginBasedRepositoryMapper(String... origins) {
            urlPatterns = new LinkedHashMap<>();
            for (String origin : origins) {
                Pattern pattern = Pattern.compile(origin + "/(.*)");
                urlPatterns.put(origin, pattern);
            }
        }

        @Nullable
        @Override
        public RepositoryInput determineRepository(String cloneUrl, String branch) {
            for (Map.Entry<String, Pattern> entry : urlPatterns.entrySet()) {
                String origin = entry.getKey();
                Matcher matcher = entry.getValue().matcher(cloneUrl);
                if (matcher.find()) {
                    String path = cleanPath(matcher.group(1));
                    return new RepositoryInput(path, origin, branch);
                }
            }
            return null;
        }

        private static String cleanPath(String path) {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (path.endsWith(".git")) {
                path = path.substring(0, path.length() - 4);
            }
            return path;
        }
    }
}
