package com.bkuberek.bookings.db.entities

import com.bkuberek.bookings.db.Endorsement
import java.util.*

class RestaurantTableAvailability {
    lateinit var id: UUID
    lateinit var name: String
    lateinit var size: String
    var endorsements: MutableSet<Endorsement>? = mutableSetOf()
    var tables: MutableSet<RestaurantTableEntity> = mutableSetOf()
    var occupiedTables: MutableSet<RestaurantTableEntity> = mutableSetOf()
    var availableTables: MutableSet<RestaurantTableEntity> = mutableSetOf()

    override fun toString(): String {
        return "RestaurantTableAvailability(id=$id, name='$name', size='$size', endorsements=$endorsements, tables=$tables, occupiedTables=$occupiedTables, availableTables=$availableTables)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantTableAvailability

        if (id != other.id) return false
        if (name != other.name) return false
        if (size != other.size) return false
        if (endorsements != other.endorsements) return false
        if (tables != other.tables) return false
        if (occupiedTables != other.occupiedTables) return false
        if (availableTables != other.availableTables) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + (endorsements?.hashCode() ?: 0)
        result = 31 * result + tables.hashCode()
        result = 31 * result + occupiedTables.hashCode()
        result = 31 * result + availableTables.hashCode()
        return result
    }

}
