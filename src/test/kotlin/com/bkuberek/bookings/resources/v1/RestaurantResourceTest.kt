package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.resources.loadGraphqlQueryAsJson
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test

@QuarkusTest
class RestaurantResourceTest {

    @Test
    fun testGetAllRestaurants() {
        val query = loadGraphqlQueryAsJson("/graphql/allRestaurants.gql");

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", greaterThan(0))
            .and()
            .body(Matchers.containsString("Lardo"))
            .log()
            .all()
    }

    @Test
    fun testFindTable() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/findTable.gql",
            mapOf(
                Pair("size", 2),
                Pair("restrictions", listOf("gluten")),
                Pair("time", "2024-08-01T20:00:00.00Z")
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
            .body("data.restaurants.size()", greaterThan(0))
            .and()
            .body(Matchers.containsString("Lardo"))
            .and()
            .body(Matchers.not(Matchers.containsString("Falling Piano Brewing Co")))
            .and()
            .body(Matchers.not(Matchers.containsString("u.to.pi.a")))
            .log()
            .all()
    }
}