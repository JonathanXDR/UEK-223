package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Credential;
import ch.zli.m223.service.TestDataService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(AuthController.class)
public class AuthControllerTest {

  @Inject
  TestDataService testDataService;

  @BeforeEach
  public void reset() {
    testDataService.generateTestData(null);
  }

  @Test
  public void testLoginCorrectMember() {
    var credentials = new Credential("application-user-b@user.com", "ApplicationUserB");
    given().when().contentType(ContentType.JSON).body(credentials).post().then().statusCode(200)
        .body("role", is("MEMBER"));
  }

  @Test
  public void testLoginCorrectAdmin() {
    var credentials = new Credential("application-user-a@user.com", "ApplicationUserA");
    given().when().contentType(ContentType.JSON).body(credentials).post().then().statusCode(200)
        .body("role", is("ADMIN"));
  }

  @Test
  public void testLoginWrongEmail() {
    var credentials = new Credential("application-user-b@user.com", "ApplicationUserA");
    given().when().contentType(ContentType.JSON).body(credentials).post().then().statusCode(403);
  }

  @Test
  public void testLoginWrongPassword() {
    var credentials = new Credential("application-user-b@user.com", "ApplicationUserA");
    given().when().contentType(ContentType.JSON).body(credentials).post().then().statusCode(403);
  }

}
