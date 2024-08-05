package com.bkuberek.bookings.graphql.v1

import com.bkuberek.bookings.RESERVATION_DURATION_MINUTES
import com.bkuberek.bookings.RESTAURANT_HOURS_OF_OPERATION
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.repositories.ReservationRepository
import com.bkuberek.bookings.graphql.v1.models.ReservationRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.ZonedDateTime

@ApplicationScoped
class ReservationValidator @Inject constructor(private val reservationRepository: ReservationRepository) {

    fun validate(
        reservationRequest: ReservationRequest,
        restaurant: RestaurantEntity
    ) {
        val timeStart = reservationRequest.reservationTime
        val timeStop = reservationRequest.reservationTime.plusMinutes(RESERVATION_DURATION_MINUTES)

        checkHoursOfOperation(reservationRequest, restaurant, timeStart, timeStop)
        checkConflicts(reservationRequest, timeStart, timeStop)
    }

    fun checkHoursOfOperation(
        reservationRequest: ReservationRequest,
        restaurant: RestaurantEntity,
        timeStart: ZonedDateTime,
        timeStop: ZonedDateTime
    ) {
        // Is the request withing the hours of operation of the restaurant?
        // @Todo: restaurant hours of operation
        if ((timeStart.toLocalTime() < RESTAURANT_HOURS_OF_OPERATION.first
                    && timeStop.toLocalTime() > RESTAURANT_HOURS_OF_OPERATION.first)
            || (timeStart.toLocalTime() < RESTAURANT_HOURS_OF_OPERATION.second
                    && timeStop.toLocalTime() > RESTAURANT_HOURS_OF_OPERATION.second)
        ) {
            throw ReservationIllegalTimeException()
        }
    }

    fun checkConflicts(
        reservationRequest: ReservationRequest,
        timeStart: ZonedDateTime,
        timeStop: ZonedDateTime
    ) {
        val existing = reservationRepository.listAllForUserAndRestaurant(
            reservationRequest.name,
            reservationRequest.restaurantId
        )
        if (existing.isNotEmpty()) {
            for (it in existing) {
                // Does it overlap with the requested time?
                if (
                // 1. Existing reservation starts earlier but overlaps with the requested time
                    (it.reservationTime >= timeStart && it.reservationTime < timeStop)
                    // 2. Existing reservation starts during the INTERVAL after the requested time
                    || (it.reservationTime.plusMinutes(RESERVATION_DURATION_MINUTES) >= timeStart
                            && it.reservationTime.plusMinutes(RESERVATION_DURATION_MINUTES) < timeStop)
                ) {
                    // no need to continue the loop if a conflict is found. Break and return error.
                    throw ReservationConflictException()
                }
            }
        }
    }

    open class ValidationException : RuntimeException()
    class ReservationConflictException : ValidationException()
    class ReservationIllegalTimeException : ValidationException()
}