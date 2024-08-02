package com.bkuberek.bookings.db.entities

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "restaurant")
@DynamicUpdate
@DynamicInsert
class RestaurantEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "created_time", nullable = true)
    lateinit var createdTime: ZonedDateTime

    @Column(name = "updated_time", nullable = true)
    var updatedTime: ZonedDateTime? = null

    @OneToMany(
        targetEntity = RestaurantEndorsementEntity::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    var endorsements: MutableList<RestaurantEndorsementEntity> = mutableListOf()

    @OneToMany(
        targetEntity = RestaurantTableEntity::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    var tables: MutableList<RestaurantTableEntity> = mutableListOf()
}