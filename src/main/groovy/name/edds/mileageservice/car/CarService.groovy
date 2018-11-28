package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.user.User
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@TypeChecked
@Component
class CarService {

    /**
     * Validate that we have sufficient data to create a new Car
     * Validate that we have sufficient data to create a new Car
     *
     * @param car
     * @return "" or error messages
     */
    String validateNewCar(Car car) {

        if (null == car._id) {
            return "Car is missing an ID"
        }

        if (null == car.carModel) {
            return "Car is missing model information"
        }

        if (0 >= car.mileage) {
            return "Mileage must be greater than zero"
        }

        if (null != car.events && 0 <= car.events.size()) {
            return "A new car should not have any events"
        }

        return ""

    }

    /**
     * List cars for the specified user
     * @return
     */

    List<Car> listCars(ObjectId userid, UserService userService) {


     //   List<Car> cars = new ArrayList<>()

        User user = userService.findUser(userid)
        return user.cars

    }


    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User findUser(ObjectId userObjectId) {

    }


}
