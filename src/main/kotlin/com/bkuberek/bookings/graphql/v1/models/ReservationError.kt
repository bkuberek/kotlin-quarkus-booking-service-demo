package com.bkuberek.bookings.graphql.v1.models

data class ReservationError(
    val error: ApiError,
    val message: String
) : ReservationResponse
