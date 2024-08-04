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
}
