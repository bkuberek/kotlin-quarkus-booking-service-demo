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
}