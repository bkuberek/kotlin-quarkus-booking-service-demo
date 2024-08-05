package com.bkuberek.bookings.graphql.v1.resources

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantTableAvailability
import com.bkuberek.bookings.db.repositories.RestaurantRepository
import com.bkuberek.bookings.graphql.v1.models.RestaurantInfo
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.inject.Named
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import java.time.ZonedDateTime
import java.util.*

@GraphQLApi
class RestaurantResource @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) {
    @Query
    @Description("Get a list of all Restaurants")
    fun allRestaurants(ctx: Context): Collection<RestaurantInfo> {
        return restaurantRepository.listAll().map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get Restaurant by ID")
    fun getRestaurantById(ctx: Context, @Named("id") id: UUID): RestaurantInfo? {
        return restaurantRepository.getById(id)?.let { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by ID")
    fun getRestaurantsById(ctx: Context, @Named("ids") ids: List<UUID>): Collection<RestaurantInfo> {
        return restaurantRepository.getByIds(ids).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of Restaurants by name")
    fun getRestaurantsByName(ctx: Context, @Named("names") names: List<String>): Collection<RestaurantInfo> {
        return restaurantRepository.findByName(names).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurants that have certain endorsements")
    fun findRestaurantsWithEndorsements(
        ctx: Context,
        @Named("endorsements") endorsements: List<Endorsement>
    ): Collection<RestaurantInfo> {
        return restaurantRepository.findRestaurantsByEndorsement(endorsements).map { RestaurantInfo(it) }
    }

    @Query
    @Description("Get a list of restaurant tables that are available at time")
    fun getAvailableTables(
        ctx: Context,
        @Named("restaurantId") restaurantId: UUID,
        @Named("time") time: ZonedDateTime,
        @Named("size") size: Int?
    ): RestaurantTableAvailability? {
        return restaurantRepository.getAvailableTables(restaurantId, time, size)
    }

    @Query
    @Description("Get a list of restaurants that have available tables given time, party size and food restrictions")
    fun findTable(
        ctx: Context,
        @Named("size") size: Int,
        @Named("endorsements") endorsements: Set<Endorsement>,
        @Named("time") time: ZonedDateTime
    ): Collection<RestaurantTableAvailability> {
        return if (endorsements.isNotEmpty()) {
            restaurantRepository.findRestaurantsWithAvailableTableAndRestrictions(size, endorsements, time)
        } else {
            restaurantRepository.findRestaurantsWithAvailableTable(size, time)
        }
    }
}