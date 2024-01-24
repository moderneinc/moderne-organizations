package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class Organization {
  private String id;

  private String name;

  /**
   * Ordered list of commit options as they should appear in the UI.
   */
  private List<CommitOption> commitOptions;

  private Organization _parent;

  /**
   * The dashboard for this organization.
   */
  private DashboardConfiguration dashboard;

  public Organization() {
  }

  public Organization(String id, String name, List<CommitOption> commitOptions,
      Organization _parent, DashboardConfiguration dashboard) {
    this.id = id;
    this.name = name;
    this.commitOptions = commitOptions;
    this._parent = _parent;
    this.dashboard = dashboard;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Ordered list of commit options as they should appear in the UI.
   */
  public List<CommitOption> getCommitOptions() {
    return commitOptions;
  }

  public void setCommitOptions(List<CommitOption> commitOptions) {
    this.commitOptions = commitOptions;
  }

  public Organization getParent() {
    return _parent;
  }

  public void setParent(Organization _parent) {
    this._parent = _parent;
  }

  /**
   * The dashboard for this organization.
   */
  public DashboardConfiguration getDashboard() {
    return dashboard;
  }

  public void setDashboard(DashboardConfiguration dashboard) {
    this.dashboard = dashboard;
  }

  @Override
  public String toString() {
    return "Organization{" + "id='" + id + "'," +"name='" + name + "'," +"commitOptions='" + commitOptions + "'," +"parent='" + _parent + "'," +"dashboard='" + dashboard + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(name, that.name) &&
                            java.util.Objects.equals(commitOptions, that.commitOptions) &&
                            java.util.Objects.equals(_parent, that._parent) &&
                            java.util.Objects.equals(dashboard, that.dashboard);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, name, commitOptions, _parent, dashboard);
  }

  public static io.moderne.organizations.types.Organization.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private String name;

    /**
     * Ordered list of commit options as they should appear in the UI.
     */
    private List<CommitOption> commitOptions;

    private Organization _parent;

    /**
     * The dashboard for this organization.
     */
    private DashboardConfiguration dashboard;

    public Organization build() {
                  io.moderne.organizations.types.Organization result = new io.moderne.organizations.types.Organization();
                      result.id = this.id;
          result.name = this.name;
          result.commitOptions = this.commitOptions;
          result._parent = this._parent;
          result.dashboard = this.dashboard;
                      return result;
    }

    public io.moderne.organizations.types.Organization.Builder id(String id) {
      this.id = id;
      return this;
    }

    public io.moderne.organizations.types.Organization.Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Ordered list of commit options as they should appear in the UI.
     */
    public io.moderne.organizations.types.Organization.Builder commitOptions(
        List<CommitOption> commitOptions) {
      this.commitOptions = commitOptions;
      return this;
    }

    public io.moderne.organizations.types.Organization.Builder _parent(Organization _parent) {
      this._parent = _parent;
      return this;
    }

    /**
     * The dashboard for this organization.
     */
    public io.moderne.organizations.types.Organization.Builder dashboard(
        DashboardConfiguration dashboard) {
      this.dashboard = dashboard;
      return this;
    }
  }
}
