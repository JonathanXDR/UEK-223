package ch.zli.m223.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.enumeration.StatusEnum;
import ch.zli.m223.model.enumeration.TimeFrameEnum;
import ch.zli.m223.service.TestDataServiceTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(BookingController.class)
@TestSecurity(user = "application-user-b@user.com", roles = "Member")
public class BookingControllerTest {

  @Inject
  TestDataServiceTest testDataServiceTest;

  @BeforeEach
  public void reset() {
    testDataServiceTest.generateTestData(null);
  }

  @Test
  public void testIndexEndpoint() {
    given().when().get("/bookings").then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
  }

  @Test
  public void testPostEndpoint() {
    var payload = new Booking(LocalDate.now().plusDays(3), TimeFrameEnum.AFTERNOON, null, null);

    given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("timeFrame", is("AFTERNOON"));
  }

  @Test
  public void testPostBadRequest() {
    var payload = new Booking();

    given().when().contentType(ContentType.JSON).body(payload).post("/bookings").then().statusCode(400);
  }

  @Test
  public void testPutEndpoint() {
    var payload = new Booking(LocalDate.now().minusDays(1), TimeFrameEnum.FULL_DAY,
        StatusEnum.DECLINED, null);

    given().when().contentType(ContentType.JSON).body(payload).put("/bookings/" + 1).then().statusCode(200)
        .body("date", is(payload.getDate().toString())).body("timeFrame", is("FULL_DAY"));
  }

  @Test
  public void testPutNotFound() {
    given().when().put("/bookings/" + 100).then().statusCode(404);
  }

  @Test
  public void testDeleteEndpoint() {
    given().when().delete("/bookings/" + 1).then().statusCode(200);
  }

  @Test
  public void testDeleteNotFound() {
    given().when().delete("/bookings/" + 100).then().statusCode(404);
  }

}
