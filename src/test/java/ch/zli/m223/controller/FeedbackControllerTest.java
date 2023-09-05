package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Feedback;
import ch.zli.m223.service.TestDataServiceTest;
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
  TestDataServiceTest testDataServiceTest;

  @BeforeEach
  public void reset() {
    testDataServiceTest.generateTestData(null);
  }

  @Test
  public void testIndexEndpoint() {
    given().when().get("/feedbacks").then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
  }

  @Test
  public void testPostEndpoint() {
    var payload = new Feedback("Feedback A title", "Feedback A description", null);

    given().when().contentType(ContentType.JSON).body(payload).post("/feedbacks").then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("title", is("Feedback A title"), "description",
            is("Feedback A description"));
  }

  @Test
  public void testPostBadRequest() {
    var payload = new Feedback();

    given().when().contentType(ContentType.JSON).body(payload).post("/feedbacks").then().statusCode(400);
  }

  @Test
  public void testPutEndpoint() {
    var payload = new Feedback("Feedback A title", "Feedback A description", null);

    given().when().contentType(ContentType.JSON).body(payload).put("/feedbacks" + 1).then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("title", is("Feedback A title"), "description",
            is("Feedback A description"));
  }

  @Test
  public void testPutNotFound() {
    given().when().delete("/feedbacks" + 100).then().statusCode(404);
  }

  @Test
  public void testDeleteEndpoint() {
    given().when().delete("/feedbacks" + 1).then().statusCode(200);
  }

  @Test
  public void testDeleteNotFound() {
    given().when().delete("/feedbacks" + 100).then().statusCode(404);
  }

}
