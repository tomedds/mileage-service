#!/bin/bash
set -x
ENDPOINT=http://localhost:8081/api/users
POST_DATA='{ "lastName": "Jones", "firstName": "Indiana", "email": "thatGuy124@example.com"}'
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA" $ENDPOINT 
