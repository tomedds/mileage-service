#!/bin/bash
set -x
USER_IDENTIFIER=thatGuy123@example.com
ENDPOINT=http://localhost:8081/api/users/$USER_IDENTIFIER/cars
#POST_DATA='{ "lastName": "Jones", "firstName": "Indiana", "email": "thatGuy123@example.com"}'
POST_DATA='{ "carModel": { "make" : "Toyota", "model": "Prius", "year" : "2019" }, "mileage": 1234, "isDefault": true }'
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA" $ENDPOINT 
