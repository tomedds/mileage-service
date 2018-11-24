package name.edds.tools

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import groovy.json.JsonSlurper
import org.bson.Document

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import static com.xlson.groovycsv.CsvParser.parseCsv

@SpringBootApplication
class LoadData implements CommandLineRunner {

    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.car_model_collection_name}')
    String carModelCollectionName

    @Value('${mileage.mongo.user_collection_name}')
    String userCollectionName

    @Value('${mileage.car_model_data_path}')
    String carModelCsvPath

    @Value('${mileage.user_data_path}')
    String userJsonPath

    MongoDatabase mileageDb

    /**
     * Load data into the database
     * @param args
     */
    static void main(String[] args) {
        SpringApplication app = new SpringApplication(LoadData.class);
        app.run()
    }

    void run(String... args) throws Exception {
        MongoClient mongoClient = MongoClients.create();
        mileageDb = mongoClient.getDatabase(dbName)
        loadUsers()
        loadCarModels()
        assignCars()
    }

    /**
     * Load car_model details
     * @param db
     */
    void loadCarModels() {

        // drop any existing collection and recreate it
        MongoCollection<Document> carModelCollection = mileageDb.getCollection(carModelCollectionName)
        carModelCollection.drop()
        carModelCollection = mileageDb.getCollection(carModelCollectionName);

        // read in the CSV
        def csvFile = new File(carModelCsvPath)
        def carModelData = parseCsv(csvFile.getText('utf-8'))

        // load it into the DB
        int numCars = 0

        for (carModel in carModelData) {

            carModelCollection.insertOne(new Document("make", carModel.make)
                    .append("model", carModel.model)
                    .append("year", carModel.year));
            numCars++
        }

        println "loaded data for ${numCars} car models."
    }

    /**
     * Load user details
     *
     * TODO: convert this to use REST API
     * @param db
     */

    void loadUsers() {

        MongoCollection<Document> userCollection = mileageDb.getCollection(userCollectionName)
        userCollection.drop()
        userCollection = mileageDb.getCollection(userCollectionName);

        // read in the JSON
        def userFile = new File(userJsonPath)
        def userText = userFile.getText('utf-8')
        def jsonSlurper = new JsonSlurper()
        def userList = jsonSlurper.parseText(userText)

        int numUsers = 0

        for (user in userList) {

            userCollection.insertOne(new Document("lastName", user.lastName)
                    .append("firstName", user.firstName)
                    .append("email", user.email))
            numUsers++
        }

        println "loaded data for ${numUsers} users."
    }

    /**
     * Use the REST APILoad user details
     * @param db
     */

    void assignCars() {

    }
}
