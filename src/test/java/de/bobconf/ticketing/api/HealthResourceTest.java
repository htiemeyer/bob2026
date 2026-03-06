package de.bobconf.ticketing.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HealthResourceTest {

    @Test
    void health_endpoint_should_return_ok() {
        RestAssured.given()
                .when().get("/health")
                .then()
                .statusCode(200)
                .body(is("OK – Event Ticketing Workshop is ready"));
    }
}
