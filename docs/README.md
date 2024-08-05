# Booking Service Demo

This project is intended for demonstration purposes. It proposes a [Solution](./solution.md) to an example [Problem](./problem.md).

The application is a GraphQL API writen in Kotlin and Quarkus. It uses Postgres for persistence.

To learn more about this project and try it out, continue reading.

Thank you for your interest. Feel free to add questions, comments and suggestions [here](https://github.com/bkuberek/kotlin-quarkus-booking-service-demo/issues).


## Documentation

1. [Problem](./problem.md)
2. [Solution](./solution.md)
3. [Running the Application](./running.md)
4. [Using the API](./api.md)
5. [Notes](./notes.md)


## Project Scaffolding

This project was initialized using the command:

```shell
quarkus create app com.bkuberek:booking-service --extension='kotlin,rest-jackson,quarkus-smallrye-graphql,quarkus-jdbc-postgresql,quarkus-jdbi,quarkus-liquibase'
```

Quarkus Extensions

- `kotlin`
- `rest-jackson`
- `quarkus-smallrye-graphql`
- `quarkus-jdbc-postgresql`
- `quarkus-jdbi`
- `quarkus-liquibase`

## References

- Quarkus ([guide](https://quarkus.io/)): Learn more about Quarkus, the Supersonic Subatomic Framework.
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye GraphQL ([guide](https://quarkus.io/guides/smallrye-graphql)): Create GraphQL Endpoints using the code-first approach from MicroProfile GraphQL.
- GraphQL Kotlin ([guide](https://opensource.expediagroup.com/graphql-kotlin/docs/schema-generator/writing-schemas/unions)): **Not a quarkus example** (sprint boot), but it is a good reference for implementing graphql on kotlin.
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC.
- Quarkus Jdbi ([guide](https://github.com/quarkiverse/quarkus-jdbi)): Makes it possible to use JDBI in native executables.
- Jdbi ([guide](https://jdbi.org)): Provides convenient, idiomatic, access to relational data in Java.
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase.
