package org.edds.mileageservice.car

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
class CarController {

    @Autowired
    CarService carService

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Car>> getCars() {

        return new ResponseEntity<>(
                carService.listCars(),
                HttpStatus.OK);


    }

}