package org.edds.tools

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoClient

import static com.xlson.groovycsv.CsvParser.parseCsv

class LoadCarData {

    public static String MILEAGE_DB = "mileage-service"
    public static String CARS_COLLECTION = "cars"

    static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mileageDb = mongoClient.getDatabase(MILEAGE_DB)
        MongoCollection<Document> carCollection = mileageDb.getCollection(CARS_COLLECTION)

        // drop any existing collection
        carCollection.drop()

        // and recreate it
        carCollection = mileageDb.getCollection("cars");

        String csvFilePath = "/Users/tom/dev/mileage/mileage-service/data/vehicles-simpler.csv"
        // String csvFilePath = "/Users/tom/dev/mileage/mileage-service/data/test.csv"

        def csvFile = new File(csvFilePath)
        def csvContent = csvFile.getText('utf-8')
        def carData = parseCsv(csvContent)

        for (car in carData) {

            Document carDoc = new Document("make", car.make)
                    .append("model", car.model)
                    .append("year", car.year)

            carCollection.insertOne(carDoc);

        }

        println "loaded data for ${carData.size()} cars."

    }
}
