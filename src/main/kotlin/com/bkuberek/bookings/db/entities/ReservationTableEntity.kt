package com.bkuberek.bookings.db.entities

import java.util.*

class ReservationTableEntity {

    lateinit var reservationId: UUID
    var size: Int = 0
    var quantity: Int = 0

    override fun toString(): String {
        return "ReservationTableEntity(reservationId=$reservationId, size=$size, quantity=$quantity)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReservationTableEntity

        if (reservationId != other.reservationId) return false
        if (size != other.size) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reservationId.hashCode()
        result = 31 * result + size
        result = 31 * result + quantity
        return result
    }
}