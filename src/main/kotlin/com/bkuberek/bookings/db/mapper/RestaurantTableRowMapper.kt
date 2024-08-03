package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.entities.RestaurantTableEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class RestaurantTableRowMapper : RowMapper<RestaurantTableEntity> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): RestaurantTableEntity {
        val table = RestaurantTableEntity()
        rs?.getString("t_restaurant_id")?.let { id -> table.restaurantId = UUID.fromString(id) }
        rs?.getInt("t_size")?.let { size -> table.size = size }
        rs?.getInt("t_quantity")?.let { quantity -> table.quantity = quantity }
        return table
    }
}