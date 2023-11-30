package io.moderne.organizations;

import java.lang.String;

public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String Organizations = "organizations";

    public static final String UserOrganizations = "userOrganizations";

    public static class ORGANIZATIONS_INPUT_ARGUMENT {
      public static final String Repository = "repository";
    }

    public static class USERORGANIZATIONS_INPUT_ARGUMENT {
      public static final String User = "user";

      public static final String At = "at";
    }
  }

  public static class ORGANIZATION {
    public static final String TYPE_NAME = "Organization";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CommitOptions = "commitOptions";

    public static final String Parent = "parent";

    public static final String Dashboard = "dashboard";
  }

  public static class DASHBOARD {
    public static final String TYPE_NAME = "Dashboard";

    public static final String UpgradesAndMigrations = "upgradesAndMigrations";

    public static final String Visualizations = "visualizations";

    public static final String Security = "security";
  }

  public static class DASHBOARDVISUALIZATION {
    public static final String TYPE_NAME = "DashboardVisualization";

    public static final String RecipeId = "recipeId";

    public static final String RecipeOptions = "recipeOptions";

    public static final String VisualizationId = "visualizationId";

    public static final String VisualizationOptions = "visualizationOptions";
  }

  public static class OPTION {
    public static final String TYPE_NAME = "Option";

    public static final String Name = "name";

    public static final String Value = "value";
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
