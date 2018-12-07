package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.events.EventService
import name.edds.mileageservice.events.Fueling
import name.edds.mileageservice.user.User
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/* Manage Car resources */

@RestController
@TypeChecked
@RequestMapping("/api/cars")
class CarController {

    CarService carService
    UserService userService
    EventService eventService

    @Autowired
    CarController(CarService carService, UserService userService, EventService eventService) {
        this.carService = carService
        this.userService = userService
        this.eventService = eventService
    }

    /**
     * Update a car
     *
     */

    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    ResponseEntity<Car> updateCar(@PathVariable("carId") String carId, @RequestBody Car car) {

        return new ResponseEntity<Car>(car, HttpStatus.NOT_IMPLEMENTED)
        /*
        String errMsg = carService.updateCar(carId)

        if (errMSg) {
            return new ResponseEntity<Car>(car, HttpStatus.OK)
        } else {
            return new ResponseEntity<Car>(errMsg, HttpStatus.BAD_REQUEST)
        }
        */
    }

    /**
     * Delete a car
     *
     */

    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    ResponseEntity<String> deleteCar(@PathVariable("carId") String carId) {

        return new ResponseEntity<String>("", HttpStatus.NOT_IMPLEMENTED)
        /*
        String errMsg = carService.delete(carId)

        if (errMSg) {
            return new ResponseEntity<Car>(car, HttpStatus.OK)
        } else {
            return new ResponseEntity<Car>(errMsg, HttpStatus.BAD_REQUEST)
        }
        */
    }

    /**
     * Update a car
     *
     * @return
     */
    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    ResponseEntity<Car> getCar(@PathVariable("carId") String carId) {
        Car car = carService.find(carId)
        if (car) {
            return new ResponseEntity<Car>(car, HttpStatus.OK)
        } else {
            return new ResponseEntity<Car>(new Car(), HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Get a list of all cars
     *
     * FIXME: this needs paging
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = new ArrayList<>()
        return new ResponseEntity<List<Car>>(cars, HttpStatus.NOT_IMPLEMENTED)
    }

    /**
     * Add a fueling event to a car
     *
     * @return
     */
    @RequestMapping(value = "/{carId}/fueling", method = RequestMethod.POST)
    ResponseEntity<String> addFueling(@PathVariable("userId") String userId,
                                      @PathVariable("carId") String carId,
                                      @RequestBody Fueling fueling) {
        String errMsg

        try {
            // The userId passed as part of the REST URL needs to be valid but we don't actually use it
            ObjectId userObjectId = new ObjectId(userId)

            try {
                // FIXME: for now, always add fueling to default car. This needs to be the currently selected car
                errMsg = eventService.addFueling(userService.getUserCollection(), new ObjectId(carId), fueling)

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
