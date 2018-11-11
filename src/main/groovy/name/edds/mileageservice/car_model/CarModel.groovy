package name.edds.mileageservice.car_model

import groovy.transform.TypeChecked
import org.bson.types.ObjectId

@TypeChecked
class CarModel {

    ObjectId id;
    String make
    String model
    String year

}
