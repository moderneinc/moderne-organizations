package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Option {
  private String name;

  private String value;

  public Option() {
  }

  public Option(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Option{" + "name='" + name + "'," +"value='" + value + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option that = (Option) o;
        return java.util.Objects.equals(name, that.name) &&
                            java.util.Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(name, value);
  }

  public static io.moderne.organizations.types.Option.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String name;

    private String value;

    public Option build() {
                  io.moderne.organizations.types.Option result = new io.moderne.organizations.types.Option();
                      result.name = this.name;
          result.value = this.value;
                      return result;
    }

    public io.moderne.organizations.types.Option.Builder name(String name) {
      this.name = name;
      return this;
    }

    public io.moderne.organizations.types.Option.Builder value(String value) {
      this.value = value;
      return this;
    }
  }
}
