package name.edds.mileageservice.events

import groovy.transform.TypeChecked
import name.edds.mileageservice.events.EventType
import name.edds.mileageservice.events.ServiceEvent
import org.bson.types.ObjectId

/**
 * Data for a fueling event
 */
@TypeChecked
class Fueling extends ServiceEvent {

    BigDecimal pricePerGallon
    BigDecimal numberOfGallons
    boolean partialFill

    /**
     * see if the contents of the fueling is valid
     * @return
     */

    String isValid() {

        if (!this.date) {
            return "Fueling event is missing date."
        }

        if (!this.id) {
            return "Fueling event is missing ID."
        }

        if (EventType.Fueling != this.eventType) {
            return "Fueling event cannot have type ${this.eventType}."
        }

        if (!this.odometer) {
            return "Odometer reading cannot be zero."
        }
        if (!this.pricePerGallon) {
            return "Price per gallon cannot be zero."
        }

        if (!this.numberOfGallons) {
            return "Number of gallons cannot be zero."
        }

        BigDecimal calcTotalCost = this.pricePerGallon*this.numberOfGallons
        calcTotalCost = calcTotalCost.round(2)
        if (this.totalCost != calcTotalCost) {
            return "Total cost is incorrect for fueling event."
        }

        return ""

    }

}

