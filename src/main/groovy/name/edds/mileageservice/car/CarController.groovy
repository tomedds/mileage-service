package name.edds.mileageservice.car


import groovy.transform.TypeChecked
import name.edds.mileageservice.events.Fueling
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
    @RequestMapping(value = "/{userId}/cars", method = RequestMethod.GET)
    ResponseEntity<List<Car>> getCarsForUser(@PathVariable("userId") String userId) {
        new ResponseEntity<>(carService.listCars(new ObjectId(userId)), HttpStatus.OK)
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
        String resultStr

        try {
            userId = new ObjectId(id)
        }
        catch (IllegalArgumentException ex) {
            resultStr = "id value is not a valid ObjectId"
        }

        if (!resultStr) {
            // confirm that user exists
            user = userService.findUser(userId)

            if (null == user) {
                return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
            }
        }

        /* add car and make it the default if needed */
        // FIXME: currently the most recently added car is the default

        if (!resultStr) {
            resultStr = carService.addCarToUser(userId, newCar);
        }

        // at this point resultStr is either the id for the new car or an error message

        if (ObjectId.isValid(resultStr)) {
            return new ResponseEntity<String>("{\"id\": \"$resultStr\"}".toString(), HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(resultStr, HttpStatus.BAD_REQUEST);

    }

    /**
     * Add a fueling event to a car
     *
     * @return
     */
    @RequestMapping(value = "/{userId}/cars/{carId}/fueling", method = RequestMethod.POST)
    ResponseEntity<String> addFueling(@PathVariable("userId") String userId,
                                      @PathVariable("carId") String carId,
                                      @RequestBody Fueling fueling) {
        String errMsg

        try {
            // The userId passed as part of the REST URL needs to be valid but we don't actually use it
            ObjectId userObjectId = new ObjectId(userId)

            try {
                // FIXME: for now, always add fueling to default car. This needs to be the currently selected car
                errMsg = carService.addFueling(userService.getUserCollection(), new ObjectId(carId), fueling)

                if (errMsg.isEmpty()) {
                    return new ResponseEntity<String>("", HttpStatus.CREATED);
                }
            }
            catch (IllegalArgumentException ex) {
                errMsg = "car id value is not a valid ObjectId"
            }
        }
        catch (IllegalArgumentException ex) {
            errMsg = "user id value is not a valid ObjectId"
        }

        return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);

    }


}
