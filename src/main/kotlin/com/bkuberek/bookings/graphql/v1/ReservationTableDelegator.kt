package com.bkuberek.bookings.graphql.v1

import com.bkuberek.bookings.db.entities.ReservationEntity
import com.bkuberek.bookings.db.entities.ReservationTableEntity
import com.bkuberek.bookings.db.entities.RestaurantEntity
import com.bkuberek.bookings.db.entities.RestaurantTableEntity
import com.bkuberek.bookings.graphql.v1.models.ReservationRequest
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import java.util.*

@ApplicationScoped
class ReservationTableDelegator {
    private val logger = Logger.getLogger(ReservationTableDelegator::class.java)

    fun assignTables(
        restaurant: RestaurantEntity,
        availableTables: Set<RestaurantTableEntity>,
        reservationRequest: ReservationRequest
    ): ReservationEntity {
        if (availableTables.isEmpty()) {
            throw IllegalArgumentException("No available tables")
        }

        val randomUUID = UUID.randomUUID()
        val reservation = ReservationEntity()

        reservation.id = randomUUID
        reservation.restaurant = restaurant
        reservation.name = reservationRequest.name
        reservation.size = reservationRequest.size
        reservation.reservationTime = reservationRequest.reservationTime
        reservationRequest.restrictions.forEach { reservation.restrictions.add(it) }

        var standing = reservationRequest.size
        var maxSeats = 0

        // Map to quickly reference tables by size. Used later to increment quantity.
        val reservedTables: MutableMap<Int, ReservationTableEntity> = mutableMapOf()

        // We know how many table tables are in the list but until we sum their quantities we don't know we can fit.
        // Since we have to do at least one pass to count all seats, we also assign tables as we go.
        // It may be necessary to do more than one pass. If the group size is greater than the biggest table size,
        // we have to split the group into multiple tables. We assign the smallest number of tables possible.
        // !! Important: it is expected that the SQL orders the tables by SIZE in ascending order.
        while (standing > 0) {
            // This only evaluates to true on the first iteration, so that we only count the tables once.
            val doCount = maxSeats == 0

            // Keep track of the last table.
            // Assuming our query is right, the last item should be the largest table.
            var lastSeen: RestaurantTableEntity? = null
            for (table in availableTables) {
                // Skip if the table supply is depleted
                if (table.quantity == 0) {
                    continue
                }
                // Only counts seats in first pass
                if (doCount) {
                    maxSeats += table.size * table.quantity
                }
                // Does the group fit in a single table?
                if (table.size >= standing) {
                    // Have we seen this table before?
                    // if we have already reserved this table before, and it still has availability,
                    // increment reservation quantity rather than creating a new record
                    assignTable(table, reservation, reservedTables, randomUUID)
                    standing -= kotlin.math.min(standing, table.size)
                    table.quantity -= 1
                    // We found our table, no need to look any further
                    break
                }
                lastSeen = table
            }

            // We've seen the whole list, and we didn't find a table.
            // We messed up somewhere and the list of tables doesn't add up to the number of people.
            if (standing > 0 && maxSeats < reservationRequest.size) {
                logger.error(
                    "Table Assignment Error: Expected at least $reservationRequest.size seats but found $maxSeats. Request: %s"
                        .format(this)
                )
                throw IllegalArgumentException("Not enough seats")
            }

            // If we do have enough capacity but the tables are not large enough, we split the group
            if (standing > 0 && lastSeen != null && lastSeen.quantity > 0) {
                // seat part of the group at the last largest table
                assignTable(lastSeen, reservation, reservedTables, randomUUID)
                standing -= lastSeen.size
                lastSeen.quantity -= 1
            }
        }

        // This should not have happened since we throw an exception above, but just in case,
        if (reservation.tables.isEmpty()) {
            logger.error(
                "Table Assignment Counting Error: Expected at least $reservationRequest.size seats but found $maxSeats. Request: %s"
                    .format(this)
            )
            throw IllegalArgumentException("Not enough seats")
        }

        return reservation
    }

    private fun assignTable(
        table: RestaurantTableEntity,
        reservation: ReservationEntity,
        reservedTables: MutableMap<Int, ReservationTableEntity>,
        randomUUID: UUID
    ) {
        val reservedTable = reservedTables[table.size]
        if (reservedTable == null) {
            val newTable = reservationTableEntity(randomUUID, table)
            reservedTables[table.size] = newTable
            reservation.tables.add(newTable)
        } else {
            reservedTable.quantity += 1
        }
    }

    private fun reservationTableEntity(
        reservationId: UUID,
        availableTable: RestaurantTableEntity
    ): ReservationTableEntity {
        val reservationTableEntity = ReservationTableEntity()
        reservationTableEntity.reservationId = reservationId
        reservationTableEntity.size = availableTable.size
        reservationTableEntity.quantity = 1
        return reservationTableEntity
    }
}