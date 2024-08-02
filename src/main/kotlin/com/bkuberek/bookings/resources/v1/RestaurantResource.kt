package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.repositories.RestaurantRepository
import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.resources.v1.models.RestaurantInfo
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.inject.Named
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import java.time.ZonedDateTime

@GraphQLApi
class RestaurantResource {

    @Inject
    lateinit var restaurantRepository: RestaurantRepository

    @Query
    @Description("Get a list of all restaurants")
    fun restaurants(ctx: Context): List<RestaurantInfo> {
        return restaurantRepository.listAll().map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have available tables given time, party size and food restrictions")
    fun findTable(
        ctx: Context,
        @Named("size") size: Int,
        @Named("restrictions") restrictions: List<Endorsement>,
        @Named("time") time: ZonedDateTime
    ): List<RestaurantInfo> {
        return restaurantRepository.findTable(size, restrictions, time).map { RestaurantInfo(it) }
    }
}