package com.bkuberek.bookings.graphql.v1.models

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantEntity

open class RestaurantInfo(
    val id: String,
    val name: String,
    val endorsements: List<Endorsement>,
    val tables: List<TableInfo>,
) {
    constructor(restaurant: RestaurantEntity) :
            this(
                restaurant.id.toString(),
                restaurant.name,
                restaurant.endorsements.map { e -> e.endorsement },
                restaurant.tables.map { TableInfo(it.size, it.quantity) }
            )

}