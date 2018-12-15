package name.edds.mileageservice.user;

import groovy.transform.TypeChecked;
import io.swagger.annotations.ApiOperation;
import name.edds.mileageservice.Formatter;
import name.edds.mileageservice.car.Car;
import name.edds.mileageservice.car.CarDto;
import name.edds.mileageservice.car.CarService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// FIXME: allow lookup for user by either ID or email without specifying type.
// If it is a value email, use it that way.
// Otherwise, assume it's an ID

@RestController
@RequestMapping("/api/users")
public final class UserController {

    UserService userService;
    CarService carService;

    @Autowired
    UserController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    /**
     * Get the list of users
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(
                userService.findUsers(),
                HttpStatus.OK);
    }

    /**
     * Get all of the cars for the specified user
     *
     * @return list of Cars
     */
    @RequestMapping(value = "/{identifier}/cars", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> getCarsForUser(@PathVariable("identifier") String identifier) {
        return new ResponseEntity<>(carService.listCars(identifier), HttpStatus.OK);
    }


    /**
     * Get the current user
     * FIXME: this will be different when we use authentication
     *
     * @return
     */
    @ApiOperation(value = "Get current user", notes = "")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser() {

        Optional<User> user = userService.getCurrentUser();

        if (user.isPresent()) {
            return new ResponseEntity<>(
                    user.get(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Get a single user using either the ID or email
     *
     * @return
     */
    @RequestMapping(value = "/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("identifier") String identifier) {

        Optional<User> user = Optional.empty();

        // sniff the identify to figure out which find() to use
        if (userService.isValidEmailAddress(identifier)) {
            user = userService.findUser(identifier);
        } else {
            try {
                user = userService.findUser(new ObjectId(identifier));
            } catch (IllegalArgumentException ex) {
                System.err.println("invalid identifier");
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Create a new user
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody User user) {

        try {
            Optional<ObjectId> result = userService.createUser(user);
            return result.isPresent() ? new ResponseEntity<>(String.valueOf(result.get()), HttpStatus.CREATED) :
                    new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (InvalidUserException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Add a car to the user.
     * FIXME: make sure the make/model/year combination is valid
     *
     * @return
     */
    @RequestMapping(value = "/{identifier}/cars", method = RequestMethod.POST)
    public ResponseEntity<String> addCarToUser(@PathVariable("userId") String userId, @RequestBody CarDto newCarDto) {

        try {
            return carService.addNewCar(new ObjectId(userId), newCarDto);
        } catch (IllegalArgumentException ex) {
            // If the identifier passed is not valid, we didn't find a user
            return new ResponseEntity<>(Formatter.formatErrorAsJson("Invalid user identifier"), HttpStatus.BAD_REQUEST);
        }

    }

}
