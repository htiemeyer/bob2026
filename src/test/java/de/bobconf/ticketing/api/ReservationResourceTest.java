package de.bobconf.ticketing.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ReservationResourceTest {

    @Test
    void testGetEvents() {
        RestAssured.given()
                .when().get("/events")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    void testReserve() {
        String body = """
            {
              "eventId": 1,
              "tickets": 2,
              "customerEmail": "test@example.com"
            }
            """;

        RestAssured.given()
                .contentType("application/json")
                .body(body)
                .when().post("/reservations")
                .then()
                .statusCode(200)
                .body("success", is(true));
    }
}
