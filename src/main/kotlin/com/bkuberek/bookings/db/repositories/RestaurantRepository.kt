package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.RESERVATION_DURATION_MINUTES
import com.bkuberek.bookings.RESERVATION_DURATION_SQL_INTERVAL
import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.dao.RestaurantDao
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.entities.RestaurantTableAvailability
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.time.ZonedDateTime
import java.util.*

@ApplicationScoped
class RestaurantRepository @Inject constructor(
    private val jdbi: Jdbi
) {
    fun listAll(): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.listAll()
        }
    }

    fun getById(id: UUID): RestaurantEntity? {
        return jdbi.withExtension<RestaurantEntity, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.getById(id)
        }
    }

    fun getByIds(ids: List<UUID>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.getByIds(ids)
        }
    }

    fun findByName(names: List<String>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findByName(names)
        }
    }

    fun findRestaurantsByEndorsement(endorsements: List<Endorsement>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantsByEndorsement(endorsements)
        }
    }

    fun getAvailableTables(
        restaurantId: UUID,
        time: ZonedDateTime,
        size: Int?
    ): RestaurantTableAvailability? {
        return jdbi.withExtension<RestaurantTableAvailability?, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.getAvailableTables(
                restaurantId,
                size ?: 1,
                time,
                time.plusMinutes(RESERVATION_DURATION_MINUTES),
                RESERVATION_DURATION_SQL_INTERVAL
            ).firstOrNull()
        }
    }

    fun findRestaurantsWithAvailableTable(
        size: Int,
        time: ZonedDateTime
    ): List<RestaurantTableAvailability> {
        return jdbi.withExtension<List<RestaurantTableAvailability>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantsWithAvailableTable(
                size,
                time,
                time.plusMinutes(RESERVATION_DURATION_MINUTES),
                RESERVATION_DURATION_SQL_INTERVAL
            )
        }
    }

    fun findRestaurantsWithAvailableTableAndRestrictions(
        size: Int,
        restrictions: Set<Endorsement>,
        time: ZonedDateTime
    ): List<RestaurantTableAvailability> {
        return jdbi.withExtension<List<RestaurantTableAvailability>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantsWithAvailableTableAndRestrictions(
                size,
                ArrayList(restrictions),
                time,
                time.plusMinutes(RESERVATION_DURATION_MINUTES),
                RESERVATION_DURATION_SQL_INTERVAL
            )
        }
    }
}