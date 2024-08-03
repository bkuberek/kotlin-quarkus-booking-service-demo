package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.db.entities.ReservationEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class ReservationRepository : PanacheRepository<ReservationEntity> {

    fun findById(id: UUID): ReservationEntity? {
        return find("id", id).firstResult()
    }

    fun findById(id: String): ReservationEntity? {
        return findById(UUID.fromString(id))
    }

    fun deleteById(id: UUID) {
        delete("id", id)
    }

    fun deleteById(id: String) {
        delete("id", UUID.fromString(id))
    }
}