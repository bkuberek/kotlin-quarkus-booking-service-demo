package com.bkuberek.bookings.graphql.v1.resources

import com.bkuberek.bookings.db.repositories.ReservationRepository
import com.bkuberek.bookings.db.repositories.RestaurantRepository
import com.bkuberek.bookings.graphql.v1.ReservationManager
import com.bkuberek.bookings.graphql.v1.ReservationValidator
import com.bkuberek.bookings.graphql.v1.models.*
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.inject.Named
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import org.jboss.logging.Logger
import java.util.*

@GraphQLApi
class ReservationResource @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val reservationRepository: ReservationRepository,
    private val reservationValidator: ReservationValidator,
    private val reservationManager: ReservationManager,
) {
    private val logger = Logger.getLogger(ReservationResource::class.java)

    @Query
    @Description("Get Reservation by ID")
    fun getReservation(ctx: Context, @Named("id") id: UUID): ReservationInfo? {
        return reservationRepository.getById(id)?.let { ReservationInfo(it) }
    }

    @Query
    @Description("Get all active reservations for a given user name.")
    fun getReservations(
        ctx: Context,
        @Named("name") name: String,
        @Named("restaurantId") restaurantId: UUID?
    ): Collection<ReservationInfo> {
        return if (restaurantId != null) {
            reservationRepository.listAllForUserAndRestaurant(name, restaurantId).map { ReservationInfo(it) }
        } else {
            reservationRepository.listAllForUser(name).map { ReservationInfo(it) }
        }
    }

    @Mutation
    @Description("Create a new reservation.")
    fun createReservation(
        ctx: Context,
        @Named("reservationRequest") reservationRequest: ReservationRequest
    ): ReservationResponse {
        val restaurant = restaurantRepository.getById(reservationRequest.restaurantId)
            ?: return ReservationError(
                ApiError.INVALID,
                "Invalid ID"
            )

        // Run validator
        try {
            reservationValidator.validate(reservationRequest, restaurant)
        } catch (e: ReservationValidator.ReservationIllegalTimeException) {
            return ReservationError(
                ApiError.INVALID,
                "The requested time is outside the Hours of Operation of the venue."
            )
        } catch (e: ReservationValidator.ReservationConflictException) {
            return ReservationError(
                ApiError.DENIED,
                "There is an existing reservation for this user at this restaurant at this time."
            )
        }

        // Does the restaurant have availability?
        val available = restaurantRepository.getAvailableTables(
            reservationRequest.restaurantId,
            reservationRequest.reservationTime,
            reservationRequest.size
        )
        if (available == null || available.availableTables.isEmpty()) {
            return ReservationError(
                ApiError.UNAVAILABLE,
                "The restaurant does not have available capacity for party size at requested time."
            )
        }

        return try {
            val entity =
                reservationManager.assignTables(restaurant, available.availableTables, reservationRequest)
            ReservationInfo(reservationRepository.createReservation(entity))
        } catch (e: IllegalArgumentException) {
            ReservationError(
                ApiError.UNAVAILABLE,
                "The restaurant does not have available capacity for party size at requested time. (${e.message})"
            )
        } catch (e: Exception) {
            logger.error("Unhandled Exception", e)
            ReservationError(
                ApiError.INTERNAL,
                "Internal Server Error"
            )
        }
    }

    @Mutation
    @Description("Delete a Reservation by ID")
    fun deleteReservation(ctx: Context, @Named("id") id: UUID): ReservationResponse {
        val reservation = reservationRepository.getById(id)
        if (reservation != null) {
            reservation.id?.let { reservationRepository.deleteById(it) }
            return ReservationInfo(reservation)
        } else {
            return ReservationError(ApiError.INVALID, "Reservation Not Found")
        }
    }
}