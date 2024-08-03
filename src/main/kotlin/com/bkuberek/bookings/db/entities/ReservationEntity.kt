package com.bkuberek.bookings.db.entities

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "reservation")
@DynamicUpdate
@DynamicInsert
class ReservationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    lateinit var restaurant: RestaurantEntity

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "restrictions", nullable = true)
    var restrictions: String? = null

    @Column(name = "size", nullable = false)
    var size: Int = 1

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    @Column(name = "reservation_time", nullable = false)
    lateinit var reservationTime: ZonedDateTime

    @Column(name = "created_time", nullable = false)
    lateinit var createdTime: ZonedDateTime

    @Column(name = "updated_time", nullable = true)
    var updatedTime: ZonedDateTime? = null

    @OneToMany(
        targetEntity = ReservationTableEntity::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    var tables: MutableList<ReservationTableEntity> = mutableListOf()
}
