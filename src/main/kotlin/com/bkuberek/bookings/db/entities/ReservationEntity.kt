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
