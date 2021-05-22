# dps-multi-module-starterkit-java

Java API Starter from Template

## Getting started

Suppose we need a new service to support a new business line.
As a developer, I would like to be able to create a new service quickly, and have all of the common bits already done (for some definition of "common bits").

This is essentially what the starter kit provides: an opinionated starter skeleton which is capable of building a service and deploying it into production.

### Common Bits

At the current stage of development, the "common bits" are:

* Spring-Boot application structure
* Three-tier service architecture
  * API interface with SpringMVC and OpenAPI annotations
  * A Controller class implementing the API interface
  * A Service layer, consisting of:
    * Service Provider Interface (SPI)
    * Provider implementation
  * A Persistence layer
* Unit / Integration testing
* Architectural fitness functions (to enforce 3-tier arch)
* Flyway database migration support
* Code coverage metrics and minimum coverage thresholds
* Build execution time tracking
* Endpoint security via Open Policy Agent
* Kafka support
* Open Telemetry integration
* Gatling load / performance testing
* Model data lifecycle notifications (i.e. data mutation events)
* Documentation support, using MkDocs (deploy to Github Pages)
* OpenAPI v3.0 support for generating docs from code
* Versioning support for dependency updates
* Versioning support for building application
* Helm charts for deployment
* Container support
  * Docker container creation (app, db-init, opa-init)
* Local execution support
  * Docker-Compose definition for running service and dependencies
  * Supporting Postgres, Kafka, Jaeger, OPA, Spring-Boot app
 

## Creating a new service

Here is a walk-through of the process for creating a new service skeleton.

```bash
% export CC_PROJECT=https://github.com/thoughtworks-dps/dps-multi-module-starterkit-java # (1)
% cookiecutter "${CC_PROJECT}" \
--directory templates/project \
PROJECT_NAME=mpi-facade \
PACKAGE_NAME=mpifacade \
RESOURCE_VAR_NAME=facilityVisit \
projectDir="${CC_PROJECT}" \
--no-input
% cd $PROJECT_NAME
% gradlew clean build check docker
% gradlew :app:dockerComposeDown :app:dcPrune  :app:dockerComposeUp
```

> Note:
> 1. If testing locally, set this to the path to your local repo

> Note: Any time you are pulling artifacts from the Github packages repository, it is likely that you will need to specify your authorizations.
> Typically, we run the build behind `secrethub` to obtain the necessary credentials for Github Packages. 
> ```bash
> % secrethub run -- gradlew clean build check docker
> ```

In order to see the system working, attach a consumer to the Kafka queue.
Then run the performance test to generate traffic.

```bash
% scripts/consume-kafka.sh # <--- run this in a separate window
% gradlew :app:gatlingRun  
```

View the OpenAPI documentation for your service

```bash
% chrome http://localhost:8080/swagger-ui/index.html
 ```

View the Jaeger monitoring system

```bash
% chrome http://localhost:16686/
 ```

## MPI Facade example

## Creating a new service

Here is a walk-through of the process for creating a new service skeleton including a sub-resource structure.

```bash
% export CC_PROJECT=https://github.com/thoughtworks-dps/dps-multi-module-starterkit-java # (1)
% cookiecutter "${CC_PROJECT}" \
--directory templates/project \
PROJECT_NAME=mpi-facade \
PACKAGE_NAME=mpifacade \
--no-input
% cookiecutter "${CC_PROJECT}" \
--directory templates/resource \
PROJECT_NAME=mpi-facade \
PACKAGE_NAME=mpifacade \
RESOURCE_VAR_NAME=facilityVisit \
--no-input
% cd $PROJECT_NAME
% gradlew clean build check docker
% gradlew :app:dockerComposeDown :app:dcPrune  :app:dockerComposeUp
```

> Note:
> 1. If testing locally, set this to the path to your local repo

> Note: Any time you are pulling artifacts from the Github packages repository, it is likely that you will need to specify your authorizations.
> Typically, we run the build behind `secrethub` to obtain the necessary credentials for Github Packages.
> ```bash
> % secrethub run -- gradlew clean build check docker
> ```
