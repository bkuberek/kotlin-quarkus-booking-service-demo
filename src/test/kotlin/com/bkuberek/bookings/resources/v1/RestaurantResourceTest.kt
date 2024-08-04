package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.resources.loadGraphqlQueryAsJson
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.ZoneId

@QuarkusTest
class RestaurantResourceTest {

    // The Sample Data migration uses current date + interval to create reservations tomorrow
    private val tomorrow: LocalDate = LocalDate.now().plusDays(1)

    @Test
    fun testGetAllRestaurants() {
        val query = loadGraphqlQueryAsJson("/graphql/allRestaurants.gql");

        val response = given()
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
            .extract()
    }

    @Test
    fun testGetRestaurantById() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/getRestaurantById.gql",
            mapOf(
                Pair("id", "b1e6728c-da7c-4841-bbf3-ba7e97f7e07c")
            )
        );

        val response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurant", Matchers.notNullValue())
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .log()
            .all()
            .extract()
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

        val response = given()
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
            .extract()
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

        val response = given()
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
            .extract()
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

        val response = given()
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
            .extract()
    }

    @Test
    fun testFindTableNoRestrictions() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/findTable.gql",
            mapOf(
                Pair("size", 2),
                Pair("endorsements", emptyList<Endorsement>()),
                Pair("time", tomorrow.atTime(20, 0, 0, 0).atZone(ZoneId.systemDefault()))
            )
        )

        val response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(5))
            .and()
            .body(Matchers.containsString("Lardo"))
            .and()
            .body(Matchers.containsString("Falling Piano Brewing Co"))
            .and()
            .body(Matchers.containsString("u.to.pi.a"))
            .log()
            .all()
            .extract()
    }

    @Test
    fun testFindTableOneRestriction() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/findTable.gql",
            mapOf(
                Pair("size", 2),
                Pair("endorsements", listOf(Endorsement.gluten)),
                Pair("time", tomorrow.atTime(20, 0, 0, 0).atZone(ZoneId.systemDefault()))
            )
        )
        val response = given()
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
            .body(Matchers.containsString("Panadería Rosetta"))
            .and()
            .body(Matchers.containsString("Tetetlán"))
            .and()
            .body(Matchers.not(Matchers.containsString("u.t.o.p.i.a")))
            .log()
            .all()
            .extract()
    }

    @Test
    fun testFindTableMultipleRestrictions() {
        val query = loadGraphqlQueryAsJson(
            "/graphql/findTable.gql",
            mapOf(
                Pair("size", 2),
                Pair("endorsements", listOf(Endorsement.vegan, Endorsement.vegetarian)),
                Pair("time", tomorrow.atTime(20, 0, 0, 0).atZone(ZoneId.systemDefault()))
            )
        )

        val response = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(query)
            .`when`()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.restaurants.size()", Matchers.equalTo(2))
            .and()
            .body(Matchers.containsString("Panadería Rosetta"))
            .and()
            .body(Matchers.containsString("u.to.pi.a"))
            .and()
            .body(Matchers.not(Matchers.containsString("Tetetlán")))
            .log()
            .all()
            .extract()
    }
}