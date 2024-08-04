package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantTableAvailability
import com.bkuberek.bookings.db.repositories.RestaurantRepository
import com.bkuberek.bookings.resources.v1.models.RestaurantInfo
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.inject.Named
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import java.time.ZonedDateTime
import java.util.*

public const val RESERVATION_DURATION_MINUTES = 120L

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
    fun getRestaurantById(ctx: Context, @Named("id") id: UUID): RestaurantInfo? {
        return restaurantRepository.getById(id)?.let { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by ID")
    fun getRestaurantsById(ctx: Context, @Named("ids") ids: List<UUID>): List<RestaurantInfo> {
        return restaurantRepository.getByIds(ids).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by name")
    fun getRestaurantsByName(ctx: Context, @Named("names") names: List<String>): List<RestaurantInfo> {
        return restaurantRepository.findByName(names).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have certain endorsements")
    fun findRestaurantsWithEndorsements(
        ctx: Context,
        @Named("endorsements") endorsements: List<Endorsement>
    ): List<RestaurantInfo> {
        return restaurantRepository.findRestaurantsByEndorsement(endorsements).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have available tables given time, party size and food restrictions")
    fun findTable(
        ctx: Context,
        @Named("size") size: Int,
        @Named("endorsements") endorsements: Set<Endorsement>,
        @Named("time") time: ZonedDateTime
    ): List<RestaurantTableAvailability> {
        return if (endorsements.isNotEmpty()) {
            restaurantRepository.findRestaurantsWithAvailableTableAndRestrictions(
                size,
                endorsements,
                time.minusMinutes(RESERVATION_DURATION_MINUTES),
                time
            )
        } else {
            restaurantRepository.findRestaurantsWithAvailableTable(
                size,
                time.minusMinutes(RESERVATION_DURATION_MINUTES),
                time
            )
        }
    }
}