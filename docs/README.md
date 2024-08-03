# Bookings Service Demo

This project uses Kotlin and Quarkus, the Supersonic Subatomic Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


## Table of Contents

1. [Requirements](./requirements.md)
2. [Running the Application](./running.md)
3. [Using the API](./api.md)
4. [Notes](./notes.md)


## Project Scaffolding

This project was initialized using the command:

```shell
quarkus create app com.nelo:reservations-service --extension='kotlin,rest-jackson,quarkus-smallrye-graphql,quarkus-jdbc-postgresql,quarkus-hibernate-orm-panache,quarkus-liquibase'
```

## Related Guides

- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- SmallRye GraphQL ([guide](https://quarkus.io/guides/smallrye-graphql)): Create GraphQL Endpoints using the code-first approach from MicroProfile GraphQL
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase


## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### SmallRye GraphQL

Start coding with this Hello GraphQL Query

[Related guide section...](https://quarkus.io/guides/smallrye-graphql)
