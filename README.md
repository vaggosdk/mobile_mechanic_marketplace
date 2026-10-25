# mobile_mechanic_marketplace
A platform inspired by Uber/Wolt principles that connects vehicle owners with verified mechanics for roadside assistance, diagnostics, maintenance, and workshop services.

## Local development

The checked-in `.env` file provides shared local-development defaults for Docker Compose and Spring Boot. It configures the PostGIS database on `localhost:5434` and the application on `localhost:8080`. The database credentials in that file are for local development only; do not reuse them in production.

After cloning the repository, start PostgreSQL and the application:

```powershell
docker compose up -d postgres
.\gradlew.bat bootRun
```

Liquibase applies the database schema when the application starts. To use different local ports or database settings, override the corresponding variables (`POSTGRES_HOST_PORT`, `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`, or `SERVER_PORT`) in the environment where you run the commands.
