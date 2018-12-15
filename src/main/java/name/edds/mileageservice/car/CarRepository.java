package name.edds.mileageservice.car;

import com.mongodb.client.MongoCollection;
import name.edds.mileageservice.DbService;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@Repository
public class CarRepository {

    DbService dbService;
    UserRepository userRepository;

    @Autowired
    public CarRepository(DbService dbService, UserRepository userRepository) {
        this.dbService = dbService;
        this.userRepository = userRepository;
    }

    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @return id for new car
     */
    String create(User user, Car newCar) {

        MongoCollection userCollection = dbService.getUserCollection();
                /* reset the default flag if needed */

        List<Car> updatedCars = new ArrayList<>();

        user.getCars().stream().forEach(car ->
        {
            updatedCars.add(car.isDefault() ? new Car(car, false) : car);
        });

        updatedCars.add(newCar);
        User updatedUser = new User(user, updatedCars);

        userCollection.updateOne(eq("_id", updatedUser.get_id()),
                combine(set("cars", updatedUser.getCars())));

        return newCar.getId().toString();
    }

    /**
     * Find the car with the specified ID
     *
     * @return the Car matching the ID if found
     */
    Optional<Car> find(ObjectId objectId) {
        User userWithCar = userRepository.getUserCollection().find(eq("cars._id", objectId)).first();

        return userWithCar.getCars().stream().filter(
                car ->
                        car.getId().equals(objectId)
        ).findFirst();

    }

    ;

    // TODO: add methods for update, delete Car */

}
