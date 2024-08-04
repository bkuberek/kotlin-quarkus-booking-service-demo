package com.bkuberek.bookings.db.repositories

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

    fun findRestaurantsWithAvailableTable(
        size: Int,
        timeStart: ZonedDateTime,
        timeStop: ZonedDateTime
    ): List<RestaurantTableAvailability> {
        return jdbi.withExtension<List<RestaurantTableAvailability>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantsWithAvailableTable(size, timeStart, timeStop)
        }
    }

    fun findRestaurantsWithAvailableTableAndRestrictions(
        size: Int,
        restrictions: Set<Endorsement>,
        timeStart: ZonedDateTime,
        timeStop: ZonedDateTime
    ): List<RestaurantTableAvailability> {
        return jdbi.withExtension<List<RestaurantTableAvailability>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantsWithAvailableTableAndRestrictions(size, ArrayList(restrictions), timeStart, timeStop)
        }
    }
}