package com.bkuberek.bookings.resources.v1

import com.bkuberek.bookings.db.entities.ReservationEntity
import com.bkuberek.bookings.db.repositories.ReservationRepository
import com.bkuberek.bookings.resources.v1.models.ReservationRequest
import com.bkuberek.bookings.resources.v1.models.ReservationResponse
import io.smallrye.graphql.api.Context
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import org.eclipse.microprofile.graphql.*

@GraphQLApi
class ReservationResource {

    @Inject
    lateinit var reservationRepository: ReservationRepository

    @Query
    @Description("Get all active reservations for a given user name.")
    fun getReservations(ctx: Context, @Name("name") name: String): List<ReservationResponse> {
        return reservationRepository.find("name", name)
            .list<ReservationEntity>()
            .stream()
            .map { ReservationResponse(it) }
            .toList()
    }

    @Mutation
    @Description("Create a new reservation.")
    @Transactional
    fun createReservation(
        ctx: Context,
        @Name("reservationRequest") reservationRequest: ReservationRequest
    ): ReservationResponse {
        // validate request
        // save reservation in a transaction and fail if conflicts
        throw NotImplementedError()
    }

    @Query
    @Description("Get Reservation by ID")
    fun getReservation(ctx: Context, @Name("id") id: String): ReservationResponse? {
        return reservationRepository.findById(id)?.let { ReservationResponse(it) }
    }

    @Mutation
    @Description("Delete a Reservation by ID")
    @Transactional
    fun deleteReservation(ctx: Context, @Name("id") id: String): ReservationResponse {
        val reservation = reservationRepository.findById(id)
        if (reservation == null) {
            throw NotFoundException()
        } else {
            reservation.id?.let { reservationRepository.deleteById(it) }
        }
        return ReservationResponse(reservation)
    }
}