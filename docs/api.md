# Bookings API

Visit the GraphQL UI at http://localhost:8080/q/graphql-ui/

## Get a list of all restaurants

Query

```graphql
query AllRestaurants {
  restaurants: restaurants {
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
query FindTables(
  $time: DateTime!,
  $size: Int!,
  $restrictions: [Endorsement]!,
) {
  restaurants: findTable(
    time: $time,
    size: $size,
    restrictions: $restrictions
  ) {
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
{
  "size": 2,
  "restrictions": ["gluten"],
  "time": "2024-08-01T20:00:00.00Z"
}
```

## Book a new Reservation

Mutation

```graphql
mutation BookReservation(
  $request: ReservationRequestInput!,
) {
  reservation: createReservation(reservationRequest: $request) {
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

## Delete a Reservation

Mutation

```graphql
mutation DeleteReservation($id: String!) {
  reservation: deleteReservation(id: $id) {
    name
    size
    reservationTime
  }
}
```

Variables

```json
{
  "id": "ae5a8791-43dc-4fee-a6c5-5d6be12344ed"
}
```