# Core Service

## Pre requisites

* Java 8 or above
* gradle

## How to Run locally

* Download git repository

```bash
git clone https://github.com/mnuthan1/vilma.git
```

* from `core` folder
  * to run unit tests

    ```bash
    gradle test
    ```

  * start core server

    ```bash
    gradle bootRun
    ```

## Run locally using docker

* generate docker image

  ```bash
    gradle buildDocker
  ```

  creates docker image with name `com.vilma/core` and with tag as `<application revision>`

* run docker image with port mapping

  ```bash
  docker run -p 9090:9090  com.vilma/core:0.0.1-SNAPSHOT
  ```

## testing with swagger UI

* browser to `http://localhost:9090/swagger-ui.html` and play with available APIs


docker rm -f dev-postgres
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres:11
docker exec -it dev-postgres  bash

psql -U postgres -c"CREATE DATABASE core" postgres


