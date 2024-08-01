package com.nelo.bookings.models

import java.time.ZonedDateTime

data class Restaurant(
    val id: String,
    val name: String,
    val endorsements: List<Endorsement>,
    val createdTime: ZonedDateTime,
    val updatedTime: ZonedDateTime
)