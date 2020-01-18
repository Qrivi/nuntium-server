# fapptime

### Run in production
```shell script
docker-compose up && echo "👌"
```
This will fetch a MongoDB image and the fappserver image from Docker Hub, and run the app as if it ran in a production environment.

### Run for development
```shell script
docker-compose run --service-ports fappstore
./gradlew clean bootRun
```
This will fetch a MongoDB image from Docker Hub and deploy fappserver locally as a Spring Boot application.

### Other cool things
```shell script
./gradlew clean build jibDockerBuild
```
This will build a local docker image with fappserver deployed.