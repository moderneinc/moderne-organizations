package io.moderne.organizations.types;

import java.lang.Override;
import java.lang.String;

public class Option {
  /**
   * Example: `methodPattern`
   */
  private String name;

  /**
   * Example: `java.util.List add(..)`
   */
  private Object value;

  public Option() {
  }

  public Option(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Example: `methodPattern`
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Example: `java.util.List add(..)`
   */
  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Option{" + "name='" + name + "'," +"value='" + value + "'" +"}";
  }

  @Override
  public boolean equals(java.lang.Object o) {
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
    /**
     * Example: `methodPattern`
     */
    private String name;

    /**
     * Example: `java.util.List add(..)`
     */
    private Object value;

    public Option build() {
                  io.moderne.organizations.types.Option result = new io.moderne.organizations.types.Option();
                      result.name = this.name;
          result.value = this.value;
                      return result;
    }

    /**
     * Example: `methodPattern`
     */
    public io.moderne.organizations.types.Option.Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Example: `java.util.List add(..)`
     */
    public io.moderne.organizations.types.Option.Builder value(Object value) {
      this.value = value;
      return this;
    }
  }
}
