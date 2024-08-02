package com.bkuberek.bookings.db.entities

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "restaurant_table")
class RestaurantTableEntity : PanacheEntityBase() {

    @Id
    @Column(name = "restaurant_id")
    lateinit var restaurantId: UUID

    @Id
    @Column(name = "size")
    var size: Int = 0

    @Column(name = "quantity")
    var quantity: Int = 0
}