package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class CommitInput {
  private String message;

  private String extendedMessage;

  public CommitInput() {
  }

  public CommitInput(String message, String extendedMessage) {
    this.message = message;
    this.extendedMessage = extendedMessage;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getExtendedMessage() {
    return extendedMessage;
  }

  public void setExtendedMessage(String extendedMessage) {
    this.extendedMessage = extendedMessage;
  }

  @Override
  public String toString() {
    return "CommitInput{" + "message='" + message + "'," +"extendedMessage='" + extendedMessage + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        CommitInput that = (CommitInput) o;
        return java.util.Objects.equals(message, that.message) &&
                            java.util.Objects.equals(extendedMessage, that.extendedMessage);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(message, extendedMessage);
  }

  public static io.moderne.organizations.types.CommitInput.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String message;

    private String extendedMessage;

    public CommitInput build() {
                  io.moderne.organizations.types.CommitInput result = new io.moderne.organizations.types.CommitInput();
                      result.message = this.message;
          result.extendedMessage = this.extendedMessage;
                      return result;
    }

    public io.moderne.organizations.types.CommitInput.Builder message(String message) {
      this.message = message;
      return this;
    }

    public io.moderne.organizations.types.CommitInput.Builder extendedMessage(
        String extendedMessage) {
      this.extendedMessage = extendedMessage;
      return this;
    }
  }
}
