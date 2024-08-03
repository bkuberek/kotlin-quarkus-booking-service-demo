package com.bkuberek.bookings.db.dao

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantEndorsementEntity
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.entities.RestaurantTableEntity
import org.jdbi.v3.core.result.LinkedHashMapRowReducer
import org.jdbi.v3.core.result.RowView
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.UseRowReducer
import java.time.ZonedDateTime
import java.util.*


interface RestaurantDao {

    @SqlQuery(
        """
            SELECT r.id             as r_id,
                   r.name           as r_name,
                   r.created_time   as r_created_time,
                   r.updated_time   as r_updated_time,
                   re.restaurant_id as e_restaurant_id,
                   re.endorsement   as e_endorsement,
                   rt.restaurant_id as t_restaurant_id,
                   rt.size          as t_size,
                   rt.quantity      as t_quantity
            FROM restaurant AS r
                     LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
                     LEFT JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
            ORDER BY r_name, t_size;
        """
    )
    @UseRowReducer(RestaurantRowReducer::class)
    fun listAll(): List<RestaurantEntity>

    fun getById(id: String): RestaurantEntity? {
        return getByIds(listOf(id)).firstOrNull()
    }

    @SqlQuery(
        """
            SELECT r.id             as r_id,
                   r.name           as r_name,
                   r.created_time   as r_created_time,
                   r.updated_time   as r_updated_time,
                   re.restaurant_id as e_restaurant_id,
                   re.endorsement   as e_endorsement,
                   rt.restaurant_id as t_restaurant_id,
                   rt.size          as t_size,
                   rt.quantity      as t_quantity
            FROM restaurant AS r
                     LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
                     LEFT JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
            WHERE r.id in (<ids>)
            ORDER BY r_name, t_size;
        """
    )
    @UseRowReducer(RestaurantRowReducer::class)
    fun getByIds(ids: List<String>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.id             as r_id,
                   r.name           as r_name,
                   r.created_time   as r_created_time,
                   r.updated_time   as r_updated_time,
                   re.restaurant_id as e_restaurant_id,
                   re.endorsement   as e_endorsement,
                   rt.restaurant_id as t_restaurant_id,
                   rt.size          as t_size,
                   rt.quantity      as t_quantity
            FROM restaurant AS r
                     LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
                     LEFT JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
            WHERE r.name in (<names>)
            ORDER BY r_name, t_size;
        """
    )
    @UseRowReducer(RestaurantRowReducer::class)
    fun findByName(name: List<String>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            INNER JOIN restaurant_endorsement e ON e.restaurant_id = r.id
            WHERE e.endorsement in (<endorsements>)
        """
    )
    @UseRowReducer(RestaurantRowReducer::class)
    fun findRestaurantByEndorsement(@BindList endorsements: List<Endorsement>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.* FROM restaurant r 
            INNER JOIN restaurant_endorsement e ON e.restaurant_id = r.id
            WHERE e.endorsement in (<endorsements>)
        """
    )
    @UseRowReducer(RestaurantRowReducer::class)
    fun findTable(size: Int, restrictions: List<Endorsement>, time: ZonedDateTime): List<RestaurantEntity>

    /**
     * Combines multiple rows into an entity and its relationships.
     */
    class RestaurantRowReducer : LinkedHashMapRowReducer<UUID, RestaurantEntity> {
        override fun accumulate(container: MutableMap<UUID, RestaurantEntity>?, rowView: RowView?) {
            if (rowView != null && container != null) {

                val restaurant = container.computeIfAbsent(
                    rowView.getColumn("r_id", UUID::class.java)
                ) { _ -> rowView.getRow(RestaurantEntity::class.java) }

                if (rowView.getColumn("t_restaurant_id", UUID::class.java) != null) {
                    restaurant.tables.add(rowView.getRow(RestaurantTableEntity::class.java))
                }

                if (rowView.getColumn("e_restaurant_id", UUID::class.java) != null) {
                    restaurant.endorsements.add(rowView.getRow(RestaurantEndorsementEntity::class.java))
                }

            }
        }

    }
}