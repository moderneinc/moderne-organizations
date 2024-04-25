package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class DevCenterVisualizationCard {
  private String visualizationId;

  private List<Option> visualizationOptions;

  public DevCenterVisualizationCard() {
  }

  public DevCenterVisualizationCard(String visualizationId, List<Option> visualizationOptions) {
    this.visualizationId = visualizationId;
    this.visualizationOptions = visualizationOptions;
  }

  public String getVisualizationId() {
    return visualizationId;
  }

  public void setVisualizationId(String visualizationId) {
    this.visualizationId = visualizationId;
  }

  public List<Option> getVisualizationOptions() {
    return visualizationOptions;
  }

  public void setVisualizationOptions(List<Option> visualizationOptions) {
    this.visualizationOptions = visualizationOptions;
  }

  @Override
  public String toString() {
    return "DevCenterVisualizationCard{" + "visualizationId='" + visualizationId + "'," +"visualizationOptions='" + visualizationOptions + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        DevCenterVisualizationCard that = (DevCenterVisualizationCard) o;
        return java.util.Objects.equals(visualizationId, that.visualizationId) &&
                            java.util.Objects.equals(visualizationOptions, that.visualizationOptions);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(visualizationId, visualizationOptions);
  }

  public static io.moderne.organizations.types.DevCenterVisualizationCard.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String visualizationId;

    private List<Option> visualizationOptions;

    public DevCenterVisualizationCard build() {
                  io.moderne.organizations.types.DevCenterVisualizationCard result = new io.moderne.organizations.types.DevCenterVisualizationCard();
                      result.visualizationId = this.visualizationId;
          result.visualizationOptions = this.visualizationOptions;
                      return result;
    }

    public io.moderne.organizations.types.DevCenterVisualizationCard.Builder visualizationId(
        String visualizationId) {
      this.visualizationId = visualizationId;
      return this;
    }

    public io.moderne.organizations.types.DevCenterVisualizationCard.Builder visualizationOptions(
        List<Option> visualizationOptions) {
      this.visualizationOptions = visualizationOptions;
      return this;
    }
  }
}
