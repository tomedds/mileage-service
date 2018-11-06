package org.edds.mileageservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

 @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "{\"cars\" : \"empty\" }";

    }

}
