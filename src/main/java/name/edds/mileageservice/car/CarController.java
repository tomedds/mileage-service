package name.edds.mileageservice.car;

import name.edds.mileageservice.events.EventService;
import name.edds.mileageservice.events.FuelingDto;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Manage Car resources */

@RestController
@RequestMapping("/api/cars")
public class CarController {

    CarService carService;
    UserService userService;
    EventService eventService;

    Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    CarController(CarService carService, UserService userService, EventService eventService) {
        this.carService = carService;
        this.userService = userService;
        this.eventService = eventService;
    }

    /**
     * Update a car
     */

    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    ResponseEntity<Car> updateCar(@PathVariable("carId") String carId, @RequestBody Car car) {

        return new ResponseEntity<Car>(car, HttpStatus.NOT_IMPLEMENTED);
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
     */

    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    ResponseEntity<String> deleteCar(@PathVariable("carId") String carId) {

        return new ResponseEntity<String>("", HttpStatus.NOT_IMPLEMENTED);
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
     * Fetch a car
     *
     * @return
     */
    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    ResponseEntity<Car> getCar(@PathVariable("carId") String carId) {

        try {
            ObjectId carObjectId = new ObjectId(carId);
            Optional<Car> car = carService.find(carObjectId);

            return car.isPresent() ? new ResponseEntity<>(car.get(), HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Invalid value passed for carId:" + carId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    /**
     * Get a list of all cars
     * <p>
     * FIXME: this needs paging
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = new ArrayList<>();
        return new ResponseEntity<List<Car>>(cars, HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Add a fueling event to a car. We use the carId directly and so don't need the user id.
     *
     * @return
     */
    @RequestMapping(value = "/{carId}/fueling", method = RequestMethod.POST)
    public ResponseEntity<String> addFueling(@PathVariable("carId") String carId,
                                             @RequestBody FuelingDto fuelingDto) {

        try {
            // FIXME: for now, always add fueling to default car. This needs to be the currently selected car
            ObjectId carObjectId = new ObjectId(carId);

            // Make sure this car exists
            Optional<Car> car = carService.find(carObjectId);
            if (car.isPresent()) {
                if (eventService.saveNewFueling(carObjectId, fuelingDto)) {
                    return new ResponseEntity<>("", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("Error saving fueling.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("No car found with this ID.", HttpStatus.NOT_FOUND);
            }

        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("car id value is not a valid ObjectId", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Add a fueling event to the default car for this user.
     *
     * @return
     */
    @RequestMapping(value = "user/{userIdentifier}/defaultCar/fueling", method = RequestMethod.POST)
    public ResponseEntity<String> addFuelingToDefault(@PathVariable("carId") String carId,
                                                      @RequestBody FuelingDto fuelingDto) {
        /* not implemented yet */
        return new ResponseEntity<>("Feature not implemented", HttpStatus.METHOD_NOT_ALLOWED);

    }


}
