package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import org.bson.types.ObjectId

@TypeChecked
class Car {

    ObjectId id;
    CarModel carModel
    int mileage

}
