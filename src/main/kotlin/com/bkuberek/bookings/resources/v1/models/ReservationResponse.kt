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
    val tables: List<TableInfo>,
    val reservationTime: ZonedDateTime,
    val createdTime: ZonedDateTime,
    val updatedTime: ZonedDateTime?
) {
    constructor(entity: ReservationEntity) :
            this(
                entity.id.toString(),
                RestaurantInfo(entity.restaurant),
                entity.name,
                entity.size,
                entity.isActive,
                if (entity.restrictions != null) entity.restrictions!!.split(",") else emptyList(),
                entity.tables.map { TableInfo(it.size, it.quantity) },
                entity.reservationTime,
                entity.createdTime,
                entity.updatedTime,
            )
}