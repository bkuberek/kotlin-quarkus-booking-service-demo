package com.bkuberek.bookings.resources.v1.models

import com.bkuberek.bookings.db.entities.ReservationEntity
import java.time.ZonedDateTime

data class ReservationResponse(
    val id: String,
    val restaurant: RestaurantInfo,
    val name: String,
    val size: Int,
    val isActive: Boolean,
    val restrictions: List<String>,
    val reservationTime: ZonedDateTime,
    val createdTime: ZonedDateTime,
    val updatedTime: ZonedDateTime?
) {
    constructor(it: ReservationEntity) :
            this(
                it.id.toString(),
                RestaurantInfo(it.restaurant),
                it.name,
                it.size,
                it.isActive,
                if (it.restrictions != null) it.restrictions!!.split(",") else emptyList(),
                it.reservationTime,
                it.createdTime,
                it.updatedTime
            )
}