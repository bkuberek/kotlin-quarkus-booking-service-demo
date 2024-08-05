package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.db.dao.ReservationDao
import com.bkuberek.bookings.db.entities.ReservationEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.util.*

@ApplicationScoped
class ReservationRepository @Inject constructor(
    private val jdbi: Jdbi
) {

    fun listAllForUser(name: String): Set<ReservationEntity> {
        return jdbi.withExtension<Set<ReservationEntity>, ReservationDao, Exception>(ReservationDao::class.java) { dao ->
            dao.listAllForUser(name)
        }
    }

    fun listAllForUserAndRestaurant(name: String, restaurantId: UUID): Set<ReservationEntity> {
        return jdbi.withExtension<Set<ReservationEntity>, ReservationDao, Exception>(ReservationDao::class.java) { dao ->
            dao.listAllForUserAndRestaurant(name, restaurantId)
        }
    }

    fun getById(id: UUID): ReservationEntity? {
        return getByIds(setOf(id)).firstOrNull()
    }

    fun getByIds(ids: Set<UUID>): Set<ReservationEntity> {
        return jdbi.withExtension<Set<ReservationEntity>, ReservationDao, Exception>(ReservationDao::class.java) { dao ->
            dao.getByIds(ids)
        }
    }

    fun createReservation(reservation: ReservationEntity): ReservationEntity {
        jdbi.useExtension<ReservationDao, Exception>(ReservationDao::class.java) { dao ->
            dao.createReservation(reservation)
        }
        return reservation
    }

    fun deleteById(id: UUID) {
        deleteByIds(setOf(id))
    }

    fun deleteByIds(ids: Set<UUID>) {
        jdbi.useExtension<ReservationDao, Exception>(ReservationDao::class.java) { dao ->
            dao.deleteByIds(ids)
        }
    }
}