package name.edds.mileageservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Create an instance of a Mongo client or return one if it is already created
 */
public class Client {

    private static MongoClient mongoClient;

    private static final String dbHost = "localhost";

    public static synchronized MongoClient getInstance() {
        if (mongoClient == null) {
           mongoClient = MongoClients.create();
        }
        return mongoClient;
    }
}