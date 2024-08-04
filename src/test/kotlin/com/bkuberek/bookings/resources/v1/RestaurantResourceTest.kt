package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.Endorsement
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
    fun testGetRestaurantById() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getRestaurantById.gql",
            mapOf(
                Pair("id", "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c")
            )
        );

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(1))
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .log()
            .all()
    }

    @Test
    fun testGetRestaurantsById() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getRestaurantsById.gql",
            mapOf(
                Pair(
                    "ids", listOf(
                        "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c",
                        "dfe2cab1-6a39-4426-8937-c1d89403e0f0"
                    )
                )
            )
        );

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(2))
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .and()
            .body(Matchers.containsString("Panadería Rosetta"))
            .log()
            .all()
    }

    @Test
    fun testGetRestaurantsByName() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getRestaurantsByName.gql",
            mapOf(
                Pair(
                    "names", listOf(
                        "Tetetlán",
                        "Panadería Rosetta"
                    )
                )
            )
        );

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(2))
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .and()
            .body(Matchers.containsString("Panadería Rosetta"))
            .log()
            .all()
    }

    @Test
    fun testFindRestaurantsWithEndorsements() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/findRestaurantsWithEndorsements.gql",
            mapOf(
                Pair(
                    "endorsements", listOf(
                        Endorsement.gluten
                    )
                )
            )
        );

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(3))
            .and()
            .body(Matchers.containsString("Lardo"))
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .and()
            .body(Matchers.containsString("Panadería Rosetta"))
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
                Pair("time", "2024-08-04T20:00:00.00Z")
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