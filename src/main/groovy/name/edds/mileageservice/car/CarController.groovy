package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car.Car
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.user.User
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/* Manage Car resources */

@TypeChecked
@RestController
@RequestMapping("/api/users")
class CarController {

    CarService carService
    UserService userService

    @Autowired
    CarController(CarService carService, UserService userService) {
        this.carService = carService
        this.userService = userService
    }

/**
 * Get all of the cars for the specified user
 * @return list of Cars
 */
    @RequestMapping(value = "/{id}/cars", method = RequestMethod.GET)
    ResponseEntity<List<Car>> getCarsForUser(@PathVariable("id") String id) {

        int debug = 0;
        return new ResponseEntity<>(
                carService.listCars(new ObjectId(id), userService),
                HttpStatus.OK)


    }
/**
 * Add a car to the user.
 * FIXME: make sure the make/model/year combination is valid
 * @return
 */
    @RequestMapping(value = "/{id}/cars", method = RequestMethod.POST)
    ResponseEntity<String> addCarToUser(@PathVariable("id") String id, @RequestBody Car newCar) {

        ObjectId userId
        User user
        String errMsg

        try {
            userId = new ObjectId(id)
        }
        catch (IllegalArgumentException ex) {
            errMsg = "_id value is not a valid ObjectId"
        }

        if (!errMsg) {
            // confirm that user exists
            user = userService.findUser(userId)

            if (null == user) {
                return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
            }
        }

        /* add car and make it the default if needed */
        if (!errMsg) {
            errMsg = userService.addCarToUser(userId, newCar);
        }

        if (!errMsg) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);

    }


}
