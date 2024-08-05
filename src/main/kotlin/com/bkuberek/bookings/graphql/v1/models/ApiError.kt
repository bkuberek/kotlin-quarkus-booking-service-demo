package com.bkuberek.bookings.graphql.v1.models

enum class ApiError(val message: String) {
    DENIED("action denied"),
    INVALID("invalid"),
    UNAVAILABLE("unavailable"),
    INTERNAL("internal error")
}
