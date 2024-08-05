package com.bkuberek.bookings.graphql.v1.models

import com.bkuberek.bookings.db.Endorsement
import jakarta.json.bind.annotation.JsonbCreator
import java.time.ZonedDateTime
import java.util.*

data class ReservationRequest @JsonbCreator constructor(
    val restaurantId: UUID,
    val name: String,
    val size: Int,
    val restrictions: Set<Endorsement>,
    val reservationTime: ZonedDateTime
)
