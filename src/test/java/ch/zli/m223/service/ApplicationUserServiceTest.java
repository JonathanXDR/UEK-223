package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.enumeration.RoleEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class ApplicationUserServiceTest {

  @Inject
  ApplicationUserService service;

  @Inject
  TestDataServiceTest testDataServiceTest;

  @BeforeEach
  public void reset() {
    testDataServiceTest.generateTestData(null);
  }

  @Test
  public void testFindByEmail() {
    var user = service.findByEmail("application-user-a@user.com");
    assertTrue(user.isPresent());
  }

  @Test
  public void firstUserIsAdmin() {
    testDataServiceTest.clearData();

    var payload = new ApplicationUser("application-user-a@user.com", "User A first name", "User A last name",
        "ApplicationUserA", null);
    var user = service.createApplicationUser(payload);
    assertEquals(RoleEnum.ADMIN, user.getRole());
  }
}
