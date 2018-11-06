# Mileage Tracker API

Provides a RESTful API that will be part of an application for tracking gas mileage.

NOTE: this application is in the very early stages of development.


## Getting Started

### Prerequisites

MongoDB server


### Building

 `./gradlew build`

### Installing

TBD 


## Running the tests

Tests are run as part of the standard build.

## Deployment

TBD

## Execution

You may need to run LoadData to reset the collection and loads the car data into the database.

Run the main application using the JAR built by Spring Boot.

### REST API

 `GET http://localhost:8080/api/car_models` - fetch the list of car models 


 `GET http://localhost:8080/api/users` - fetch the list of users

 `POST http://localhost:8080/api/users` - create a user


## Built With

* [Spring Initializr](https://start.spring.io/) - Good way to start Spring projects
* [GroovyCVSV](https://github.com/xlson/groovycsv/) - A simple CSV parsing library for groovy
* [MongoDB](https://www.mongodb.com/) - A popular NoSQL database

## Contributing

TBD

## Versioning

TBD

## Authors

* **Tom Edds** - *Initial work* 

## License

TBD

## Acknowledgements

Car model data comes from https://www.fueleconomy.gov/feg/ws/index.shtml


[//]: # (This file based on a temlpate from https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
