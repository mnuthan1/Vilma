# File Store

## Pre requisites

* Java 8 or above
* gradle

## How to Run locally

* Download git repository

```bash
git clone https://github.com/mnuthan1/vilma.git
```

* from `filestore` folder
  * to run unit tests

    ```bash
    gradle test
    ```

  * start filestore server

    ```bash
    gradle bootRun
    ```

## Run locally using docker

* generate docker image

  ```bash
    gradle buildDocker
  ```

  creates docker image with name `com.vilma/fileserver` and with tag as `<application revision>`

* run docker image with port mapping

  ```bash
  docker run -p 9090:9090 -v <file_store_location>:/store com.vilma/fileserver:0.0.1-SNAPSHOT
  ```

  * file_store_location - your local storage for file store path

## testing with swagger UI

* browser to `http://localhost:9090/swagger-ui.html` and play with available APIs

**NOTE:** Uploaded files are stored under \<repo_base_dir\>/filestore/**store**

## Features

* uploaded files are stored in hashed buckets
* files are renamed with uniq ID on disk for security reasons
