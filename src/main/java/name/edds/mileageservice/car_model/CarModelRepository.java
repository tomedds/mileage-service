package name.edds.mileageservice.car_model;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Repository for CarModels. Currently we have only queries (no create, update, delete)
 * because this collection is loaded once with a MongoDB utility script.
 *
 */
@Repository
public class CarModelRepository {

    @Value("${mileage.mongo.db_name}")
    String dbName;

    @Value("${mileage.mongo.car_model_collection_name}")
    String carCollectionName;

    /**
     * Get a list of all car models in the DB
     * TODO: add paging
     *
     * @return list of car models
     */
   public List<CarModel> findCarModels() {

        MongoClient mongoClient = MongoClients.create();

        // create codec registry for POJOs
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);

        MongoCollection<CarModel> carCollection = mileageDb.getCollection(carCollectionName, CarModel.class);

        MongoCursor<CarModel> cursor = carCollection.find().iterator();

        List<CarModel> carModels = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                carModels.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return carModels;
    }

    /* TODO: add queries with parameters, e.g., Make, Make/Model, Year, etc */



}
