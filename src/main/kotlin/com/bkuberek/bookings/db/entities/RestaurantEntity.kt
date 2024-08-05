package com.bkuberek.bookings.db.entities

import java.time.ZonedDateTime
import java.util.*

class RestaurantEntity {

    var id: UUID? = null
    lateinit var name: String
    lateinit var createdTime: ZonedDateTime
    var updatedTime: ZonedDateTime? = null
    var endorsements: MutableSet<RestaurantEndorsementEntity> = mutableSetOf()
    var tables: MutableSet<RestaurantTableEntity> = mutableSetOf()

    override fun toString(): String {
        return "RestaurantEntity(id=$id, name='$name', createdTime=$createdTime, updatedTime=$updatedTime, endorsements=$endorsements, tables=$tables)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (createdTime != other.createdTime) return false
        if (updatedTime != other.updatedTime) return false
        if (endorsements != other.endorsements) return false
        if (tables != other.tables) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + createdTime.hashCode()
        result = 31 * result + (updatedTime?.hashCode() ?: 0)
        result = 31 * result + endorsements.hashCode()
        result = 31 * result + tables.hashCode()
        return result
    }
}
