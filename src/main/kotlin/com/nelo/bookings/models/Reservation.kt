package com.nelo.bookings.models

import java.time.ZonedDateTime

data class Reservation(
    val id: String,
    val restaurantId: String,
    val name: String,
    val size: Int,
    val isActive: Boolean,
    val reservationTime: ZonedDateTime,
    val createdTime: ZonedDateTime,
    val updatedTime: ZonedDateTime
)