package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.Fueling
import name.edds.mileageservice.events.ServiceEvent
import name.edds.mileageservice.events.EventType
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
                events: new ArrayList<ServiceEvent>()
        )

        assert 1300 == car1.mileage

        assert "Rambler" == car1.carModel.model


    }
}
