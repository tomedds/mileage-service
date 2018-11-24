package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.Event
import org.bson.types.ObjectId

@TypeChecked
class Car {

    ObjectId _id;
    CarModel carModel
    int mileage
    Date dateAdded
    List <Event> events
    boolean isDefault

    void addEvent(Event event) {
        // TODO: validate event first
        events.add(event)
    }

    /*
      after there are documents for fill ups, consider adding other fields that contain the cumulative values for those fields
      (mileage tracked, money spent on gas, number of gallson) so they don't have to be computred each time.
      Or does the overhead of keeping this accurate without having to using mongo do the math when there is a query.
      
     */

}
