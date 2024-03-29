package ch.zli.m223.model;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import ch.zli.m223.model.enumeration.StatusEnum;
import ch.zli.m223.model.enumeration.TimeFrameEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TimeFrameEnum timeFrame;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @ManyToOne
  private ApplicationUser applicationUser;

  public Booking(
      LocalDate date,
      TimeFrameEnum timeFrame,
      StatusEnum status,
      ApplicationUser applicationUser) {
    this.date = date;
    this.timeFrame = timeFrame;
    this.status = status;
    this.applicationUser = applicationUser;
  }

  public Booking() {
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
    this.date = date;
  }

  public TimeFrameEnum getTimeFrame() {
    return timeFrame;
  }

  public void setTimeFrame(TimeFrameEnum timeFrame) {
    this.timeFrame = timeFrame;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ApplicationUser getApplicationUser() {
    return applicationUser;
  }

  public void setApplicationUser(ApplicationUser applicationUser) {
    this.applicationUser = applicationUser;
  }
}
