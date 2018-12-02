package name.edds.mileageservice.events

import groovy.transform.TypeChecked
import org.bson.types.ObjectId

/**
 * Common data for refueling, service, etc
 */
@TypeChecked
class ServiceEvent {

    ObjectId id
    EventType eventType
    Date date
    int odometer
    BigDecimal totalCost

}

