# fapptime (WIP)

### Run in production
```shell script
docker rmi -f qrivi/fappserver # Optional, but makes sure latest image is fetched
docker-compose up
```
This will fetch a MongoDB image and the `fappserver` image from Docker Hub, and run as intended for production.

### Run for development
```shell script
docker-compose run --service-ports fappstore
./gradlew clean bootRun
```
This will fetch a MongoDB image from Docker Hub and deploy `fappserver` locally as a Spring Boot application.

### Other cool things
```shell script
./gradlew clean build jibDockerBuild
```
This will build a local docker image with `fappserver` deployed.