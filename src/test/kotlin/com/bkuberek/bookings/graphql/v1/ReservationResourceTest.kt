package com.bkuberek.bookings.graphql.v1

import com.bkuberek.bookings.loadGraphqlQueryAsJson
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

@QuarkusTest
class ReservationResourceTest {

    @Test
    fun testGetReservation() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getReservation.gql",
            mapOf(
                Pair("id", "ae5a8791-43dc-4fee-a6c5-5d6be12344ed")
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
            .body(Matchers.containsString("ae5a8791-43dc-4fee-a6c5-5d6be12344ed"))
            .and()
            .body(Matchers.containsString("Falling Piano Brewing Co"))
            .log()
            .all()
    }

    @Test
    fun testGetReservationsForUser() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getReservations.gql",
            mapOf(
                Pair("name", "Tobias")
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
            .body("data.reservations", Matchers.notNullValue())
            .and()
            .body(Matchers.containsString("ae5a8791-43dc-4fee-a6c5-5d6be12344ed"))
            .and()
            .body(Matchers.containsString("Falling Piano Brewing Co"))
            .log()
            .all()
    }

    @Test
    fun testGetReservationsForUserAndRestaurant() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getReservations.gql",
            mapOf(
                Pair("name", "Tobias"),
                Pair("restaurantId", "ae5a8791-43dc-4fee-a6c5-5d6be12344ed")
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
            .body("data.reservations", Matchers.notNullValue())
            .and()
            .body(Matchers.containsString("ae5a8791-43dc-4fee-a6c5-5d6be12344ed"))
            .and()
            .body(Matchers.containsString("Falling Piano Brewing Co"))
            .log()
            .all()
    }

    @Test
    fun testGetReservationNotFound() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getReservation.gql",
            mapOf(
                Pair("id", "ae5a8791-43dc-4fee-a6c5-5d6be1234444")
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
            .body("data.reservation", Matchers.nullValue())
            .log()
            .all()
    }

    @Test
    fun testCreateReservation() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/createReservation.gql",
            mapOf(
                Pair(
                    "request", mapOf(
                        Pair("restaurantId", "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c"),
                        Pair("name", "Mike Tyson"),
                        Pair("reservationTime", "2024-08-01T20:00:00.00Z"),
                        Pair("size", 3),
                        Pair("restrictions", listOf("paleo"))
                    )
                )
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
    fun testDeleteReservation() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/deleteReservation.gql",
            mapOf(Pair("id", "3ca1ddc3-1ba1-4dfb-9a32-a33259b7fe0c"))
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
            .body(Matchers.containsString("Gob"))
            .log()
            .all()
    }
}