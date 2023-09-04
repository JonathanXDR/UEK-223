package ch.zli.m223.service;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Feedback;
import ch.zli.m223.model.StatusEnum;
import ch.zli.m223.model.TimeFrameEnum;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@IfBuildProfile("dev")
@ApplicationScoped
public class TestDataService {

  @Inject
  EntityManager entityManager;

  @Transactional
  void generateTestData(@Observes StartupEvent event) {
    // ApplicationUsers
    var ApplicationUserA = new ApplicationUser();
    ApplicationUserA.setFirstName("User A first name");
    ApplicationUserA.setLastName("User A last name");
    ApplicationUserA.setEmail("application-user-a@user.com");
    ApplicationUserA.setPassword("ApplicationUserA");
    entityManager.persist(ApplicationUserA);

    var ApplicationUserB = new ApplicationUser();
    ApplicationUserB.setFirstName("User B first name");
    ApplicationUserB.setLastName("User B last name");
    ApplicationUserB.setEmail("application-user-b@user.com");
    ApplicationUserB.setPassword("ApplicationUserB");
    entityManager.persist(ApplicationUserB);

    var ApplicationUserC = new ApplicationUser();
    ApplicationUserC.setFirstName("User C first name");
    ApplicationUserC.setLastName("User C last name");
    ApplicationUserC.setEmail("application-user-c@user.com");
    ApplicationUserC.setPassword("ApplicationUserC");
    entityManager.persist(ApplicationUserC);

    // Bookings
    var BookingA = new Booking();
    BookingA.setDate(java.time.LocalDate.now());
    BookingA.setTimeFrame(TimeFrameEnum.MORNING);
    BookingA.setStatus(StatusEnum.PENDING);
    BookingA.setApplicationUser(ApplicationUserA);
    entityManager.persist(BookingA);

    var BookingB = new Booking();
    BookingB.setDate(java.time.LocalDate.now());
    BookingB.setTimeFrame(TimeFrameEnum.AFTERNOON);
    BookingB.setStatus(StatusEnum.PENDING);
    BookingB.setApplicationUser(ApplicationUserB);
    entityManager.persist(BookingB);

    var BookingC = new Booking();
    BookingC.setDate(java.time.LocalDate.now());
    BookingC.setTimeFrame(TimeFrameEnum.FULL_DAY);
    BookingC.setStatus(StatusEnum.PENDING);
    BookingC.setApplicationUser(ApplicationUserC);
    entityManager.persist(BookingC);

    // Feedbacks
    var FeedbackA = new Feedback();
    FeedbackA.setDate(java.time.LocalDate.now());
    FeedbackA.setTitle("Feedback A title");
    FeedbackA.setDescription("Feedback A description");
    FeedbackA.setApplicationUser(ApplicationUserA);
    entityManager.persist(FeedbackA);

    var FeedbackB = new Feedback();
    FeedbackB.setDate(java.time.LocalDate.now());
    FeedbackB.setTitle("Feedback B title");
    FeedbackB.setDescription("Feedback B description");
    FeedbackB.setApplicationUser(ApplicationUserB);
    entityManager.persist(FeedbackB);

    var FeedbackC = new Feedback();
    FeedbackC.setDate(java.time.LocalDate.now());
    FeedbackC.setTitle("Feedback C title");
    FeedbackC.setDescription("Feedback C description");
    FeedbackC.setApplicationUser(ApplicationUserC);
    entityManager.persist(FeedbackC);
  }
}
