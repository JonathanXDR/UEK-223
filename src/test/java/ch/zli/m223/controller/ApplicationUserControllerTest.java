package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.RoleEnum;
import ch.zli.m223.service.TestDataService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(ApplicationUserController.class)
public class ApplicationUserControllerTest {

  @Inject
  TestDataService testDataService;

  @BeforeEach
  public void reset() {
    testDataService.generateTestData(null);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testIndexEndpoint() {
    given().when().get().then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
  }

  @Test
  public void testPostEndpoint() {
    var payload = new ApplicationUser("application-user-b@user.com", "User B first name", "User B last name",
        "ApplicationUserB",
        RoleEnum.MEMBER);

    given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200)
        .body("email", is("application-user-b@user.com"));
  }

  @Test
  public void testPostInvalid() {
    var payload = new ApplicationUser();
    payload.setEmail("ThisIsNotAnEmail");
    payload.setPassword("Short");

    given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(400);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testPutEndpoint() {
    var payload = new ApplicationUser("application-user-a@user.com", "User A first name", "User A last name",
        "ApplicationUserA",
        RoleEnum.ADMIN);

    var response = given().when().contentType(ContentType.JSON).body(payload).put("/3");
    response.then().statusCode(200);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testDeleteEndpoint() {
    given().when().delete("/2").then().statusCode(204);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testDeleteNotFound() {
    given().when().delete("/100").then().statusCode(404);
  }
}
