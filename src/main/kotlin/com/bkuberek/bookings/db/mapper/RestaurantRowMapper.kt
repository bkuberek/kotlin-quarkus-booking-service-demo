package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.entities.RestaurantEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.time.ZoneId
import java.util.*

class RestaurantRowMapper : RowMapper<RestaurantEntity> {

    override fun map(rs: ResultSet?, ctx: StatementContext?): RestaurantEntity {
        val restaurant = RestaurantEntity()
        rs?.getString("r_id")?.let { id -> restaurant.id = UUID.fromString(id) }
        rs?.getString("r_name")?.let { name -> restaurant.name = name }

        rs?.getTimestamp("r_created_time")?.let { timestamp ->
            restaurant.createdTime = timestamp.toInstant().atZone(ZoneId.systemDefault())
        }
        rs?.getTimestamp("r_updated_time")?.let { timestamp ->
            restaurant.updatedTime = timestamp.toInstant().atZone(ZoneId.systemDefault())
        }

        return restaurant
    }
}