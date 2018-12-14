package name.edds.mileageservice.car_model;

import groovy.transform.TypeChecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@TypeChecked
@RestController
@RequestMapping("/api/carModels")
class CarModelController {

    CarModelService carModelService;

    @Autowired
    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarModel>> getCarModels() {

        return new ResponseEntity<>(
                carModelService.findCarModels(),
                HttpStatus.OK);


    }

}
