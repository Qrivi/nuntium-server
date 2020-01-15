# fapptime

- Make a clean build.
```shell script
./gradlew clean && ./gradlew build
```
- Make unpacked war available to Docker image.
```shell script
 mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar) 
``` 
- Make Docker image.
```shell script
docker build --build-arg DEPENDENCY=build/dependency -t qrivi/fappserver .
```
```shell script
./gradlew jib --image=qrivi/fappserver
```
- Gaan met de banaan.
```shell script
docker-compose up  
```