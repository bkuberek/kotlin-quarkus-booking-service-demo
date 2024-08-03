package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.repositories.RestaurantRepository
import com.bkuberek.bookings.resources.v1.models.RestaurantInfo
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.inject.Named
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import java.time.ZonedDateTime

@GraphQLApi
class RestaurantResource @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) {
    @Query
    @Description("Get a list of all Restaurants")
    fun allRestaurants(ctx: Context): List<RestaurantInfo> {
        return restaurantRepository.listAll().map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get Restaurant by ID")
    fun getRestaurantById(ctx: Context, @Named("id") id: String): RestaurantInfo? {
        return restaurantRepository.getById(id)?.let { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by ID")
    fun getRestaurantsById(ctx: Context, @Named("ids") ids: List<String>): List<RestaurantInfo> {
        return restaurantRepository.getByIds(ids).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by name")
    fun findRestaurantsByName(ctx: Context, @Named("names") names: List<String>): List<RestaurantInfo> {
        return restaurantRepository.findByName(names).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have certain endorsements")
    fun findRestaurantWithEndorsements(
        ctx: Context,
        @Named("endorsements") endorsements: List<Endorsement>
    ): List<RestaurantInfo> {
        return restaurantRepository.findRestaurantByEndorsement(endorsements).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have available tables given time, party size and food restrictions")
    fun findTable(
        ctx: Context,
        @Named("size") size: Int,
        @Named("endorsements") endorsements: List<Endorsement>,
        @Named("time") time: ZonedDateTime
    ): List<RestaurantInfo> {
        return restaurantRepository.findTable(size, endorsements, time).map { RestaurantInfo(it) }
    }
}