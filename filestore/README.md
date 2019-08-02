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

* test using swagger UI
  * browser to `http://localhost:9090/swagger-ui.html` and play with available APIs

**NOTE:** Uploaded files are stored under \<repobase-dir\>/filestore/**store**
