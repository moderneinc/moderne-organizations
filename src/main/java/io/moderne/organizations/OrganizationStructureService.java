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
    private static final String ORGS_CSV = "orgs.csv";
    private static final String REPOS_CSV = "repos.csv";

    public Map<String, OrganizationRepositories> readOrganizationStructure() {
        Map<String, Set<RepositoryInput>> organizationRepositoryMap = readOrganizationRepositoryMap();
        Map<String, OrganizationRepositories> organizations = new LinkedHashMap<>();
        List<CommitOption> allCommitOptions = List.of(CommitOption.values());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(ORGS_CSV).getInputStream()))) {
            reader.readLine(); // skip header
            reader.lines()
                    .forEach(line -> {
                        String[] fields = line.split(",", 4); // keeps empty fields
                        String id = fields[0].trim();
                        String name = fields[1].trim();
                        String parent = fields[2].trim();
                        String commitOptionList = fields[3].trim();
                        List<CommitOption> commitOptions;
                        if (commitOptionList.isEmpty()) {
                            commitOptions = allCommitOptions;
                        } else {
                            commitOptions = Arrays.stream(commitOptionList.split("\\|")).map(CommitOption::valueOf).toList();
                        }
                        OrganizationRepositories organizationRepositories = new OrganizationRepositories(id, name, organizationRepositoryMap.getOrDefault(id, Set.of()), commitOptions, parent);
                        organizations.put(id, organizationRepositories);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return organizations;
    }

    public Map<String, Set<RepositoryInput>> readOrganizationRepositoryMap() {
        Map<String, Set<RepositoryInput>> organizationRepositories = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(REPOS_CSV).getInputStream()))) {
            reader.readLine(); // skip header
            reader.lines()
                    .forEach(line -> {
                        String[] fields = line.split(",");
                        String cloneUrl = fields[0].trim();
                        String branch = fields[1].trim();
                        String organization = fields[2].trim();
                        Matcher matcher = GITHUB_PATTERN.matcher(cloneUrl);
                        if (!matcher.find()) {
                            return;
                        }
                        String origin = "github.com";
                        String path = matcher.group(1);
                        organizationRepositories.compute(organization, (k, v) -> {
                            if (v == null) {
                                v = new LinkedHashSet<>();
                            }
                            v.add(new RepositoryInput(path, origin, branch));
                            return v;
                        });
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return organizationRepositories;
    }
}
