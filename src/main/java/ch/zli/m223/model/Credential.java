package ch.zli.m223.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class Credential {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Length(min = 8)
  private String password;

  public Credential(
    @NotBlank @Email String email,
    @NotBlank @Length(min = 8) String password
  ) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
