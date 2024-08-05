# Notes

This is my very first project in Kotlin. I decided to take a stab at it since that it is an awesome language and I have
been meaning to learn it for some time.
This codebase is simple, and I'm sure there are other ways, likely better ways, to do it. I really enjoyed working on
this.

Here are some notes about this exercise.

## Process

This was my process during this exercise.

- Idea and conceptualization
- Decide between Rest or GraphQL, Programming Languages and Frameworks.
- Setup project scaffolding
- High level data and api design (mental model)
  - What are the inputs and expected outputs of the api?
  - How to design the database relations to make it possible to filter endorsements and aggregate counts?
- Configure database migrations, including sample data
- Add basic read endpoints and unit tests
- Write all the GraphQL queries to test resources folder and use them to
- Write tests for each endpoint (tests will fail, can mark as disabled for now)
- Experiment with SQL queries
  - Write aggregation queries to list restaurants with available capacity, provided restrictions, party size and time
- TROUBLE: Hibernate+Panache is not working and I have wasted a lot of time.
- Rewrite the Data Access Layer to use Jdbi
- Implement Advanced SQL queries for search
- Enable each of the unit tests and implement solution until test pass
- Repeat for all tests
- Add/Update documentation

## Challenges

- reactive database sessions didn't work in kotlin. Seems to be an issue with graphql library. Will be watching for updates.
  https://github.com/quarkusio/quarkus/issues/34101

- Using data classes as graphql input took some digging to work
  https://github.com/smallrye/smallrye-graphql/issues/1853

- How to use PostgreSQL `Endorsement[]` mapped to `List<Endorsement>` with Panache? | (Fixed with Jdbi)

- **The main challenge** was trying to do custom SQL with Hibernate and Panache. I have come to the conclusion it
  was a waste of time. I switched to Jdbi, rewriting the data access layer.

- While writing the logic for table assignment, Intellij crashed and I lost a lot of unsaved work. ಥ_ಥ

---

[<- Previous (Using the API)](./api.md)
| [Back to beginning (Home) ->](./README.md)
