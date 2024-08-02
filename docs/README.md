# Bookings Service Demo

This project uses Kotlin and Quarkus, the Supersonic Subatomic Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


1. [Running the Application](./running.md)
2. [Using the API](./api.md)
2. [Notes](./notes.md)

## Related Guides

This project was initialized using the command:

```shell
quarkus create app com.nelo:reservations-service --extension='kotlin,rest-jackson,quarkus-smallrye-graphql,quarkus-jdbc-postgresql,quarkus-hibernate-orm-panache,quarkus-reactive-pg-client,quarkus-liquibase'
```

- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL database using the reactive pattern
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
