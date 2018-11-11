package name.edds.mileageservice.car_model

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import groovy.transform.TypeChecked
import org.bson.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@TypeChecked
@Component
class CarModelService {


    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.car_model_collection_name}')
    String carCollectionName

    /**
     * Get a list of all cars in the DB
     * TODO: add paging
     *
     * @return list of cars
     */
    List<CarModel> listCars() {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName)
        MongoCollection<CarModel> carCollection = mileageDb.getCollection(carCollectionName, CarModel.class)

        MongoCursor<CarModel> cursor = carCollection.find().iterator()

        List<CarModel> carModels = new ArrayList<>()

        try {
            while (cursor.hasNext()) {
                 carModels.add(cursor.next())
            }
        } finally {
            cursor.close();
        }

        return carModels
    }


}
