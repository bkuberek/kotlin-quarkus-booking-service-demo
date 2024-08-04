# Restaurant Booking Problem

We’re building the backend for an app that allows users to book a table at a restaurant.

## Functional Requirements

Given a group of friends, 

1. find a restaurant that fits 
    - the dietary restrictions of the whole group
    - with an available table 
    - at a specific time
2. and then create a reservation for that group and time

### Scope 

Assume that reservations last 2 hours. Users may not double-book or have overlapping
reservations. Eg. Jane may not have a reservation at Quintonil at 7pm and at Pujol at 7:30pm.

With this starting point, build endpoints to do the following:
- An endpoint to find restaurants with an available table for a group of users at a specific time.
    - Example: Jack, Jill and Jane are looking for a reservation at 7:30pm on Tuesday. Return a list of restaurants with an available table (for that many people or more) at that time, which meets all of the group’s dietary requirements.
- An endpoint that creates a reservation for a group of users. This will always be called after the search endpoint above.
- An endpoint to delete an existing reservation.

### Out of scope

Only the API is in scope - the UI is out of scope. Authentication is out of scope.

## Provided Data

### Restaurants

| Name                     | No. of two-top tables | No. of four-top tables | No. of six-top tables | Endorsements                             |
|:-------------------------|:----------------------|:-----------------------|:----------------------|:-----------------------------------------|
| Lardo                    | 4                     | 2                      | 1                     | Gluten Free Options                      |
| Panadería Rosetta        | 3                     | 2                      | 0                     | Vegetarian-Friendly, Gluten Free Options |
| Tetetlán                 | 4                     | 2                      | 1                     | Paleo-friendly, Gluten Free Options      |
| Falling Piano Brewing Co | 5                     | 5                      | 5                     |                                          |
| u.to.pi.a                | 2                     | 0                      | 0                     | Vegan-Friendly, Vegetarian-Friendly      |


### Diners

| Name    | Home Location                 | Dietary Restrictions    |
|:--------|:------------------------------|:------------------------|
| Michael | 19.4153107,-99.1804722        | Vegetarian              |
| George  | Michael19.4058242,-99.1671942 | Vegetarian, Gluten-Free |
| Lucile  | 19.3634215,-99.1769323        | Gluten-Free             |
| Gob     | 19.3318331,-99.2078983        | Paleo                   |
| Tobias  | 19.4384214,-99.2036906        |                         |
| Maeby   | 19.4349474,-99.1419256        | Vegan                   |

---

[<- Previous (Home)](./README.md)
| [Next (Solution) -> ](./solution.md)
