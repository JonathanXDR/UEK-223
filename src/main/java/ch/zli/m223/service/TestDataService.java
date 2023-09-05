package ch.zli.m223.service;

import java.time.LocalDate;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Feedback;
import ch.zli.m223.model.RoleEnum;
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
        public void clearData() {
                entityManager.createNativeQuery("TRUNCATE TABLE Booking RESTART IDENTITY CASCADE")
                                .executeUpdate();
                entityManager.createNativeQuery("TRUNCATE TABLE ApplicationUser RESTART IDENTITY CASCADE")
                                .executeUpdate();
        }

        @Transactional
        void generateTestData(@Observes StartupEvent event) {
                clearData();

                // ApplicationUsers
                var ApplicationUserA = new ApplicationUser("application-user-a@user.com", "User A first name",
                                "User A last name",
                                "ApplicationUserA", RoleEnum.ADMIN);
                entityManager.persist(ApplicationUserA);

                var ApplicationUserB = new ApplicationUser("application-user-b@user.com", "User B first name",
                                "User B last name",
                                "ApplicationUserB", RoleEnum.MEMBER);
                entityManager.persist(ApplicationUserB);

                var ApplicationUserC = new ApplicationUser("application-user-c@user.com", "User C first name",
                                "User C last name",
                                "ApplicationUserC", RoleEnum.MEMBER);
                entityManager.persist(ApplicationUserC);

                // Bookings
                var BookingA = new Booking(LocalDate.now(), TimeFrameEnum.MORNING, StatusEnum.PENDING,
                                ApplicationUserA);
                entityManager.persist(BookingA);

                var BookingB = new Booking(LocalDate.now(), TimeFrameEnum.AFTERNOON, StatusEnum.CONFIRMED,
                                ApplicationUserB);
                entityManager.persist(BookingB);

                var BookingC = new Booking(LocalDate.now(), TimeFrameEnum.FULL_DAY, StatusEnum.DECLINED,
                                ApplicationUserC);
                entityManager.persist(BookingC);

                // Feedbacks
                var FeedbackA = new Feedback("Feedback A title", "Feedback A description",
                                ApplicationUserA);
                entityManager.persist(FeedbackA);

                var FeedbackB = new Feedback("Feedback B title", "Feedback B description",
                                ApplicationUserB);
                entityManager.persist(FeedbackB);

                var FeedbackC = new Feedback("Feedback C title", "Feedback C description",
                                ApplicationUserC);
                entityManager.persist(FeedbackC);
        }
}
