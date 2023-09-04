package ch.zli.m223.model;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  @NotNull
  private LocalDate date;

  @Column(nullable = false)
  @NotBlank(message = "Title may not be blank")
  private String title;

  @Column(nullable = false)
  @NotBlank(message = "Description may not be blank")
  private String description;

  @ManyToOne
  private ApplicationUser applicationUser;

  public Feedback(
      String title,
      String description,
      ApplicationUser applicationUser) {
    this.date = LocalDate.now();
    this.title = title;
    this.description = description;
    this.applicationUser = applicationUser;
  }

  public Feedback() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = LocalDate.now();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ApplicationUser getApplicationUser() {
    return applicationUser;
  }

  public void setApplicationUser(ApplicationUser applicationUser) {
    this.applicationUser = applicationUser;
  }
}
