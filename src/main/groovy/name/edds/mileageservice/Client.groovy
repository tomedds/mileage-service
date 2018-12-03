package name.edds.mileageservice

import com.mongodb.MongoClientOptions
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

/**
 * Create an instance of a Mongo client or return one if it is already created
 */
class Client {

    private static MongoClient mongoClient

    static String dbHost = "localhost"

    private Client() {}

    static synchronized MongoClient getInstance() {
        if (mongoClient == null) {
           mongoClient = MongoClients.create()
        }
        return mongoClient
    }
}