package com.nelo.bookings.v1

import com.nelo.bookings.models.Reservation
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/v1/reservation")
class ReservationResource {

    /**
     * Endpoint to get all active reservations for current user.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getReservations(): List<Reservation> {
        throw NotImplementedError()
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun createReservation(): Reservation {
        // validate request
        // save reservation
        throw NotImplementedError()
    }

    @GET
    @Path("{reservation_id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReservation(): Reservation {
        throw NotImplementedError()
    }

    @DELETE
    @Path("{reservation_id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun deleteReservation() {
        throw NotImplementedError()
    }
}