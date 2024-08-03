# Requirements

We’re building the backend for an app that allows users to do the following: 

Given a group of friends, 

1. find a restaurant that fits the dietary restrictions of the whole group, with an available table at a specific time,
2. and then create a reservation for that group and time

## Scope 

Assume that reservations last 2 hours. Users may not double-book or have overlapping
reservations. Eg. Jane may not have a reservation at Quintonil at 7pm and at Pujol at 7:30pm.

With this starting point, build endpoints to do the following:
- An endpoint to find restaurants with an available table for a group of users at a specific time.
    - Example: Jack, Jill and Jane are looking for a reservation at 7:30pm on Tuesday. Return a list of restaurants with an available table (for that many people or more) at that time, which meets all of the group’s dietary requirements.
- An endpoint that creates a reservation for a group of users. This will always be called after the search endpoint above.
- An endpoint to delete an existing reservation.

## Out of scope

Only the API is in scope - the UI is out of scope. Authentication is out of scope.
