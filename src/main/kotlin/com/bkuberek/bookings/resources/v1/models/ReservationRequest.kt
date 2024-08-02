package com.bkuberek.bookings.resources.v1.models

import com.bkuberek.bookings.db.Endorsement
import jakarta.json.bind.annotation.JsonbCreator
import java.time.ZonedDateTime

data class ReservationRequest @JsonbCreator constructor(
    val restaurantId: String,
    val name: String,
    val size: Int,
    val restrictions: List<Endorsement>,
    val reservationTime: ZonedDateTime
)