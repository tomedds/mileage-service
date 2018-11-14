package name.edds.mileageservice.events

import groovy.transform.TypeChecked
import org.bson.types.ObjectId

/**
 * Data for a fueling stop
 */
@TypeChecked
class Event {

    ObjectId id;
    EventType eventType
    Date date
    int odometer
    float cost


}

