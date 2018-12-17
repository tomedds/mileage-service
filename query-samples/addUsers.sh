#!/bin/bash
# load several users
ENDPOINT=http://localhost:8081/api/users
POST_DATA1='{ "lastName": "Banana", "firstName": "Anna", "email": "fruity2373@example.com"}'
POST_DATA2='{ "lastName": "Lanfranconi", "firstName": "Shandra", "email": "sf2@example.com"}'
POST_DATA3='{ "lastName": "Woodwing", "firstName": "Milly", "email": "memilly@example.com"}'
set -x
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA1" $ENDPOINT 
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA2" $ENDPOINT 
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA3" $ENDPOINT 
