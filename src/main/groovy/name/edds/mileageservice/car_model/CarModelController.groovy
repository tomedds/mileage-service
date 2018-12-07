package name.edds.mileageservice.car_model

import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@TypeChecked
@RestController
@RequestMapping("/api/carModels")
class CarModelController {

    @Autowired
    CarModelService carModelService

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<CarModel>> getCarModels() {

        return new ResponseEntity<>(
                carModelService.findCarModels(),
                HttpStatus.OK);


    }

}
