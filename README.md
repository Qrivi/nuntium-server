# nuntium-server
<a href="https://github.com/Qrivi/nuntium-server/actions"><img alt="CI" align="right" src="https://github.com/Qrivi/nuntium-server/workflows/CI/badge.svg?branch=develop"></a>

---

👷 Spring Boot back-end _currently being written_ in Kotlin.

---

## How to run

> Put one foot in front of the other. Repeat this process rapidly.

Now my dad is proud, below are the steps to get up and running after a `git clone`.

We will need a database named `nuntium_loc` on localhost with as owner the user `nuntium_user` with
password `nuntium_password`. If you have Docker installed, you can quickly spin up this database as
follows:
```shell
docker-compose run --service-ports nuntium-store
```

Without Docker, you will need to create user and database yourself.
```shell
psql -c "CREATE ROLE nuntium_user WITH LOGIN superuser PASSWORD 'nuntium_password';"
psql -c "CREATE DATABASE nuntium_loc WITH OWNER nuntium_user;"
```

Next up we will set up the database using the nifty Liquibase Gradle plugin. Liquibase will create
tables and add constraints.
```shell
./gradlew dropAll update -PrunList=dev
```

We are now ready to deploy a local `nuntium-server` instance.
```shell
./gradlew bootRunDev
```

## Other cool things

- Build a local Docker image with `nuntium-server` deployed.
```shell
./gradlew clean build jibDockerBuild # note to future self
```

