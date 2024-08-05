# Bookings API

Visit the GraphQL UI at http://localhost:8080/q/graphql-ui/. 
You can access the complete API documentation there. 

Below are some examples. You can also see more examples in 

- [src/test/kotlin/com/bkuberek/bookings/graphql/v1](https://github.com/bkuberek/kotlin-quarkus-booking-service-demo/tree/main/src/test/kotlin/com/bkuberek/bookings/graphql/v1)
- [src/test/resources/graphql](https://github.com/bkuberek/kotlin-quarkus-booking-service-demo/tree/main/src/test/resources/graphql)

## Get a list of all restaurants

Query

```graphql
query AllRestaurants {
  restaurants: allRestaurants {
    id
    name
    endorsements
    tables {
      size
      quantity
    }
  }
}

```

Variables

```json
{}
```

## Find a Table

Query

```graphql
query FindTable(
  $time: DateTime!,
  $size: Int!,
  $endorsements: [Endorsement]!,
) {
  restaurants: findTable(
    time: $time,
    size: $size,
    endorsements: $endorsements
  ) {
    id
    name
    size
    endorsements
    tables {
      size
      quantity
    }
    occupiedTables {
      size
      quantity
    }
    availableTables {
      size
      quantity
    }
  }
}
```

Variables

```json
{
  "size": 2,
  "endorsements": ["gluten"],
  "time": "2024-08-01T20:00:00.00Z"
}
```

## Book a new Reservation

Mutation

```graphql
mutation CreateReservation($request: ReservationRequestInput!) {
  reservation: createReservation(reservationRequest: $request) {
    ... on ReservationInfo {
      id
      name
      size
      restrictions
      restaurant {
        id
        name
      }
      tables {
        size
        quantity
      }
      reservationTime
      createdTime
      updatedTime
      active
    }
    ... on ReservationError {
      error
      message
    }
  }
}
```

Variables

```json
{
  "request": {
    "restaurantId": "B1E6728C-DA7C-4841-BBF3-BA7E97F7E07C",
    "name": "Mike Tyson",
    "size": 3,
    "reservationTime": "2024-08-01T20:00:00.0Z",
    "restrictions": [
      "paleo"
    ]
  }
}
```

## View a Reservation

Mutation

```graphql
query GetReservation($id: String!) {
  reservation: reservation(id: $id) {
    id
    name
    size
    restrictions
    restaurant {
      id
      name
    }
    tables {
      size
      quantity
    }
    reservationTime
    createdTime
    updatedTime
    active
  }
}
```

Variables

```json
{
  "id": "ae5a8791-43dc-4fee-a6c5-5d6be12344ed"
}
```

## View all Reservations for a user

Mutation

```graphql
query GetReservations($name: String!) {
  reservations: reservations(name: $name) {
    id
    name
    size
    restrictions
    restaurant {
      id
      name
    }
    tables {
      size
      quantity
    }
    reservationTime
    createdTime
    updatedTime
    active
  }
}
```

Variables

```json
{
  "name": "Tobias"
}
```
Optional parameter: `restaurantId: UUID`, return reservations for a single restaurant.

## Delete a Reservation

Mutation

```graphql
mutation DeleteReservation($id: String!) {
  reservation: deleteReservation(id: $id) {
    ... on ReservationInfo {
      name
      size
      reservationTime
    }
    ... on ReservationError {
      error
      message
    }
  }
}
```

Variables

```json
{
  "id": "ae5a8791-43dc-4fee-a6c5-5d6be12344ed"
}
```


---

[<- Previous (Running the Application)](./running.md)
| [Next (Notes) ->](./notes.md)
