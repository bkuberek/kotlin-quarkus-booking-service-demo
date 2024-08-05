package com.bkuberek.bookings.db.entities

import com.bkuberek.bookings.db.Endorsement
import java.util.*

class RestaurantEndorsementEntity {

    lateinit var restaurantId: UUID
    lateinit var endorsement: Endorsement

    override fun toString(): String {
        return "RestaurantEndorsementEntity(restaurantId=$restaurantId, endorsement=$endorsement)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantEndorsementEntity

        if (restaurantId != other.restaurantId) return false
        if (endorsement != other.endorsement) return false

        return true
    }

    override fun hashCode(): Int {
        var result = restaurantId.hashCode()
        result = 31 * result + endorsement.hashCode()
        return result
    }
}