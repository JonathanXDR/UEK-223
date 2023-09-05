package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.dto.Credential;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class AuthServiceTest {

  @Inject
  AuthService service;

  @Inject
  TestDataServiceTest testDataServiceTest;

  @BeforeEach
  public void reset() {
    testDataServiceTest.generateTestData(null);
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
