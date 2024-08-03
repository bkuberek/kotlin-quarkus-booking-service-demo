package com.bkuberek.bookings.db.entities

import com.bkuberek.bookings.db.Endorsement
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.util.*

@Entity
@Table(name = "restaurant_endorsement")
class RestaurantEndorsementEntity : PanacheEntityBase() {

    @Id
    @Column(name = "restaurant_id")
    lateinit var restaurantId: UUID

    @Id
    @Column(name = "endorsement")
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType::class)
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