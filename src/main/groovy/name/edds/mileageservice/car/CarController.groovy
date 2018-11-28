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
                carService.listCars(new ObjectId(id)), HttpStatus.OK)


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
    @RequestMapping(value = "/{userId}/cars/fueling}", method = RequestMethod.POST)
    ResponseEntity<String> addFueling(@PathVariable("userId") String userId, @RequestBody Fueling fueling) {

        ObjectId userObjectId
        String errMsg

        try {
            userObjectId = new ObjectId(userId)
        }
        catch (IllegalArgumentException ex) {
            errMsg = "id value is not a valid ObjectId"
        }


        if (!errMsg) {

            // get the user document
            User user = userService.findUser(userObjectId)

            Car defaultCar = carService.findDefaultCar(user)

            // what is the Mongo way to add an item to a list which is a nested document?
            // with the codec, do we add it to the USer and then save it?
            if (null == defaultCar) {
                return new ResponseEntity<String>("Default car not found", HttpStatus.NOT_FOUND);
            }

            // FIXME: for now, always add fueling to default car. This needs to be the currently selected car
            errMsg = carService.addFueling(user, defaultCar, fueling)

        }

        if (!errMsg) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);

    }


}
