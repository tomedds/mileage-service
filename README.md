# RESTful service for a mileage tracker API

This repo contains the service component for an application that can be used to track gas mileage for your car.
It is still pre-alpha at this point, and I'll continue to provide updates as they are ready.

## Getting Started

### Prerequisites

Linux or MacOS. The project was developed and tested on MacOS. It should work on Linux. It will probably work on Windows but you'll need to convert the 
import-vehicles.sh and run.sh scripts to  Windows command files.

Java (Java 8 or later)

The application uses MongoDB for data, so you'll need to install the MongoDB server and load it with the initial set of data.
Detailed instructions can be found in the Installing section. Testing was done using Version 4.0.4.

The [Postman](https://www.getpostman.com/) tool is handy for submitting requests to the service.

Note that the current version has only been tested with everything running on localhost.

### Building

 `./gradlew clean build`
 
The application is currently configured to use port 8081. Review the application.yml file to change this or other settings such as logging.

### Installing

#### Load required data into MongoDB

Start the MongoDB server on localhost.

Use the import-vehicles.sh script to create the mileage-service database and the car_models collection.

TBD: load sample user data.

## Running the tests

Unit tests are run as part of the standard build.

Details on how to run the integration tests are not yet ready.

## Deployment

No additional deployment required in this version.

## Execution

Once you have build the application and loaded the data, you can use the `run.sh` script to start the service.

### Documentation

The project provides API details using Swagger UI. Currently this is available after the application has started
by going to 

 `http://localhost:8081/swagger-ui.html`

Work is underway to provide static Swagger documentation.

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
