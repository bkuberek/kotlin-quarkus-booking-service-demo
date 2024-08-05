package com.bkuberek.bookings.db.entities

import com.bkuberek.bookings.db.Endorsement
import java.time.ZonedDateTime
import java.util.*

class ReservationEntity {

    var id: UUID? = null
    lateinit var restaurant: RestaurantEntity
    lateinit var name: String
    var restrictions: MutableSet<Endorsement> = mutableSetOf()
    var size: Int = 1
    var isActive: Boolean = true
    lateinit var reservationTime: ZonedDateTime
    lateinit var createdTime: ZonedDateTime
    var updatedTime: ZonedDateTime? = null
    var tables: MutableList<ReservationTableEntity> = mutableListOf()

    override fun toString(): String {
        return "ReservationEntity(id=$id, restaurant=$restaurant, name='$name', restrictions=$restrictions, size=$size, isActive=$isActive, reservationTime=$reservationTime, createdTime=$createdTime, updatedTime=$updatedTime, tables=$tables)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReservationEntity

        if (id != other.id) return false
        if (restaurant != other.restaurant) return false
        if (name != other.name) return false
        if (restrictions != other.restrictions) return false
        if (size != other.size) return false
        if (isActive != other.isActive) return false
        if (reservationTime != other.reservationTime) return false
        if (createdTime != other.createdTime) return false
        if (updatedTime != other.updatedTime) return false
        if (tables != other.tables) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + restaurant.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (restrictions?.hashCode() ?: 0)
        result = 31 * result + size
        result = 31 * result + isActive.hashCode()
        result = 31 * result + reservationTime.hashCode()
        result = 31 * result + createdTime.hashCode()
        result = 31 * result + (updatedTime?.hashCode() ?: 0)
        result = 31 * result + tables.hashCode()
        return result
    }
}
