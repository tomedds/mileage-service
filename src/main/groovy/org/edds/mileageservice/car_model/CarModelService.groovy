package org.edds.mileageservice.car_model

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

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
        MongoCollection<Document> carCollection = mileageDb.getCollection(carCollectionName)

        MongoCursor<Document> cursor = carCollection.find().iterator()

        List<CarModel> cars = new ArrayList<>()

        try {
            while (cursor.hasNext()) {

                def carDocument = cursor.next()

                CarModel car = new CarModel(make: carDocument.make,
                model: carDocument.model,
                year: carDocument.year)

                cars.add(car)
            }
        } finally {
            cursor.close();
        }

        return cars
    }


}
