# fapptime
<a href="https://github.com/Qrivi/fappserver/actions"><img alt="CI" align="right" src="https://github.com/Qrivi/fappserver/workflows/CI/badge.svg?branch=develop"></a>

---

👷 Spring Boot back-end _currently being written_ in Kotlin for an application I hope I can start developing soon.

---

### Run as in production

- Fetch a PostgreSQL image and the `fappserver` image from Docker Hub, and run as intended for production.
```shell
docker rmi -f qrivi/fappserver # Optional, ensures latest image is fetched
docker-compose up
```
⚠️ Will give errors for now, as database won't have been set up properly, and `fappserver` image on DockerHub is out of date (too early to put much time in Docker).

### Run for development

- Run the database from the PostgreSQL Docker image in `docker-compose.yml`.
```shell
docker-compose run --service-ports fappstore
```

- Local database works too, if you create user, password and database (already done if using Docker).
```shell
psql -c "CREATE ROLE fappdb_user WITH LOGIN superuser PASSWORD 'fappdb_password';"
psql -c "CREATE DATABASE fappdb_loc WITH OWNER fappdb_user;"
```

- Set up the database with the nifty Liquibase Gradle plugin.
```shell
./gradlew dropAll update -PrunList=main
```

- Deploy and run a `fappserver` instance.
```shell
./gradlew bootRun
```

### Other cool things

- Build a local Docker image with `fappserver` deployed.
```shell
./gradlew clean build jibDockerBuild
```
