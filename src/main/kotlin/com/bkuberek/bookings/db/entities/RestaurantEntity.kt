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
@NamedQueries(
    NamedQuery(name = "restaurant.findTable", query = """FROM ReservationEntity r"""),
)
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
    var endorsements: MutableSet<RestaurantEndorsementEntity> = mutableSetOf()

    @OneToMany(
        targetEntity = RestaurantTableEntity::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
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
