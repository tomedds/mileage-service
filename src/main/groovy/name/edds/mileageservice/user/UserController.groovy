package name.edds.mileageservice.user

import groovy.transform.TypeChecked
import io.swagger.annotations.ApiOperation

import name.edds.mileageservice.car.Car
import name.edds.mileageservice.car.CarService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

// FIXME: allow lookup for user by either ID or email without specifying type.
// If it is a value email, use it that way.
// Otherwise, assume it's an ID

@TypeChecked
@RestController
@RequestMapping("/api/users")
class UserController {

    UserService userService
    CarService carService

    @Autowired
    UserController(UserService userService, CarService carService) {
        this.userService = userService
        this.carService = carService
    }

    /**
     * Get the list of users
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(
                userService.findUsers(),
                HttpStatus.OK)
    }

    /**
     * Get all of the cars for the specified user
     * @return list of Cars
     */
    @RequestMapping(value = "/{identifier}/cars", method = RequestMethod.GET)
    ResponseEntity<List<Car>> getCarsForUser(@PathVariable("identifier") String identifier) {
        new ResponseEntity<>(carService.listCars(identifier), HttpStatus.OK)
    }


    /**
     * Get the current user
     * FIXME: this will be different when we use authentication
     * @return
     */
    @ApiOperation(value = "Get current user", notes = "")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    ResponseEntity<User> getCurrentUser() {

        User user = userService.getCurrentUser()

        if (null == user) {
            return new ResponseEntity<User>(
                    HttpStatus.NOT_FOUND)
        } else {

            return new ResponseEntity<User>(
                    user,
                    HttpStatus.OK)
        }

    }

    /**
     * Get a single user using either the ID or email
     * @return
     */
    @RequestMapping(value = "/{identifier}", method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@PathVariable("identifier") String identifier) {

        User user = null

        if (userService.isValidEmailAddress(identifier)) {
            user = userService.findUser(identifier)
        } else {
            try {
                ObjectId objectId = new ObjectId(identifier)
                user = userService.findUser(objectId)
            }
            catch (IllegalArgumentException ex) {
                System.err.println("id is not a valid ObjectId")
                new ResponseEntity<User>(
                        user,
                        HttpStatus.BAD_REQUEST)
            }
        }

        if (null == user) {
            return new ResponseEntity<User>(
                    HttpStatus.NOT_FOUND)
        } else {
            return new ResponseEntity<User>(
                    user,
                    HttpStatus.OK)
        }

    }

/**
 * Create a new user
 * @return
 */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<String> addUser(@RequestBody User user) {

        ObjectId result = userService.createUser(user)

        return new ResponseEntity<String>(
                String.valueOf(result),
                HttpStatus.CREATED)
    }

    /**
     * Add a car to the user.
     * FIXME: make sure the make/model/year combination is valid
     * @return
     */
    @RequestMapping(value = "/{identifier}/cars", method = RequestMethod.POST)
    ResponseEntity<String> addCarToUser(@PathVariable("userId") String userId, @RequestBody Car newCar) {

        ObjectId userObjectId
        User user
        String resultStr

        try {
            userObjectId = new ObjectId(userId)
        }
        catch (IllegalArgumentException ex) {
            resultStr = "id value is not a valid ObjectId"
        }

        if (!resultStr) {
            // confirm that user exists
            user = userService.findUser(userObjectId)

            if (null == user) {
                return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
            }
        }

        /* add car and make it the default if needed */
        // FIXME: currently the most recently added car is the default

        if (!resultStr) {
            resultStr = carService.addCar(userObjectId, newCar);
        }

        // at this point resultStr is either the id for the new car or an error message

        if (ObjectId.isValid(resultStr)) {
            return new ResponseEntity<String>("{\"id\": \"$resultStr\"}".toString(), HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(resultStr, HttpStatus.BAD_REQUEST);

    }


}
