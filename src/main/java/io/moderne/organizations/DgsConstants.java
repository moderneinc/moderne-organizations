package io.moderne.organizations;

import java.lang.String;

public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String Organizations = "organizations";

    public static final String AllOrganizations = "allOrganizations";

    public static final String UserOrganizations = "userOrganizations";

    public static final String Organization = "organization";

    public static class ORGANIZATIONS_INPUT_ARGUMENT {
      public static final String Repository = "repository";
    }

    public static class USERORGANIZATIONS_INPUT_ARGUMENT {
      public static final String User = "user";

      public static final String At = "at";
    }

    public static class ORGANIZATION_INPUT_ARGUMENT {
      public static final String Id = "id";
    }
  }

  public static class ORGANIZATION {
    public static final String TYPE_NAME = "Organization";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CommitOptions = "commitOptions";

    public static final String Parent = "parent";

    public static final String DevCenter = "devCenter";
  }

  public static class DEVCENTER {
    public static final String TYPE_NAME = "DevCenter";

    public static final String UpgradesAndMigrations = "upgradesAndMigrations";

    public static final String Visualizations = "visualizations";

    public static final String Security = "security";
  }

  public static class DEVCENTERVISUALIZATIONCARD {
    public static final String TYPE_NAME = "DevCenterVisualizationCard";

    public static final String VisualizationId = "visualizationId";

    public static final String VisualizationOptions = "visualizationOptions";
  }

  public static class MEASURE {
    public static final String TYPE_NAME = "Measure";

    public static final String Name = "name";

    public static final String Recipe = "recipe";
  }

  public static class DEVCENTERRECIPECARD {
    public static final String TYPE_NAME = "DevCenterRecipeCard";

    public static final String Title = "title";

    public static final String Measures = "measures";

    public static final String Fix = "fix";
  }

  public static class DEVCENTERRECIPE {
    public static final String TYPE_NAME = "DevCenterRecipe";

    public static final String RecipeId = "recipeId";

    public static final String Options = "options";
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
