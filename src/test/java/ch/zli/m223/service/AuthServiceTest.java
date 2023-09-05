package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Credential;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AuthServiceTest {

  @Inject
  AuthService service;

  @Inject
  TestDataService testDataService;

  @BeforeEach
  public void reset() {
    testDataService.generateTestData(null);
  }

  @Test
  @TestTransaction
  public void testAuthentication() {
    var credential = new Credential("application-user-a@user.com", "ApplicationUserA");
    var response = service.authenticate(credential);
    assertEquals(response.getStatus(), 200);
    assertTrue(response.getHeaders().get("Authorization").get(0).toString().startsWith("Bearer"));
    assertNotNull(response.getCookies().get("coworking"));
  }
}
