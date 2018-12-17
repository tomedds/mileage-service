#!/bin/bash
DB_NAME=mileage-service
COLLECTION_NAME=car_models
#FILE_TO_IMPORT=./data/vehicles.csv
FILE_TO_IMPORT=./data/vehicles-simpler.csv
set -x
mongoimport --verbose --db $DB_NAME --collection $COLLECTION_NAME --file $FILE_TO_IMPORT --type csv --headerline
