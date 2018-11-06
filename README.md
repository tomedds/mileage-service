# Mileage Tracker API

Provides a RESTful API that will be part of an application for tracking gas mileage.

NOTE: this application is in the very early stages of development.


## Getting Started

### Prerequisites

MongoDB server


### Installing

TBD 


## Running the tests

TBD


## Deployment

TBD

## Execution

When you start the application, it resets the collection, loads the car data, and waits for requests to the REST service.

Currently there is one query supported:

 `GET http://localhost:8080/api/cars`

will fetch a list of car with make, model, and year.

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

TBD


[//]: # (This file based on a temlpate from https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
