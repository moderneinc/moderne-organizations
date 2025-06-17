package io.moderne.organizations;

import java.lang.String;

public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String UserOrganizationsPages = "userOrganizationsPages";

    public static final String CommitMessage = "commitMessage";

    public static class ORGANIZATIONSPAGES_INPUT_ARGUMENT {
      public static final String After = "after";

      public static final String First = "first";

      public static final String Before = "before";

      public static final String Last = "last";
    }

    public static class USERORGANIZATIONS_INPUT_ARGUMENT {
      public static final String User = "user";

      public static final String At = "at";
    }

    public static class USERORGANIZATIONSPAGES_INPUT_ARGUMENT {
      public static final String User = "user";

      public static final String At = "at";

      public static final String After = "after";

      public static final String First = "first";

      public static final String Before = "before";

      public static final String Last = "last";
    }

    public static class ORGANIZATION_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    public static class COMMITMESSAGE_INPUT_ARGUMENT {
      public static final String CommitInput = "commitInput";

      public static final String Repository = "repository";
    }
  }

  public static class COMMIT {
    public static final String TYPE_NAME = "Commit";

    public static final String Message = "message";

    public static final String ExtendedMessage = "extendedMessage";
  }

  public static class ORGANIZATION {
    public static final String TYPE_NAME = "Organization";

    public static final String Id = "id";
  }

  public static class ORGANIZATIONCONNECTION {
    public static final String TYPE_NAME = "OrganizationConnection";

    public static final String Edges = "edges";

    public static final String PageInfo = "pageInfo";

    public static final String Count = "count";
  }

  public static class ORGANIZATIONEDGE {
    public static final String TYPE_NAME = "OrganizationEdge";

    public static final String Node = "node";

    public static final String Cursor = "cursor";
  }

  public static class PAGEINFO {
    public static final String TYPE_NAME = "PageInfo";

    public static final String HasPreviousPage = "hasPreviousPage";

    public static final String HasNextPage = "hasNextPage";

    public static final String StartCursor = "startCursor";

    public static final String EndCursor = "endCursor";
  }

  public static class COMMITINPUT {
    public static final String TYPE_NAME = "CommitInput";

    public static final String Message = "message";

    public static final String ExtendedMessage = "extendedMessage";
  }

  public static class USER {
    public static final String TYPE_NAME = "User";

    public static final String Email = "email";
  }

  public static class REPOSITORYINPUT {
    public static final String TYPE_NAME = "RepositoryInput";

    public static final String Path = "path";

    public static final String Origin = "origin";

    public static final String Branch = "branch";
  }
}
