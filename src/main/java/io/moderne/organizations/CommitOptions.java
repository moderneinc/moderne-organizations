package io.moderne.organizations;

import io.moderne.organizations.types.CommitOption;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record CommitOptions(Map<String, List<CommitOption>> commitOptions) {

    public static CommitOptions fromInputStream(InputStream commitOptionsInputStream) {
        Map<String, List<CommitOption>> configuredCommitOptions = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(commitOptionsInputStream))) {
            reader.lines().forEach(line -> {
                String[] fields = line.split("=");
                if (fields.length != 2) {
                    throw new IllegalStateException("Commit options should contain <organization>=<Comma separated list of commit options>");
                }
                String organization = fields[0].trim();
                List<CommitOption> options = Arrays.stream(fields[1].trim().split(",")).map(String::trim).map(CommitOption::valueOf).toList();
                configuredCommitOptions.put(organization, options);
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new CommitOptions(configuredCommitOptions);
    }

    public List<CommitOption> find(String organization) {
        return commitOptions().getOrDefault(organization, List.of(CommitOption.values()));
    }
}