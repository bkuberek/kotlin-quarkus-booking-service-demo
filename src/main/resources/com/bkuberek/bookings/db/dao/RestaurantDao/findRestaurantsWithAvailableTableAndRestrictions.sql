WITH venue AS (SELECT r.id,
                      rt.size,
                      SUM(rt.quantity) as capacity
               FROM restaurant AS r
                        INNER JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
               GROUP BY 1, 2),
     endorsing AS (SELECT r.id,
                          re.endorsement
                   FROM restaurant AS r
                            INNER JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
                   WHERE re.endorsement IN (<restrictions>)
                   GROUP BY 1, 2),
     booked AS (SELECT b.restaurant_id  AS id,
                       bt.size,
                       SUM(bt.quantity) as occupied
                FROM reservation AS b
                         INNER JOIN restaurant AS r ON b.restaurant_id = r.id
                         INNER JOIN reservation_table AS bt ON bt.reservation_id = b.id
                WHERE b.is_active = true
                  AND ((b.reservation_time >= :time_start AND :time_stop > b.reservation_time)
                    OR (b.reservation_time + INTERVAL '<time_interval>' >= :time_start
                        AND :time_stop > b.reservation_time + INTERVAL '<time_interval>'))
                GROUP BY 1, 2)

SELECT sub.id,
       rr.name,
       sub.size,
       sub.total,
       sub.occupied,
       sub.available,
       sub.available_seats,
       ARRAY_AGG(endorsing.endorsement) as endorsements
FROM (SELECT venue.id,
             venue.size,
             COALESCE(venue.capacity, 0)                                               AS total,
             COALESCE(booked.occupied, 0)                                              AS occupied,
             COALESCE(venue.capacity, 0) - COALESCE(booked.occupied, 0)                AS available,
             venue.size * (COALESCE(venue.capacity, 0) - COALESCE(booked.occupied, 0)) AS available_seats
      FROM venue
               LEFT JOIN booked on (booked.id = venue.id AND booked.size = venue.size)) as sub
         INNER JOIN restaurant as rr ON rr.id = sub.id
         INNER JOIN endorsing on endorsing.id = sub.id
WHERE sub.available_seats >= :size
GROUP BY 1, 2, 3, 4, 5, 6, 7
ORDER BY 2, 3
;
