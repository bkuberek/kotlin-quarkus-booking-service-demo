package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.resources.loadGraphqlQueryAsJson
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@QuarkusTest
class ReservationResourceTest {

    @Test
    @Disabled
    fun testCreateReservation() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/createReservation.gql",
            mapOf(
                Pair("restaurantId", "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c"),
                Pair("name", "Mike Tyson"),
                Pair("reservationTime", "2024-08-01T20:00:00.00Z"),
                Pair("size", 3),
                Pair("restrictions", listOf("paleo"))
            )
        )

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.reservation", Matchers.notNullValue())
            .and()
            .body(Matchers.containsString("b1e6728c-da7c-4841-bbf3-ba7e97f7e07c"))
            .log()
            .all()
    }

    @Test
    @Disabled
    fun testDeleteReservation() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/deleteReservation.gql",
            mapOf(Pair("id", "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c"))
        );
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.reservation", Matchers.notNullValue())
            .and()
            .body(Matchers.containsString("b1e6728c-da7c-4841-bbf3-ba7e97f7e07c"))
            .log()
            .all()
    }

}