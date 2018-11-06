package org.edds.mileageservice

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import static com.xlson.groovycsv.CsvParser.parseCsv

@SpringBootApplication
class LoadCarData implements CommandLineRunner {

    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.car_collection_name}')
    String carCollectionName

    static void main(String[] args) {
        SpringApplication app = new SpringApplication(LoadCarData.class);
        app.run()
    }

    void run(String... args) throws Exception {


        println "Loading collection in DB=${dbName}"

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName)
        MongoCollection<Document> carCollection = mileageDb.getCollection(carCollectionName)

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
