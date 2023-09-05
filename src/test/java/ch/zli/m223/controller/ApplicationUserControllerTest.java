package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.RoleEnum;
import ch.zli.m223.service.TestDataServiceTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(ApplicationUserController.class)
public class ApplicationUserControllerTest {

  @Inject
  TestDataServiceTest testDataServiceTest;

  @BeforeEach
  public void reset() {
    testDataServiceTest.generateTestData(null);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testIndexEndpoint() {
    given().when().get("/users").then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
  }

  @Test
  public void testPostEndpoint() {
    var payload = new ApplicationUser("application-user-b@user.com", "User B first name", "User B last name",
        "ApplicationUserB",
        RoleEnum.MEMBER);

    given().when().contentType(ContentType.JSON).body(payload).post("/users").then().statusCode(200)
        .body("email", is("application-user-b@user.com"));
  }

  @Test
  public void testPostBadRequest() {
    var payload = new ApplicationUser();
    payload.setEmail("ThisIsNotAnEmail");
    payload.setPassword("Short");

    given().when().contentType(ContentType.JSON).body(payload).post("/users").then().statusCode(400);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testPutEndpoint() {
    var payload = new ApplicationUser("application-user-a@user.com", "User A first name", "User A last name",
        "ApplicationUserA",
        RoleEnum.ADMIN);

    var response = given().when().contentType(ContentType.JSON).body(payload).put("/users" + 3);
    response.then().statusCode(200);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testPutNotFound() {
    given().when().put("/users" + 100).then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testDeleteEndpoint() {
    given().when().delete("/users" + 2).then().statusCode(200);
  }

  @Test
  @TestSecurity(user = "application-user-a@user.com", roles = "Admin")
  public void testDeleteNotFound() {
    given().when().delete("/users" + 100).then().statusCode(404);
  }
}
