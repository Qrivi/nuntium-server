# fapptime
<a href="https://github.com/Qrivi/fappserver/actions"><img alt="CI" align="right" src="https://github.com/Qrivi/fappserver/workflows/CI/badge.svg?branch=develop"></a>

---

⚠️ The README below is out of date as I am refactoring away from MongoDB in favor of PostgreSQL.

---

### Run in production
```shell script
docker rmi -f qrivi/fappserver # Optional, but makes sure latest image is fetched
docker-compose up
```
This will fetch a MongoDB image and the `fappserver` image from Docker Hub, and run as intended for production.

Spring Boot back-end written in Kotlin for an application I hope I can start developing soon.

### Other cool things
```shell script
./gradlew clean build jibDockerBuild
```
This will build a local docker image with `fappserver` deployed.

