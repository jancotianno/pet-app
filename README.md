# Pet App


This is a sample Pet Application built with Spring Boot.

It currently uses an in-memory database for data storage but is designed to support easy migration to a non-relational database like MongoDB in the future.

The application currently runs with an in-memory database, activated by the Spring profile `in-memory`.
This allows quick testing and development without the need for an external database.

A MongoDB profile has been prepared (`mongodb`) with a corresponding repository interface extending `MongoRepository`. Although MongoDB is not currently active, the necessary code structure and configuration files are in place to support a smooth migration.

- The MongoDB repository interface is annotated with `@Profile("mongodb")`.
- Entity classes will need to be annotated with `@Document` when MongoDB is activated.
- Configuration properties for MongoDB are in `application-mongodb.properties`.
- Switching to MongoDB is as simple as changing the active Spring profile to `mongodb`.
