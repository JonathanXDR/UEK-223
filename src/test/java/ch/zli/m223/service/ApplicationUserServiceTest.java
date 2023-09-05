package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.RoleEnum;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ApplicationUserServiceTest {

  @Inject
  ApplicationUserService service;

  @Inject
  TestDataService testDataService;

  @BeforeEach
  public void reset() {
    testDataService.generateTestData(null);
  }

  @Test
  public void testFindByEmail() {
    var user = service.findByEmail("application-user-a@user.com");
    assertTrue(user.isPresent());
  }

  @Test
  public void firstUserIsAdmin() {
    testDataService.clearData();

    var payload = new ApplicationUser("application-user-a@user.com", "User A first name", "User A last name",
        "ApplicationUserA", null);
    var user = service.createApplicationUser(payload);
    assertEquals(RoleEnum.ADMIN, user.getRole());
  }
}
