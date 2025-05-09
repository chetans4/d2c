This Payment service, used to manage all operations relegated to payment transactions.

http://localhost:9082/actuator
http://localhost:9082/actuator/circuitbreakers
http://localhost:9082/actuator/circuitbreakerevents

- Trying to run on wsl without installing java, this will rely on docker only for wsl.
- In main Windows machine, we can run using gradlew.bat

#### Known Errors:
> Verify the service folder path with respect to wsl /mnt/**** directory.

Error 1:
> network my_shared_network declared as external, but could not be found

Solution 1:
> Check: docker network ls  
> Create Network beforehand: docker network create --driver bridge my_shared_network

Error 2:
> *-service-app  | /__cacert_entrypoint.sh: /app/gradlew: /bin/sh^M: bad interpreter: No such file or directory  
> *-service-app  | /__cacert_entrypoint.sh: line 114: /app/gradlew: Success  
> *-service-app exited with code 127

Solution 2:
> Run command: dos2unix gradlew
 
Error 3:
> Error response from daemon: Conflict. The container name "/prometheus" is already in use by container "fa0f5e8161bce35c223f238c2bf36375233fb54b202aa84252347dc6262c8b68". You have to remove (or rename) that container to be able to reuse that name.  

Solution 3:
> Either use docker-compose down or manually stop and remove the container  
> Using docker-compose up --force-recreate ; does not help bcz Docker Compose cannot overwrite an existing container with the same name, even with --force-recreate, if it wasn't created by the current Compose project.
> Manage a Docker container as an external service via Docker network.

#### TODO:
- Can plan email trigger from CircuitBreakerEventListener

#### Build Docker Image
> sudo docker build -t d2c-payment-service .  
> docker build -t anhartit/d2c-notification-service:v2 ../d2c-notification-service/.

#### Run Docker Image
[//]: # (8082 machine port:9082 container port)
> sudo docker run -p 8082:9082 d2c-payment-service

### Host Docker image on docker hub
> Build docker image tagged with username and repository name.
> docker push anhartit/d2c-notification-service:latest

### Run from image taking directly from Docker Hub
> docker run -it -p 8083:9083 anhartit/d2c-notification-service  
> docker run -it -p 8083:9083 anhartit/d2c-notification-service:v2@sha256:7b450201d3e07454d56bf8fa64a823bf81a7ddd71ec411a3ccf36963d564d76a  
> 

#### Run Application with Gradle Commands
> ./gradlew bootRun  
> ./gradlew bootRun --args='--spring.profiles.active=docker'

#### Start prometheus
> sudo docker-compose -f docker-compose.yml up

#### Other Docker Commands:
> sudo docker ps  
> docker ps -a  
> sudo docker images
> docker network create my_shared_network
> docker-compose -f ./docker-compose.yml -f ./d2c-payment-service/docker-compose.yml up

[//]: # (Remove container  )
> docker rm 634b6763e39d   
> docker image rm 4786e098b0a6  
> docker exec -it 9e49ffb3181f sh

#### Run with temp docker container
> docker run --rm -it -v $(pwd):/app -w /app -p 9082:9082 gradle:8.10-jdk17 ./gradlew bootRun

### Run with docker-compose
> sudo docker-compose -f docker-compose.yml up

#### Configure the Grafana.
- Open http://localhost:3000 (admin/admin)
- **Configure integration with Prometheus**
    - Access configuration
    - Add data source
    - Select Prometheus
    - Use url "http://localhost:9090" and access with value "Browser"
- **Configure dashboard**
    - Access "home"
    - Import dashboard
    - Upload dashboard.json from /docker

#### Actions this service configuration should do:
- Start Payment Service
- Start Prometheus
- Start Grafana
- Grafana dashboard should live stream the Circuit Breaker failed and passed calls counts
- Circuit Breaker events should display the error overview.

#### Sample Grafana Dashboard

- Graphs with failed calls only:
![img.png](https://github.com/chetans4/d2c/blob/f57b98046167d4aa557fd37b1aae2f2e6a9bc4be/d2c-payment-service/src/main/resources/static/img.png)


- Graphs with success calls:
![img.png](https://github.com/chetans4/d2c/blob/f57b98046167d4aa557fd37b1aae2f2e6a9bc4be/d2c-payment-service/src/main/resources/static/img-success-calls.png)