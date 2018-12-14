package name.edds.mileageservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/* Top level controller that says Hello. Used in testing. */

@RestController
@RequestMapping("/apis")
public class HelloController {

    /**
     * Update a car
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

}
