package name.edds.mileageservice.car

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import groovy.transform.TypeChecked
import name.edds.mileageservice.events.EventType
import name.edds.mileageservice.events.Fueling
import name.edds.mileageservice.user.User
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@TypeChecked
@Service
class CarService {

    UserService userService
    CarRepository carRepository

    @Autowired
    CarService(UserService userService, CarRepository carRepository) {
        this.userService = userService
        this.carRepository = carRepository
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
        return userService.findUser(userid).cars
    }

    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @param userId
     * @param newCar
     * @return
     */
    String addCar(ObjectId userId, Car newCar) {

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

        carRepository.createCar(userService.getUserCollection(), user, newCar)
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




}
