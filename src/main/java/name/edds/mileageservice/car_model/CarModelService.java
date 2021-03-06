package name.edds.mileageservice.car_model;

import groovy.transform.TypeChecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@TypeChecked
@Service
public class CarModelService {

    @Autowired
    CarModelRepository carModelRepository;

    /**
     * Get a list of all car models in the DB
     * TODO: add use of paging
     *
     * @return list of car models
     */
    List<CarModel> findCarModels() {
        return carModelRepository.findCarModels();
    };

}
