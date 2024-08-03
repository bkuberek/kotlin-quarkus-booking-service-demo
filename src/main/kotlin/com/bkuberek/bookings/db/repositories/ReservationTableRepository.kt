package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.db.entities.ReservationTableEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class ReservationTableRepository : PanacheRepository<ReservationTableEntity> {

    fun findById(id: UUID): ReservationTableEntity? {
        return find("reservation_id", id).firstResult()
    }

    fun findById(id: String): ReservationTableEntity? {
        return find("reservation_id", UUID.fromString(id)).firstResult()
    }

    fun deleteById(id: String) {
        deleteById(UUID.fromString(id))
    }

    fun deleteById(id: UUID) {
        find("reservation_id", id).stream<ReservationTableEntity>().forEach { delete(it) }
    }
}