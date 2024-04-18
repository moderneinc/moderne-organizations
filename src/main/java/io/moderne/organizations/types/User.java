package io.moderne.organizations.types;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class User {
  private String email;

  public User() {
  }

  public User(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" + "email='" + email + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
        User that = (User) o;
        return java.util.Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(email);
  }

  public static io.moderne.organizations.types.User.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String email;

    public User build() {
      io.moderne.organizations.types.User result = new io.moderne.organizations.types.User();
          result.email = this.email;
          return result;
    }

    public io.moderne.organizations.types.User.Builder email(String email) {
      this.email = email;
      return this;
    }
  }
}
