package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class CommitInput {
  private String message;

  public CommitInput() {
  }

  public CommitInput(String message) {
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
    return "CommitInput{" + "message='" + message + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommitInput that = (CommitInput) o;
        return java.util.Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(message);
  }

  public static io.moderne.organizations.types.CommitInput.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String message;

    public CommitInput build() {
      io.moderne.organizations.types.CommitInput result = new io.moderne.organizations.types.CommitInput();
          result.message = this.message;
          return result;
    }

    public io.moderne.organizations.types.CommitInput.Builder message(String message) {
      this.message = message;
      return this;
    }
  }
}
