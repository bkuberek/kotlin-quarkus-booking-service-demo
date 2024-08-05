package com.bkuberek.bookings.db.mapper

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.ReservationEntity
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.time.ZoneId
import java.util.*

class ReservationRowMapper : RowMapper<ReservationEntity> {

    override fun map(rs: ResultSet?, ctx: StatementContext?): ReservationEntity {
        val reservation = ReservationEntity()
        rs?.getString("b_id")?.let { id -> reservation.id = UUID.fromString(id) }
        rs?.getString("b_name")?.let { name -> reservation.name = name }
        rs?.getString("b_size")?.let { size -> reservation.size = size.toInt() }
        rs?.getBoolean("b_is_active")?.let { isActive -> reservation.isActive = isActive }
        rs?.getString("b_restrictions")?.let { restrictions ->
            restrictions.split(",").map { reservation.restrictions.add(Endorsement.valueOf(it)) }
        }

        rs?.getTimestamp("b_reservation_time")?.let { timestamp ->
            reservation.reservationTime = timestamp.toInstant().atZone(ZoneId.systemDefault())
        }
        rs?.getTimestamp("b_created_time")?.let { timestamp ->
            reservation.createdTime = timestamp.toInstant().atZone(ZoneId.systemDefault())
        }
        rs?.getTimestamp("b_updated_time")?.let { timestamp ->
            reservation.updatedTime = timestamp.toInstant().atZone(ZoneId.systemDefault())
        }

        return reservation
    }
}