# How to run

> **_Note:_** Running locally will require docker.

## Running Tests

The application includes a few tests. You can run those with `test` or `verify`. You can also run these from your IDE.

```shell script
./mvnw verify
```

## Running the Application

The following guide shows you the different options for running the application. 
Once running, you have the following available:

- http://localhost:8080/ -> Booking Service Address
- http://localhost:8080/graphql -> Booking Service GraphQL API Endpoint
- http://localhost:8080/q/graphql-ui/ -> GraphQL UI Client

[GraphQL UI](http://localhost:8080/q/graphql-ui/) provides complete documentation of the API. 
Use it to explore the API capabilities. Some of these endpoints are documented in [Using the API](./api.md).

### Database

> **_Note:_** This is optional, as quarkus dev will provision a database.

To run the service with an external database, uncomment the database configuration settings in `application.properties`

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=${POSTGRES_USERNAME}
quarkus.datasource.password=${POSTGRES_PASSWORD}
quarkus.datasource.jdbc.url=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/bookings
```

Rename `.env.tmpl` to .env

```shell
cp .env.tmpl .env
```

To disable the data being reset on startup set the `quarkus.liquibase.clean-at-start` to `false`
```properties
quarkus.liquibase.clean-at-start=false
```

To disable SQL query log set the `quarkus.hibernate-orm.log.sql` to `false`
```properties
quarkus.hibernate-orm.log.sql=false
```

### Running with docker compose

```shell
docker compose up -d
```

This will create the directory `.docker/`, where the database data folder is mounted for persistency. 
To reset the database, delete this folder prior to running docker compose. Or use liquibase setting above.

### Running in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

### Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

### Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/booking-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

---

[<- Previous (Solution)](./solution.md)
| [Next (Using the API) ->](./api.md)
