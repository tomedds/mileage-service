package name.edds.mileageservice.car_model

import groovy.transform.TypeChecked
import org.bson.types.ObjectId

@TypeChecked
class CarModel {

    ObjectId _id
    String make
    String model
    // use String for year so we match the imported data
    String year

}
