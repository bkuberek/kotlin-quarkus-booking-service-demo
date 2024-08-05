package com.bkuberek.bookings.graphql.v1.models

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.ReservationEntity
import java.time.ZonedDateTime

data class ReservationInfo(
    val id: String,
    val restaurant: RestaurantInfo,
    val name: String,
    val size: Int,
    val isActive: Boolean,
    val restrictions: Set<Endorsement>,
    val tables: Set<TableInfo>,
    val reservationTime: ZonedDateTime,
    val createdTime: ZonedDateTime,
    val updatedTime: ZonedDateTime?
) : ReservationResponse {
    constructor(entity: ReservationEntity) :
            this(
                entity.id.toString(),
                RestaurantInfo(entity.restaurant),
                entity.name,
                entity.size,
                entity.isActive,
                entity.restrictions.toSet(),
                entity.tables.map { TableInfo(it.size, it.quantity) }.toSet(),
                entity.reservationTime,
                entity.createdTime,
                entity.updatedTime,
            )
}
