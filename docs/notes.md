# Notes

This is my very first project in Kotlin. I decided to take a stab at it since that is an awesome language and I have
been meaning to learn it for some time.
This codebase is simple and I'm sure there are other ways, likely better ways, to do it. I really enjoyed working on
this.

Here are some notes about this exercise.

## Process

1. Setup project scaffolding
2. High level data and api design (mental model)
    - What are the inputs and expected outputs of the api?
    - How to design the database relations to make it possible to filter endorsements and aggregate counts?
3. Configure database migrations, including sample data
4. Add basic read endpoints and unit tests
5. Write all the GraphQL queries to test resources folder and use them to
6. Write tests for each endpoint (tests will fail, can mark as disabled for now)
7. Experiment with SQL queries
    - Write aggregation queries to list restaurants with available capacity, provided restrictions, party size and time
8. Enable each of the unit tests and implement solution until test pass
9. Repeat for all tests
10. Add documentation

## Challenges

- reactive database sessions didn't work in kotlin. Seems to be an issue with graphql library. Will be watching for
  updates.
  https://github.com/quarkusio/quarkus/issues/34101

- Using data classes as graphql input took some digging to work
  https://github.com/smallrye/smallrye-graphql/issues/1853

- How to use PostgreSQL `Endorsement[]` mapped to `List<Endorsement>` with Panache? | @todo

- One of the main challenges was trying to do advanced SQL with Hibernate and Panache. I have come to the conclusion it
  was a waste of time. I'm switching to Jdbi, rewriting the model layer.

## External Database

To run the service with an external database,

- uncomment the database configuration settings in `application.properties`

To disable the data being reset on startup

- set the `quarkus.liquibase.clean-at-start=true` to `false`

To disable SQL query log

- set the `quarkus.hibernate-orm.log.sql=true` to `false`

## Data Model

Since this service does not implement authentication, there was no need to have a user table.
User's name and Restrictions are provided during booking of tables.

![database diagram](database.png)

## SQL

### Total Restaurant Capacity

In the following query `size` is the type of table and `capacity` is the number of tables of that type.

```postgresql
SELECT r.id,
       r.name,
       rt.size,
       SUM(rt.quantity)           as capacity,
       rt.size * SUM(rt.quantity) AS seats
FROM restaurant AS r
         INNER JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
GROUP BY 1, 2, 3
ORDER BY 2, 3;
```

| id                                   | name                     | size | capacity | seats |
|:-------------------------------------|:-------------------------|:-----|:---------|:------|
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | 2    | 5        | 10    |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | 4    | 5        | 20    |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | 6    | 5        | 30    |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | 2    | 4        | 8     |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | 4    | 2        | 8     |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | 6    | 1        | 6     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | 2    | 3        | 6     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | 4    | 2        | 8     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | 6    | 0        | 0     |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | 2    | 2        | 4     |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | 4    | 4        | 16    |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | 6    | 1        | 6     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | 2    | 2        | 4     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | 4    | 0        | 0     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | 6    | 0        | 0     |

Filter results by dietary restrictions

```postgresql
SELECT r.id,
       r.name,
       rt.size,
       SUM(rt.quantity)           as capacity,
       rt.size * SUM(rt.quantity) AS seats
FROM restaurant AS r
         INNER JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
         LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
WHERE re.endorsement IN ('vegan', 'vegetarian')
GROUP BY 1, 2, 3
ORDER BY 2, 3;
```

| id                                   | name              | size | capacity | seats |
|:-------------------------------------|:------------------|:-----|:---------|:------|
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta | 2    | 3        | 6     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta | 4    | 2        | 8     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta | 6    | 0        | 0     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a         | 2    | 4        | 8     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a         | 4    | 0        | 0     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a         | 6    | 0        | 0     |

Restaurant Capacity by table size

```postgresql
SELECT sub.id,
       sub.name,
       sub.endorsements,
       rt.size,
       SUM(rt.quantity)           as capacity,
       rt.size * SUM(rt.quantity) AS seats
FROM (SELECT r.id,
             r.name,
             ARRAY_AGG(re.endorsement) AS endorsements
      FROM restaurant AS r
               LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
      GROUP BY 1, 2) AS sub
         INNER JOIN restaurant_table AS rt ON rt.restaurant_id = sub.id
GROUP BY 1, 2, 3, 4
ORDER BY 2, 3;
```

| id                                   | name                     | endorsements        | size | capacity | seats |
|:-------------------------------------|:-------------------------|:--------------------|:-----|:---------|:------|
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | {}                  | 2    | 5        | 10    |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | {}                  | 4    | 5        | 20    |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | {}                  | 6    | 5        | 30    |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | {gluten}            | 2    | 4        | 8     |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | {gluten}            | 4    | 2        | 8     |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | {gluten}            | 6    | 1        | 6     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | {gluten,vegetarian} | 2    | 3        | 6     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | {gluten,vegetarian} | 4    | 2        | 8     |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | {gluten,vegetarian} | 6    | 0        | 0     |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | {gluten,paleo}      | 2    | 2        | 4     |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | {gluten,paleo}      | 4    | 4        | 16    |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | {gluten,paleo}      | 6    | 1        | 6     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | {vegan,vegetarian}  | 2    | 2        | 4     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | {vegan,vegetarian}  | 4    | 0        | 0     |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | {vegan,vegetarian}  | 6    | 0        | 0     |

This query lists restaurants, their endorsements and total capacity.
In this case total capacity is the number of people the venue can accommodate.

```postgresql
SELECT sub.id,
       sub.name,
       sub.endorsements,
       SUM(rt.size * rt.quantity) AS total_capacity
FROM (SELECT r.id,
             r.name,
             ARRAY_AGG(re.endorsement) AS endorsements
      FROM restaurant AS r
               LEFT JOIN restaurant_endorsement AS re ON re.restaurant_id = r.id
      GROUP BY 1, 2) AS sub
         INNER JOIN restaurant_table AS rt ON rt.restaurant_id = sub.id
GROUP BY 1, 2, 3
ORDER BY total_capacity;
```

| id                                   | name                     | endorsements        | total\_capacity |
|:-------------------------------------|:-------------------------|:--------------------|:----------------|
| d42c8608-7d52-4ea3-823f-c59b68a33407 | u.to.pi.a                | {vegan,vegetarian}  | 4               |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | Panadería Rosetta        | {gluten,vegetarian} | 14              |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | Lardo                    | {gluten}            | 22              |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | Tetetlán                 | {gluten,paleo}      | 26              |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | Falling Piano Brewing Co | {}                  | 60              |

## Find Reserved Capacity

We have determined that a reservation lasts 2 hours. We are looking for how many tables are booked within 2 hours prior
of TIME.
These are tables that are still in use.

List of restaurant IDs and their current booked capacity between (TIME - 2 hours) and TIME.

```postgresql
-- INPUT TIME: 2024-08-03 21:00:00.000000 +00:00
-- Looking between (TIME - 2) and TIME
SELECT b.restaurant_id,
       bt.size,
       SUM(bt.quantity) as occupied
FROM reservation AS b
         INNER JOIN restaurant AS r ON b.restaurant_id = r.id
         INNER JOIN reservation_table AS bt ON bt.reservation_id = b.id
WHERE b.reservation_time > '2024-08-03 19:00:00.000000 +00:00'
  AND b.reservation_time <= '2024-08-03 21:00:00.000000 +00:00'
GROUP BY 1, 2;
```

| restaurant\_id                       | size | occupied |
|:-------------------------------------|:-----|:---------|
| 635dc3bd-c515-4d41-848b-bc487bb13810 | 6    | 1        |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | 2    | 1        |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | 2    | 1        |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | 6    | 1        |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | 2    | 2        | <-- 2 x size 2 tables
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | 4    | 1        |

### Challenges

If user `A` wants to book at 7pm. At 7pm at the table is available, however,
there is an existing reservation for a table at 7:15. This would cause a conflict.
The existing reservation holder for 7:15 will arrive and potentially not have a table.

#### Proposals

Also check in the future that the table capacity is still available.

## Find Available Capacity

Building on top of the previous queries, we can now subtract occupied from total, and we have available tables.

```postgresql
WITH venue AS (SELECT r.id,
                      rt.size,
                      SUM(rt.quantity) as capacity
               FROM restaurant AS r
                        INNER JOIN restaurant_table AS rt ON rt.restaurant_id = r.id
               GROUP BY 1, 2),
     booked AS (SELECT b.restaurant_id  AS id,
                       bt.size,
                       SUM(bt.quantity) as occupied
                FROM reservation AS b
                         INNER JOIN restaurant AS r ON b.restaurant_id = r.id
                         INNER JOIN reservation_table AS bt ON bt.reservation_id = b.id
                WHERE b.reservation_time > '2024-08-04 19:00:00.000000 +00:00'
                  AND b.reservation_time <= '2024-08-04 21:00:00.000000 +00:00'
                GROUP BY 1, 2)

SELECT venue.id,
       venue.size,
       COALESCE(venue.capacity, 0)                                AS capacity,
       COALESCE(booked.occupied, 0)                               AS occupied,
       COALESCE(venue.capacity, 0) - COALESCE(booked.occupied, 0) AS available
FROM venue
         LEFT JOIN booked on (booked.id = venue.id AND booked.size = venue.size)
ORDER BY 1, 2
```

| id                                   | size | capacity | occupied | available |
|:-------------------------------------|:-----|:---------|:---------|:----------|
| 635dc3bd-c515-4d41-848b-bc487bb13810 | 2    | 4        | 0        | 4         |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | 4    | 2        | 0        | 2         |
| 635dc3bd-c515-4d41-848b-bc487bb13810 | 6    | 1        | 1        | 0         |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | 2    | 2        | 1        | 1         |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | 4    | 4        | 0        | 4         |
| b1e6728c-da7c-4841-bbf3-ba7e97f7e07c | 6    | 1        | 0        | 1         |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | 2    | 5        | 1        | 4         |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | 4    | 5        | 0        | 5         |
| c52e1d11-757a-48dc-88e8-4bf9866ca53a | 6    | 5        | 1        | 4         |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | 2    | 2        | 2        | 0         |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | 4    | 0        | 0        | 0         |
| d42c8608-7d52-4ea3-823f-c59b68a33407 | 6    | 0        | 0        | 0         |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | 2    | 3        | 0        | 3         |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | 4    | 2        | 1        | 1         |
| dfe2cab1-6a39-4426-8937-c1d89403e0f0 | 6    | 0        | 0        | 0         |

