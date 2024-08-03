package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.dao.RestaurantDao
import com.bkuberek.bookings.db.entities.RestaurantEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jdbi.v3.core.Jdbi
import java.time.ZonedDateTime

@ApplicationScoped
class RestaurantRepository @Inject constructor(
    private val jdbi: Jdbi
) {
    fun listAll(): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.listAll()
        }
    }

    fun getById(id: String): RestaurantEntity {
        return jdbi.withExtension<RestaurantEntity, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.getById(id)
        }
    }

    fun getByIds(ids: List<String>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.getByIds(ids)
        }
    }

    fun findByName(names: List<String>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findByName(names)
        }
    }

    fun findRestaurantByEndorsement(endorsements: List<Endorsement>): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findRestaurantByEndorsement(endorsements)
        }
    }

    fun findTable(size: Int, restrictions: List<Endorsement>, time: ZonedDateTime): List<RestaurantEntity> {
        return jdbi.withExtension<List<RestaurantEntity>, RestaurantDao, Exception>(RestaurantDao::class.java) { dao ->
            dao.findTable(size, restrictions, time)
        }
    }
}