package io.moderne.organizations;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
class ReposCsvRow {

    @Setter
    @JsonProperty("cloneUrl")
    private String cloneUrl;

    @Setter
    @JsonProperty("branch")
    private String branch;

    private final List<String> organizations = new ArrayList<>();

    @JsonAnySetter
    public void setOrganizations(String key, String value) {
        organizations.add(value);
    }

    @Override
    public String toString() {
        return "CsvRow{" +
                "cloneUrl='" + cloneUrl + '\'' +
                ", branch='" + branch + '\'' +
                ", orgs=" + organizations +
                '}';
    }
}