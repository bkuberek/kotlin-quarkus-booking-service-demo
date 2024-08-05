package com.bkuberek.bookings.db.entities

import java.util.*

class RestaurantTableEntity {
    lateinit var restaurantId: UUID
    var size: Int = 0
    var quantity: Int = 0

    override fun toString(): String {
        return "RestaurantTableEntity(restaurantId=$restaurantId, size=$size, quantity=$quantity)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantTableEntity

        if (restaurantId != other.restaurantId) return false
        if (size != other.size) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = restaurantId.hashCode()
        result = 31 * result + size
        result = 31 * result + quantity
        return result
    }
}