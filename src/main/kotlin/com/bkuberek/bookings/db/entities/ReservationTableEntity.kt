package com.bkuberek.bookings.db.entities

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "reservation_table")
class ReservationTableEntity : PanacheEntityBase() {

    @Id
    @Column(name = "reservation_id")
    lateinit var reservationId: UUID

    @Id
    @Column(name = "size")
    var size: Int = 0

    @Column(name = "quantity")
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