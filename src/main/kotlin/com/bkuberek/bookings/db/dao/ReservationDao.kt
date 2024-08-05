package com.bkuberek.bookings.db.dao

import com.bkuberek.bookings.db.entities.ReservationEntity
import com.bkuberek.bookings.db.entities.ReservationTableEntity
import com.bkuberek.bookings.db.entities.RestaurantEntity
import org.jdbi.v3.core.result.LinkedHashMapRowReducer
import org.jdbi.v3.core.result.RowView
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.statement.UseRowReducer
import org.jdbi.v3.sqlobject.transaction.Transaction
import java.util.*

interface ReservationDao {

    @SqlQuery(
        """
            SELECT bb.id               as b_id,
                   bb.restaurant_id    as b_restaurant_id,
                   bb.name             as b_name,
                   bb.size             as b_size,
                   bb.is_active        as b_is_active,
                   bb.reservation_time as b_reservation_time,
                   bb.created_time     as b_created_time,
                   bb.updated_time     as b_updated_time,
                   bb.restrictions     as b_restrictions,
                   rr.id               as r_id,
                   rr.name             as r_name,
                   rr.created_time     as r_created_time,
                   rr.updated_time     as r_updated_time,
                   bt.reservation_id   as bt_reservation_id,
                   bt.size             as bt_size,
                   bt.quantity         as bt_quantity
            FROM reservation AS bb
                     INNER JOIN restaurant AS rr ON rr.id = bb.restaurant_id
                     INNER JOIN reservation_table AS bt ON bt.reservation_id = bb.id
            WHERE bb.name = :name
            ORDER BY 3, 4;
        """
    )
    @UseRowReducer(ReservationRowReducer::class)
    fun listAllForUser(@Bind("name") name: String): Set<ReservationEntity>

    @SqlQuery(
        """
            SELECT bb.id               as b_id,
                   bb.restaurant_id    as b_restaurant_id,
                   bb.name             as b_name,
                   bb.size             as b_size,
                   bb.is_active        as b_is_active,
                   bb.reservation_time as b_reservation_time,
                   bb.created_time     as b_created_time,
                   bb.updated_time     as b_updated_time,
                   bb.restrictions     as b_restrictions,
                   rr.id               as r_id,
                   rr.name             as r_name,
                   rr.created_time     as r_created_time,
                   rr.updated_time     as r_updated_time,
                   bt.reservation_id   as bt_reservation_id,
                   bt.size             as bt_size,
                   bt.quantity         as bt_quantity
            FROM reservation AS bb
                     INNER JOIN restaurant AS rr ON rr.id = bb.restaurant_id
                     INNER JOIN reservation_table AS bt ON bt.reservation_id = bb.id
            WHERE bb.name = :name
              AND bb.restaurant_id = :restaurant_id
            ORDER BY 3, 4;
        """
    )
    @UseRowReducer(ReservationRowReducer::class)
    fun listAllForUserAndRestaurant(
        @Bind("name") name: String,
        @Bind("restaurant_id") restaurantId: UUID
    ): Set<ReservationEntity>

    fun getById(id: UUID): ReservationEntity? {
        return getByIds(setOf(id)).firstOrNull()
    }

    @SqlQuery(
        """
            SELECT bb.id               as b_id,
                   bb.restaurant_id    as b_restaurant_id,
                   bb.name             as b_name,
                   bb.size             as b_size,
                   bb.is_active        as b_is_active,
                   bb.reservation_time as b_reservation_time,
                   bb.created_time     as b_created_time,
                   bb.updated_time     as b_updated_time,
                   bb.restrictions     as b_restrictions,
                   rr.id               as r_id,
                   rr.name             as r_name,
                   rr.created_time     as r_created_time,
                   rr.updated_time     as r_updated_time,
                   bt.reservation_id   as bt_reservation_id,
                   bt.size             as bt_size,
                   bt.quantity         as bt_quantity
            FROM reservation AS bb
                     INNER JOIN restaurant AS rr ON rr.id = bb.restaurant_id
                     INNER JOIN reservation_table AS bt ON bt.reservation_id = bb.id
            WHERE bb.id IN (<ids>)
            ORDER BY 3, 4;
        """
    )
    @UseRowReducer(ReservationRowReducer::class)
    fun getByIds(@BindList("ids") ids: Set<UUID>): Set<ReservationEntity>

    @SqlUpdate(
        """
            INSERT INTO reservation (id, restaurant_id, name, size, restrictions, reservation_time)
            VALUES (:b.id, :b.restaurant.id, :b.name, :b.size, array_to_string(:b.restrictions, ','), :b.reservationTime)
        """
    )
    fun saveReservation(@BindBean("b") entity: ReservationEntity)

    @SqlUpdate(
        """
            INSERT INTO reservation_table (reservation_id, size, quantity)
            VALUES (:t.reservationId, :t.size, :t.quantity)
        """
    )
    fun saveReservationTable(@BindBean("t") entity: ReservationTableEntity)

    @Transaction
    fun createReservation(reservationEntity: ReservationEntity) {
        saveReservation(reservationEntity)
        reservationEntity.tables.forEach { saveReservationTable(it) }
    }

    fun deleteById(id: UUID) {
        deleteByIds(setOf(id))
    }

    @Transaction
    @SqlUpdate("DELETE FROM reservation WHERE id IN (<ids>)")
    fun deleteByIds(@BindList("ids") ids: Set<UUID>)

    class ReservationRowReducer : LinkedHashMapRowReducer<UUID, ReservationEntity> {
        override fun accumulate(container: MutableMap<UUID, ReservationEntity>?, rowView: RowView?) {
            if (rowView != null && container != null) {

                val reservation = container.computeIfAbsent(
                    rowView.getColumn("b_id", UUID::class.java)
                ) { _ -> rowView.getRow(ReservationEntity::class.java) }

                if (rowView.getColumn("bt_reservation_id", UUID::class.java) != null) {
                    reservation.tables.add(rowView.getRow(ReservationTableEntity::class.java))
                }

                if (rowView.getColumn("r_id", UUID::class.java) != null) {
                    reservation.restaurant = rowView.getRow(RestaurantEntity::class.java)
                }
            }
        }

    }

}