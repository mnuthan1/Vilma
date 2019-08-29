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

**NOTE:** Uploaded files are stored under \<repo_base_dir\>/filestore/**store**

## Features

* File upload and download
* File versioning capabilities
* File names are hashed with unique string
