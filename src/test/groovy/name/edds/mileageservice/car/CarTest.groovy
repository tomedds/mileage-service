package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.Event
import name.edds.mileageservice.events.EventType
import name.edds.mileageservice.events.Refueling
import org.bson.types.ObjectId

/**
 *  Test the construction and use of a Car
 */
@TypeChecked
class CarTest extends GroovyTestCase {

    void testCarConstruction() {

        Date testDate = new Date()
        float pricePerGallon1 = 2.779

        CarModel carModel1 = new CarModel(
                id: new ObjectId(),
                make: "American",
                model: "Rambler",
                year: "1968"
        )

        assert null != carModel1
        assert "1968" == carModel1.year

        Car car1 = new Car(

                id: new ObjectId(),
                carModel: carModel1,
                mileage: 1300,
                events: new ArrayList<Event>()
        )

        assert 1300 == car1.mileage

        assert "Rambler" == car1.carModel.model

        Refueling fillUp1 = new Refueling(
                id: new ObjectId(),
                eventType: EventType.Fueling,
                date: testDate,
                odometer: 12345,
                cost: 12.34 as float,
                numberOfGallons: 3.14 as float,
                pricePerGallon: pricePerGallon1,
                partialFill: false

        )

    /* TODO:
       car1.addEvent(fillUp1)

        assert 0 == car1.events.size()

        Refueling refueling1 = car1.events.get(0) as Refueling
        assert pricePerGallon1 == refueling1.pricePerGallon
    */

    }
}
