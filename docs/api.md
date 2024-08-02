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
  "restrictions": [],
  "time": "2024-08-01T20:00:00.00Z"
}
```

## Book a new Reservation

Mutation 
```graphql
mutation BookReservation(
  $request: ReservationRequestInput!,
) {
  reservation: createReservation(
    reservationRequest: $request
  ) {
    id
    name
    active
    size
    reservationTime
    restrictions
    createdTime
    updatedTime
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
    "restrictions": ["paleo"]
  }
}
```

## Delete a Reservation

Mutation
```graphql
mutation DeleteReservation($id: String!) {
  reservation: deleteReservation(id: $id) {
    id
    name
    active
    size
    reservationTime
    restrictions
    createdTime
    updatedTime
  }
}
```
Variables
```json
{
  "id": "ENTER ID"
}
```