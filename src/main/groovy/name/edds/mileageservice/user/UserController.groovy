package name.edds.mileageservice.user

import groovy.transform.TypeChecked
import name.edds.mileageservice.car.CarService
import org.bson.types.ObjectId
import name.edds.mileageservice.car.Car
import name.edds.mileageservice.car_model.CarModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@TypeChecked
@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService

    /**
     * Get the list of users
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(
                userService.listUsers(),
                HttpStatus.OK)
    }

    /**
     * Get a single user
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<User> getUser(@PathVariable("id") String id) {

        try {
            ObjectId userObjectId = new ObjectId(id)

            User user = userService.findUser(userObjectId)

            if (null == user) {
                return new ResponseEntity<User>(
                        HttpStatus.NOT_FOUND)
            } else {

                return new ResponseEntity<User>(
                        user,
                        HttpStatus.OK)
            }
        }
        catch (IllegalArgumentException ex) {
            //  LOGGER.error()
            System.err.println("_id is not a valid ObjectId")
            new ResponseEntity<User>(

                    HttpStatus.BAD_REQUEST)

        }
    }

/**
 * Create a new user
 * @return
 */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<String> addUser(@RequestBody User user) {

        ObjectId result = userService.addUser(user)

        return new ResponseEntity<String>(
                String.valueOf(result),
                HttpStatus.CREATED);
    }

/**
 * Add a car to the user.
 * @return
 */
    @RequestMapping(value = "/{id}/cars", method = RequestMethod.POST)
    ResponseEntity<String> addCarToUser(@PathVariable("id") String id, @RequestBody Car newCar,
                                        @RequestParam(required = false, value = "makeDefault") Boolean makeDefault) {

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

        if (!errMsg) {
            errMsg = userService.addCarToUser(userId, newCar);
        }

        // make this the default car if requested.

        if (!errMsg && makeDefault) {
            userService.updateDefaultCar(user, newCar)
        }

        if (!errMsg) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);

    }

}
