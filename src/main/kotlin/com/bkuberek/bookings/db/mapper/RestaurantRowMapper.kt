package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.entities.RestaurantEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RestaurantRowMapper : RowMapper<RestaurantEntity> {

    override fun map(rs: ResultSet?, ctx: StatementContext?): RestaurantEntity {
        val restaurant = RestaurantEntity()
        rs?.getString("id")?.let { id -> restaurant.id = UUID.fromString(id) }
        rs?.getString("name")?.let { name -> restaurant.name = name }
        rs?.getString("createdTime")?.let { createdTime ->
            restaurant.createdTime = ZonedDateTime.parse(createdTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
        rs?.getString("updatedTime")?.let { updatedTime ->
            restaurant.updatedTime = ZonedDateTime.parse(updatedTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
        // @ todo: add relations -> endorsements, tables
        restaurant.endorsements = mutableListOf()
        restaurant.tables = mutableListOf()
        return restaurant
    }
}