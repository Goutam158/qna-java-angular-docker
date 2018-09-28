# QnA Project

This is a project created for demo purpose.

## What it does

There are Topics. Topics can have multiple questions and questions can have multiple comments. You can remove and add all Topic, Question and Comments

## Technology Stack

1. Angular --> Frontend GUI
2. Angular Material --> Look and Feel
3. Spring Boot --> Backend Java layer
4. Spring Data --> Database communication of backend (JPA)
5. Junit --> java Unit testing
6. Karma amd Jasmin --> Angular unit testing
7. Protactor --> angular end 2 end (e2e) testing
8. Swagger UI --> REST documantation and Testing
9. JWT --> Token generation for authentication
10. MySql --> Database
11. Docker --> Container
12. Docker Compose --> Multiple Container management


# How to run

## with Docker-Compose

If Docker and docker-compose is installed in you system then simple run
`docker-compose up`
This would download all the images and run 2 applications

1. [Backend] http://localhost:8080
2. [Frontend] http://localhost:4200

note : 3360 for MySql, 8080 for Backend, 4200 Angular is required to be free.

## without docker

maven, nodejs, angular-cli, mysql database must be installed.

1. create a schema `create schema qnadb`
2. create a user `app_root` who should have all the access in the `qnadb` schema
3. execute `env.sh` or `env.bat`, unix/windows respectively to set the environment variables
4. run `ng serve`
5. navigate to Server subdirectory `cd Server`
6. run `mvn spring-boot:run`

now check

1. [Backend] http://localhost:8080
2. [Frontend] http://localhost:4200

note : 3360 for MySql, 8080 for Backend, 4200 Angular is required to be free.

## Running java unit tests
Run `mvn test` to execute the unit tests Junit

## Running angular unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running angular end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
