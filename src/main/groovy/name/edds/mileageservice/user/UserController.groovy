package name.edds.mileageservice.user

import groovy.transform.TypeChecked
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

    @Autowired
    UserService userService

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
     * Get the current user
     * FIXME: this will be different when we use authentication
     * @return
     */
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
                HttpStatus.CREATED);
    }

/**
 * Add a car to the user.
 * FIXME: make sure the make/model/year combination is valid
 * @return
 */
/*    @RequestMapping(value = "/{id}/cars", method = RequestMethod.POST)
    ResponseEntity<String> createCar(@PathVariable("id") String id, @RequestBody Car newCar) {

        ObjectId userId
        User user
        String errMsg

        try {
            userId = new ObjectId(id)
        }
        catch (IllegalArgumentException ex) {
            errMsg = "id value is not a valid ObjectId"
        }

        if (!errMsg) {
            // confirm that user exists
            user = userService.findUser(userId)

            if (null == user) {
                return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
            }
        }

        *//* add car and make it the default if needed *//*
        if (!errMsg) {
            errMsg = userService.createCar(userId, newCar);
        }

        if (!errMsg) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);

    }*/


}
