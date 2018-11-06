package org.edds.mileageservice.user

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

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

}
