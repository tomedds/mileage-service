#!/bin/bash
set -x
# Before running this script, you need to determine the ID for the car you are fueling.
# The Compass tool from Mongo makes this easy.


CAR_ID=5c16f19a02ff471ca31f9219
ENDPOINT=http://localhost:8081/api/cars/$CAR_ID/fueling
POST_DATA='{ "pricePerGallon": "2.69", "numGallons": "3.45", "odometer": "12234", "totalCost": "6.50" }'
curl -X POST --header "Content-Type: application/json"  --data "$POST_DATA" $ENDPOINT 
