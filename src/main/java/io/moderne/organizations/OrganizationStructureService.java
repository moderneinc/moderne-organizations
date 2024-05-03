package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;
import io.moderne.organizations.types.RepositoryInput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This service could also directly call your SCM api to determine the available repositories
 */
@Service
public class OrganizationStructureService {
    private static final Pattern GITHUB_PATTERN = Pattern.compile("github.com/(.*)");
    private static final String REPOS_CSV = "repos.csv";

    public Map<String, OrganizationRepositories> readOrganizationStructure() {
        Map<String, OrganizationRepositories> organizations = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(REPOS_CSV).getInputStream()))) {
            reader.readLine(); // skip header
            reader.lines()
                    .forEach(line -> {
                        String[] fields = line.split(",", Integer.MAX_VALUE);
                        if (fields.length < 3) {
                            throw new IllegalStateException("repos.csv lines should have at least 1 organization");
                        }
                        String cloneUrl = fields[0].trim();
                        String branch = fields[1].trim();

                        Matcher matcher = GITHUB_PATTERN.matcher(cloneUrl);
                        if (!matcher.find()) {
                            return;
                        }
                        String origin = "github.com";
                        String path = matcher.group(1);

                        for (int i = 2; i < fields.length; i++) {
                            boolean first = i == 2;
                            String organization = fields[i].trim();
                            String parent = fields.length > i + 1 ? fields[i + 1].trim() : null;
                            organizations.compute(organization, (k, v) -> {
                                if (v == null) {
                                    v = new OrganizationRepositories(organization, organization, new LinkedHashSet<>(), List.of(CommitOption.values()), parent);
                                }
                                if (!Objects.equals(v.parent(), parent)) {
                                    throw new IllegalStateException("An organization parent should be the same for each repository. %s has parent %s and %s".formatted(organization, v.parent(), parent));
                                }
                                if (first) {
                                    v.repositories().add(new RepositoryInput(path, origin, branch));
                                }
                                return v;
                            });
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return organizations;
    }
}
