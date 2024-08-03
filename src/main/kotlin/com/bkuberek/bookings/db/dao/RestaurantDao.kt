package com.bkuberek.bookings.db.dao

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.mapper.RestaurantRowMapper
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.SqlQuery
import java.time.ZonedDateTime

@RegisterRowMapper(RestaurantRowMapper::class)
interface RestaurantDao {

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            ORDER BY r.name
        """
    )
    @RegisterBeanMapper(RestaurantEntity::class)
    fun listAll(): List<RestaurantEntity>

    fun getById(id: String): RestaurantEntity? {
        return getByIds(listOf(id)).firstOrNull()
    }

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            WHERE r.id in (<ids>)
        """
    )
    @RegisterBeanMapper(RestaurantEntity::class)
    fun getByIds(ids: List<String>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            WHERE r.name in (<names>)
        """
    )
    @RegisterBeanMapper(RestaurantEntity::class)
    fun findByName(name: List<String>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            INNER JOIN restaurant_endorsement e ON e.restaurant_id = r.id
            WHERE e.endorsement in (<endorsements>)
        """
    )
    @RegisterBeanMapper(RestaurantEntity::class)
    fun findRestaurantByEndorsement(@BindList endorsements: List<Endorsement>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            INNER JOIN restaurant_endorsement e ON e.restaurant_id = r.id
            WHERE e.endorsement in (<endorsements>)
        """
    )
    @RegisterBeanMapper(RestaurantEntity::class)
    fun findTable(size: Int, restrictions: List<Endorsement>, time: ZonedDateTime): List<RestaurantEntity>

}