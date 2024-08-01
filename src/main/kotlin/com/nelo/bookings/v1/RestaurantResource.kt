package com.nelo.bookings.v1

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/v1/restaurant")
class RestaurantResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun findTable() {
        throw NotImplementedError()
    }
}