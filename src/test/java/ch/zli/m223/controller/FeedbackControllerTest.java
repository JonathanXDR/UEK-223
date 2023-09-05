package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Feedback;
import ch.zli.m223.service.TestDataService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(FeedbackController.class)
@TestSecurity(user = "application-user-b@user.com", roles = "Member")
public class FeedbackControllerTest {

  @Inject
  TestDataService testDataService;

  @BeforeEach
  public void reset() {
    testDataService.generateTestData(null);
  }

  @Test
  public void testIndexEndpoint() {
    given().when().get().then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
  }

  @Test
  public void testPostEndpoint() {
    var payload = new Feedback("Feedback A title", "Feedback A description", null);

    given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("title", is("Feedback A title"), "description",
            is("Feedback A description"));
  }

  @Test
  public void testPostInvalid() {
    var payload = new Feedback();

    given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(400);
  }

  @Test
  public void testPutEndpoint() {
    var payload = new Feedback("Feedback A title", "Feedback A description", null);

    given().when().contentType(ContentType.JSON).body(payload).put("/1").then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("title", is("Feedback A title"), "description",
            is("Feedback A description"));
  }

  @Test
  public void testDeleteEndpoint() {
    given().when().delete("/1").then().statusCode(204);
  }

  @Test
  public void testDeleteNotFound() {
    given().when().delete("/100").then().statusCode(404);
  }

}
