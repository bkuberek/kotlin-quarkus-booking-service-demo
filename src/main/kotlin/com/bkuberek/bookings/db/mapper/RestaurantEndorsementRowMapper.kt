package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantEndorsementEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class RestaurantEndorsementRowMapper : RowMapper<RestaurantEndorsementEntity> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): RestaurantEndorsementEntity {
        val endorsement = RestaurantEndorsementEntity()
        rs?.getString("e_restaurant_id")?.let { id -> endorsement.restaurantId = UUID.fromString(id) }
        rs?.getString("e_endorsement")?.let { e -> endorsement.endorsement = Endorsement.valueOf(e) }
        return endorsement
    }
}