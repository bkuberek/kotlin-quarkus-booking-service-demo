package com.bkuberek.bookings.db.dao

import com.bkuberek.bookings.db.Endorsement
import com.bkuberek.bookings.db.entities.RestaurantEndorsementEntity
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.entities.RestaurantTableAvailability
import com.bkuberek.bookings.db.entities.RestaurantTableEntity
import org.jdbi.v3.core.result.LinkedHashMapRowReducer
import org.jdbi.v3.core.result.RowView
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.customizer.DefineNamedBindings
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator
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

    fun getById(id: UUID): RestaurantEntity? {
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
    fun getByIds(@BindList("ids") ids: List<UUID>): List<RestaurantEntity>

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
    fun findByName(@BindList("names") names: List<String>): List<RestaurantEntity>

    @SqlQuery(
        """
            SELECT r.id
            FROM restaurant r 
            INNER JOIN restaurant_endorsement e ON e.restaurant_id = r.id
            WHERE e.endorsement in (<endorsements>)
        """
    )
    fun findRestaurantIdsByEndorsement(@BindList("endorsements") endorsements: List<Endorsement>): List<UUID>

    fun findRestaurantsByEndorsement(endorsements: List<Endorsement>): List<RestaurantEntity> {
        return getByIds(findRestaurantIdsByEndorsement(endorsements))
    }

    @SqlQuery
    @UseClasspathSqlLocator
    @DefineNamedBindings
    @UseRowReducer(RestaurantAvailabilityRowReducer::class)
    @RegisterBeanMapper(RestaurantTableAvailability::class)
    fun findRestaurantsWithAvailableTable(
        @Bind("size") size: Int,
        @Bind("time_start") timeStart: ZonedDateTime,
        @Bind("time_stop") timeStop: ZonedDateTime
    ): List<RestaurantTableAvailability>

    @SqlQuery
    @UseClasspathSqlLocator
    @DefineNamedBindings
    @UseRowReducer(RestaurantAvailabilityRowReducer::class)
    @RegisterBeanMapper(RestaurantTableAvailability::class)
    fun findRestaurantsWithAvailableTableAndRestrictions(
        @Bind("size") size: Int,
        @BindList("restrictions") restrictions: List<Endorsement>,
        @Bind("time_start") timeStart: ZonedDateTime,
        @Bind("time_stop") timeStop: ZonedDateTime
    ): List<RestaurantTableAvailability>

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

    class RestaurantAvailabilityRowReducer : LinkedHashMapRowReducer<UUID, RestaurantTableAvailability> {
        override fun accumulate(container: MutableMap<UUID, RestaurantTableAvailability>?, rowView: RowView?) {
            if (rowView != null && container != null) {

                val availability = container.computeIfAbsent(
                    rowView.getColumn("id", UUID::class.java)
                ) { _ -> rowView.getRow(RestaurantTableAvailability::class.java) }

                // using getColumn with String type because Int::class.java is throwing:
                //     @todo: java.lang.ClassCastException: Cannot cast java.lang.Integer to int

                val table = RestaurantTableEntity()
                table.restaurantId = availability.id
                table.size = rowView.getColumn("size", String::class.java).toInt()
                table.quantity = rowView.getColumn("total", String::class.java).toInt()
                availability.tables.add(table)

                val occupiedTable = RestaurantTableEntity()
                occupiedTable.restaurantId = availability.id
                occupiedTable.size = rowView.getColumn("size", String::class.java).toInt()
                occupiedTable.quantity = rowView.getColumn("occupied", String::class.java).toInt()
                availability.occupiedTables.add(occupiedTable)

                val availableTable = RestaurantTableEntity()
                availableTable.restaurantId = availability.id
                availableTable.size = rowView.getColumn("size", String::class.java).toInt()
                availableTable.quantity = rowView.getColumn("available", String::class.java).toInt()
                availability.availableTables.add(availableTable)

            }
        }

    }
}