package com.bkuberek.bookings

import java.time.LocalTime

const val RESERVATION_DURATION_MINUTES = 120L
const val RESERVATION_DURATION_SQL_INTERVAL = "120 minutes"

// @Todo: restaurant hours of operation
val RESTAURANT_HOURS_OF_OPERATION = Pair(
    LocalTime.of(6, 0, 0),
    LocalTime.MIDNIGHT
)
