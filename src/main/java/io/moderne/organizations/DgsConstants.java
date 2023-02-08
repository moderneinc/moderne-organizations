package io.moderne.organizations;

import java.lang.String;

public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String Organizations = "organizations";

    public static final String DefaultOrganization = "defaultOrganization";

    public static class ORGANIZATIONS_INPUT_ARGUMENT {
      public static final String Repository = "repository";
    }

    public static class DEFAULTORGANIZATION_INPUT_ARGUMENT {
      public static final String Email = "email";
    }
  }

  public static class ORGANIZATION {
    public static final String TYPE_NAME = "Organization";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CommitOptions = "commitOptions";
  }

  public static class REPOSITORYINPUT {
    public static final String TYPE_NAME = "RepositoryInput";

    public static final String Path = "path";

    public static final String Origin = "origin";

    public static final String Branch = "branch";
  }
}
