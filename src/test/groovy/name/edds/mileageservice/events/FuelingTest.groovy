package name.edds.mileageservice.events

import groovy.transform.TypeChecked
import name.edds.mileageservice.car.Car
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.EventType
import name.edds.mileageservice.events.Fueling
import name.edds.mileageservice.events.ServiceEvent
import org.bson.types.ObjectId

/**
 *  Test the construction and validation of a Fueling event
 *  TODO: modify error cases to check for specific message. This makes sense when we extract the text strings
 */

@TypeChecked
class FuelingTest extends GroovyTestCase {

    BigDecimal ppg = 2.49
    BigDecimal numGals = 5.3
    BigDecimal total = ppg * numGals

    void testFuelingValidationSuccess() {
        Fueling goodFueling = populateFueling()
        assert goodFueling.isValid().isEmpty()
    }


    void testFuelingValidationMissingId() {
        Fueling badFueling = populateFueling()
        badFueling.id = null
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationBadEventType() {
        Fueling badFueling = populateFueling()
        badFueling.eventType = null
        assert !badFueling.isValid().isEmpty()
        badFueling.eventType = EventType.Repairs
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationMissingDate() {
        Fueling badFueling = populateFueling()
        badFueling.date = null
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationMissingOdometer() {
        Fueling badFueling = populateFueling()
        badFueling.odometer = 0
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationMissingPpg() {
        Fueling badFueling = populateFueling()
        badFueling.pricePerGallon = 0.0
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationMissingNumGals() {
        Fueling badFueling = populateFueling()
        badFueling.numberOfGallons = 0.0
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationMissingTotalCost() {
        Fueling badFueling = populateFueling()
        badFueling.totalCost = 0.0
        assert !badFueling.isValid().isEmpty()
    }

    void testFuelingValidationInvalidTotalCost() {
        Fueling badFueling = populateFueling()
        badFueling.totalCost = 2.0 + badFueling.totalCost
        assert !badFueling.isValid().isEmpty()
    }

    /* Populate a Fueling that can be used by other tests */

    Fueling populateFueling() {
         new Fueling(
                id: new ObjectId(),
                eventType: EventType.Fueling,
                date: new Date(),
                odometer: 12345,
                pricePerGallon: ppg,
                numberOfGallons: numGals,
                partialFill: false,
                totalCost: total.round(2)
        )

    }

}
