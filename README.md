# Spring boot Mircoservices

Purpose of this repo is to demonstrate Microservice architecture with **Spring boot** using **Spring Cloud API gateway**

This repo includes following Spring boot projects

- Eureka Discovery service `(sub-folder:`_`discover`_`)`
- Spring Cloud API Gateway `(sub-folder:`_`gateway`_`)`
- File-Store spring boot application `(sub-folder:`_`filestore`_`)`

## Startup Eureka Discovery Service

In a microservice architecture, service discovery is one of the key tenets. Service discovery allows services to find and communicate with each other with out hard coding hostname/port.

- Starting Discovery Service

```bash
    cd discover
    gradle bootRun
```

- Access using browser

```html
    http://localhost:8888
```

## Startup API Gateway

Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs. When it receives request, Spring Cloud Gateway forwards it to a Gateway Handler Mapping, which determines what should be done with requests matching a specific route.

Gateway routes are defined in application.yml and the current examples has routes for file-store api

```yml
server:
  port: 9999
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8888/eureka
spring:
  application:
    name: api-gateway
  redis:
    host: localhost
    port: 6379
  cloud:
    gateway:
      routes:
      - id: file-store
        uri: lb://file-store
        predicates:
        - Path=/api/file/**,/api/files/**
        filters:
        - name: RequestRateLimiter
          args:
            key-resolver: '#{@userKeyResolver}'
            redis-rate-limiter.replenishRate: 2
            redis-rate-limiter.burstCapacity: 2
        - RewritePath=/api/file/(?<segment>.*),/api/v1/file/$\{segment}
```

- Starting Discovery Service

```bash
    cd gateway
    gradle bootRun
```

- Access using browser

```html
    http://localhost:9999
```

## Startup File-Store micro service

Simple File-store service with file upload, download and versioning capabilities for demo purpose

- Starting file-store Service

```bash
    cd filestore
    gradle bootRun
```

- Access using browser

```html
    http://localhost:9090
```

## With Kubernetes

- Prerequisites
  - Minikube
  - Kubectl
  - ambassdor add-on
  ```bash
  kubectl apply -f https://getambassador.io/yaml/ambassador/ambassador-rbac.yaml
  ```
- config Minikube to use local dockage images
  - setup env variables
  ```bash
  eval $(minikube docker-env)
  ```
  - Build the image with the Docker daemon of Minikube
  ```bash
  cd filestore
  docker build -t com.vilma/fileserver:0.0.1-SNAPSHOT .
  ```
  - Start ambassodor api gate way
  ```bash
  kubectl apply -f  k8s_ambassador_svc.yml
  ```

  - Create k8s Pod
  ```bash
  kubectl create -f k8s_file_store_deploy.yml

  ```
  - ~~Set the imagePullPolicy to Never, to avoid pulling images~~
  <pre><s> kubectl run spring-microservices-pod --image=com.vilma/fileserver:0.0.1-SNAPSHOT --image-pull-policy=Never </s></pre>
  - Test your app
    - Get Service url
    ```bash
    minikube service hello-minikube --url
    ```
    - Copy URL in to browser for testing

