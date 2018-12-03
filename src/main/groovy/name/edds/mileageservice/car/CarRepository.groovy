package name.edds.mileageservice.car

import com.mongodb.client.MongoCollection
import groovy.transform.TypeChecked
import name.edds.mileageservice.user.User
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Updates.combine
import static com.mongodb.client.model.Updates.set

@TypeChecked
@Repository
class CarRepository {


    final static String ID_KEY = "_id";

    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @param userId
     * @param newCar
     * @return
     */
    String createCar(MongoCollection userCollection, User user, Car newCar) {

        /* Proceed with adding this car to this user */
        if (null == user.cars) {
            user.cars = new ArrayList<Car>()
        }

        /* reset the default flag if needed */

        user.cars.each {
            thisCar ->
                if (thisCar.isDefault) {
                    thisCar.isDefault = false
                }
        }
        newCar.dateAdded = new Date()
        user.cars.add(newCar)

        userCollection.updateOne(eq(ID_KEY, user.getId()),
                combine(set("cars", user.cars)))

        return newCar.id.toString()
    }

    // TODO: add methods for update, delete Car */

}
