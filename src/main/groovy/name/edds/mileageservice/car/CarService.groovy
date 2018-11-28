package name.edds.mileageservice.car

import com.mongodb.client.MongoCollection
import groovy.transform.TypeChecked
import name.edds.mileageservice.user.User
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Updates.combine
import static com.mongodb.client.model.Updates.set

@TypeChecked
@Component
class CarService {

    UserService userService

    final static String ID_KEY = "_id";

    @Autowired
    CarService(UserService userService) {
        this.userService = userService
    }

/**
     * Validate that we have sufficient data to create a new Car
     *
     * @param car
     * @return "" or error messages
     */
    String validateNewCar(Car car) {

        if (null == car.id) {
            return "Car is missing an ID"
        }

        if (null == car.carModel) {
            return "Car is missing model information"
        }

        if (0 >= car.mileage) {
            return "Mileage must be greater than zero"
        }

        if (null != car.events && 0 < car.events.size()) {
            return "A new car should not have any events"
        }

        return ""

    }

    /**
     * List cars for the specified user
     * @return
     */

    List<Car> listCars(ObjectId userid) {

     //   List<Car> cars = new ArrayList<>()

        User user = userService.findUser(userid)
        return user.cars

    }


    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @param userId
     * @param newCar
     * @return
     */
    String addCarToUser(ObjectId userId, Car newCar) {

        /* Validate parameters */

        newCar.id = new ObjectId()
        String message = this.validateNewCar(newCar)

        if (message) {
            return message
        }

        User user = userService.findUser(userId)

        if (null == user) {
            return "user not found"
        }

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

        MongoCollection<User> userCollection = userService.setupUserCollection()

        userCollection.updateOne(eq(ID_KEY, userId),
                combine(set("cars", user.cars)))

        return newCar.id.toString()
    }

    /**
     * Find the default car for this user
     * @param user
     * @return
     */
    Car findDefaultCar(User user) {

        user.cars.each {
            thisCar ->
                if (thisCar.isDefault) {
                   return thisCar
                }
        }

       null  // if no default car found (e.g., new user)

    }


    String addFueling(User user, Car car, Fueling fueling) {

        car.addEvent(fueling)

        MongoCollection<User> userCollection = userService.setupUserCollection()

        /* db.students.updateOne(
   { id: 1, grades: 80 },
   { $set: { "grades.$" : 82 } }
)
*/

/*        userCollection.updateOne( {id: user.id, cars:  },
                combine(set("cars", user.cars)))
 */
        return ""

    }




}
