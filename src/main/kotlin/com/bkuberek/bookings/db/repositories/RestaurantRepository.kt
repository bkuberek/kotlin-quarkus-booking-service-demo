package com.bkuberek.bookings.db.repositories

import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.Endorsement
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.time.ZonedDateTime

@ApplicationScoped
class RestaurantRepository : PanacheRepository<RestaurantEntity> {

    fun findByName(name: String): RestaurantEntity? = find("name", name).firstResult()

    fun findTable(size: Int, restrictions: List<Endorsement>, time: ZonedDateTime): List<RestaurantEntity> {
        return listAll()
    }
}