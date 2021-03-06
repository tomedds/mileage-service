package name.edds.mileageservice.user;

import io.swagger.annotations.ApiOperation;
import name.edds.mileageservice.Formatter;
import name.edds.mileageservice.car.Car;
import name.edds.mileageservice.car.CarDto;
import name.edds.mileageservice.car.CarService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public final class UserController {

    UserService userService;
    CarService carService;


    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
    public ResponseEntity<List<User>> findUsers() {

        LOGGER.debug("finding all users");

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
    public ResponseEntity<List<Car>> findCarsForUser(@PathVariable("identifier") String identifier) {
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
    public ResponseEntity<User> findUser(@PathVariable("identifier") String identifier) {

        Optional<User> user = Optional.empty();

        // sniff the identifier to figure out which find() to use

        if (userService.isValidEmailAddress(identifier)) {
            LOGGER.debug("identifier [" + identifier + "] is a valid email address.");
            user = userService.findUser(identifier);
        } else {
            LOGGER.debug("identifier [" + identifier + "] is NOT valid email address.");
            try {
                user = userService.findUser(new ObjectId(identifier));
            } catch (IllegalArgumentException ex) {
                System.err.println("invalid identifier");
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (user.isPresent()) {
            LOGGER.debug("found user with identifier=[" + identifier + "].");
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            LOGGER.debug("found NO user with identifier=[" + identifier + "].");
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

        // make sure now user exists with the same email address

        Optional<User> existingUser = userService.findUser(user.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("A user already exists with email=" + user.getEmail(), HttpStatus.CONFLICT);
        }

        try {
            return userService.createUser(user);
        } catch (InvalidUserException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Add a car to the user.
     * FIXME: make sure the make/model/year combination is valid
     *
     * @return
     */
    @RequestMapping(value = "/{userIdentifier}/cars", method = RequestMethod.POST)
    public ResponseEntity<String> addCarToUser(@PathVariable("userIdentifier") String userIdentifier, @RequestBody CarDto newCarDto) {

        LOGGER.debug("adding car for user=[" + userIdentifier + "].");

        // if the identifier is an email address, get objectId for that user

        Optional<ObjectId> optIdForUpdate = Optional.empty();

        // TODO: see if we can improve on the if/then/else structure using Java 8 constructs
        if (userService.isValidEmailAddress(userIdentifier)) {

            Optional<User> optUser = userService.findUser(userIdentifier);
            if (optUser.isPresent()) {
                optIdForUpdate = Optional.of(optUser.get().getId());
                if (null == optIdForUpdate) {
                    throw new IllegalStateException("is for user " + userIdentifier + "is null");
                }
            } else {
                return new ResponseEntity<>(Formatter.formatErrorAsJson("No user found with for " + userIdentifier), HttpStatus.NOT_FOUND);
            }
        }

        if (!optIdForUpdate.isPresent()) {
            try {
                optIdForUpdate = Optional.of(new ObjectId(userIdentifier));
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<>(Formatter.formatErrorAsJson("Value " + userIdentifier + " provided for idForUser is not a valid identifier"), HttpStatus.NOT_FOUND);
            }

        }

        return carService.addNewCar(optIdForUpdate.get(), newCarDto);


    }

}
