package name.edds.mileageservice;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Create an instance of a Mongo client or return one if it is already created
 */
public class Client {

    private static MongoClient mongoClient;

    private static final String dbHost = "localhost";

    public static synchronized MongoClient getInstance() {
        if (mongoClient == null) {

            CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(pojoCodecRegistry)
                    .build();


            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }
}