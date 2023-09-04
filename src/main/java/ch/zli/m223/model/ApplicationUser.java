package ch.zli.m223.model;

import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@NamedQueries({
    @NamedQuery(name = "ApplicationUser.findByEmail", query = "SELECT u FROM ApplicationUser u WHERE u.email = :email"),
})
public class ApplicationUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @Column(nullable = false)
  @NotBlank(message = "first name may not be blank")
  private String firstName;

  @Column(nullable = false)
  @NotBlank(message = "last name may not be blank")
  private String lastName;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "Email may not be blank")
  @Email
  private String email;

  @Column(nullable = false)
  @Length(min = 8, message = "Password has to be at least 8 characters")
  private String password;

  @OneToMany(mappedBy = "applicationUser")
  private Set<Booking> bookings;

  @OneToMany(mappedBy = "applicationUser")
  private Set<Feedback> feedbacks;

  public ApplicationUser(
      @NotBlank(message = "Email may not be blank") @Email String email,
      @NotBlank(message = "first name may not be blank") String firstName,
      @NotBlank(message = "last name may not be blank") String lastName,
      @Length(min = 8, message = "Password has to be at least 8 characters") String password,
      RoleEnum role) {
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public ApplicationUser() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String username) {
    this.email = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @JsonIgnore
  public Set<Booking> getBookings() {
    return bookings;
  }

  @JsonIgnore
  public Set<Feedback> getFeedbacks() {
    return feedbacks;
  }
}
