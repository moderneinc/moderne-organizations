package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Commit {
  private String message;

  public Commit() {
  }

  public Commit(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Commit{" + "message='" + message + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit that = (Commit) o;
        return java.util.Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(message);
  }

  public static io.moderne.organizations.types.Commit.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String message;

    public Commit build() {
      io.moderne.organizations.types.Commit result = new io.moderne.organizations.types.Commit();
          result.message = this.message;
          return result;
    }

    public io.moderne.organizations.types.Commit.Builder message(String message) {
      this.message = message;
      return this;
    }
  }
}
