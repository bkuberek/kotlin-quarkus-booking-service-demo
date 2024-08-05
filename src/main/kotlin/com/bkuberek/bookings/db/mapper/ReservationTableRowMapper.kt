package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.entities.ReservationTableEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class ReservationTableRowMapper : RowMapper<ReservationTableEntity> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): ReservationTableEntity {
        val table = ReservationTableEntity()
        rs?.getString("bt_reservation_id")?.let { id -> table.reservationId = UUID.fromString(id) }
        rs?.getInt("bt_size")?.let { size -> table.size = size }
        rs?.getInt("bt_quantity")?.let { quantity -> table.quantity = quantity }
        return table
    }
}