package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.Event
import org.bson.types.ObjectId

/**
 * Data for a fueling event
 */
@TypeChecked
class Fueling {

    Date fuelingDate
    double pricePerGallon
    double numberOfGallons
    double odometer
    double totalCost

}
