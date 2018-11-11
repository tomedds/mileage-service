package name.edds.mileageservice.user


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
import org.springframework.web.bind.annotation.RestController

// TypeChecked
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
                        null,
                        HttpStatus.NOT_FOUND)
            } else {

                return new ResponseEntity<User>(
                        user,
                        HttpStatus.OK)
            }
        }
        catch (IllegalArgumentException ex) {
            //  LOGGER.error()
            System.err.println("id is not a valid ObjectId")
            new ResponseEntity<User>(
                    null,
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
    ResponseEntity<String> addCarToUser(@PathVariable("id") String id, @RequestBody CarModel carModel) {

        try {
            ObjectId userObjectId = new ObjectId(id)

            Car newCar = new Car(carModel: carModel)

            String msg = userService.addCarToUser(userObjectId, newCar).isEmpty();

            if (msg.isEmpty()) {
                return new ResponseEntity<String>(
                        "",
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        catch (IllegalArgumentException ex) {
            //  LOGGER.error()
            System.err.println("id is not a valid ObjectId")
            return new ResponseEntity<String>("id is not a valid ObjectId", HttpStatus.BAD_REQUEST)
        }


    }

}
