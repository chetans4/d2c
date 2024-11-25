This Payment service, used to manage all operations relegated to payment transactions.

http://localhost:9082/actuator
http://localhost:9082/actuator/circuitbreakers
http://localhost:9082/actuator/circuitbreakerevents

- Can plan email trigger from CircuitBreakerEventListener

#### Run docker with non-root user
> sudo snap services docker
> sudo groupadd docker
> sudo usermod -aG docker $(whoami)
> sudo snap restart docker
> sudo snap logs docker
> newgrp docker
> sudo chown root:docker /var/run/docker.sock
> sudo chmod 660 /var/run/docker.sock
> groups


#### Build Docker Image
> sudo docker build -t d2c-payment-service .

#### Run Docker Image
[//]: # (8082 machine port:9082 container port)
> sudo docker run -p 8082:9082 d2c-payment-service

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

### TODO:
[] Assign volume persistant memory to Grafana container, deleting container should not impact previously done configurations.
[] Setup K8S to orchestrate docker container, all 3 services (3 container - 3 pods) in one k8s node and scale as per load.
[] Assign volumes to k8s cluster.
[] Write Cloud Formation to Create VPC, Public and Private Subnets and EC2 instance.
[] Automate installing and running of K8S + Docker + D2C Microservices with Prometheus and Grafana
[] Expose endpoints to call services and view metrices. 
[] Setup backups / snapshots for data recovery.

#### Sample Grafana Dashboard

- Graphs with failed calls only:
![img.png](https://github.com/chetans4/d2c/blob/master/d2c-payment-service/src/main/resources/static/img.png)


- Graphs with success calls:
![img.png](https://github.com/chetans4/d2c/blob/master/d2c-payment-service/src/main/resources/static/img-success-calls.png)

- Graphs with CPU and Service monitoring:
![img.png](https://github.com/chetans4/d2c/blob/master/d2c-payment-service/src/main/resources/static/img-cpu.png)

#### Setting up K8S:
> minikube start --driver=docker
> minikube status
> minikube dashboard
> 
