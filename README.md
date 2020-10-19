# fapptime
<a href="https://github.com/Qrivi/fappserver/actions"><img alt="CI" align="right" src="https://github.com/Qrivi/fappserver/workflows/CI/badge.svg?branch=develop"></a>

👷‍♂️ Spring Boot back-end _currently being written_ in Kotlin for an application I hope I can start developing soon.

---

### Run as in production
```shell script
docker rmi -f qrivi/fappserver # Optional, ensures latest image is fetched
docker-compose up
```
This will fetch a PostgreSQL image and the `fappserver` image from Docker Hub, and run as intended for production.

### Other cool things
```shell script
./gradlew clean build jibDockerBuild
```
This will build a local docker image with `fappserver` deployed.
