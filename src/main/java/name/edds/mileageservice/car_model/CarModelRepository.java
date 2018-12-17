package name.edds.mileageservice.car_model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import name.edds.mileageservice.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for CarModels. Currently we have only queries (no create, update, delete)
 * because this collection is loaded once with a MongoDB utility script.
 *
 */
@Repository
public class CarModelRepository {

    @Autowired
    DbService dbService;

    /**
     * Get a list of all car models in the DB
     * FIXME: add paging using start and count parameters
     *
     * @return list of car models
     */
   public List<CarModel> findCarModels() {

        MongoCollection<CarModel> carModelCollection = dbService.getCarModelCollection();

        MongoCursor<CarModel> cursor = carModelCollection.find().iterator();

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
