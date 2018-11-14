package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import org.bson.types.ObjectId

/**
 *  Test the construction and use of a Car
 */
@TypeChecked
class CarServiceTest extends GroovyTestCase {

    /**
     * A new Car needs a model and mileage and no events
     */
    void testNewCarValidation() {

        CarService carService = new CarService()
        Car newCar = new Car()

        String errMsg = carService.validateNewCar(newCar)
        assert errMsg == "Car is missing an ID"

        newCar._id = new ObjectId()

        errMsg = carService.validateNewCar(newCar)
        assert errMsg == "Car is missing model information"

        newCar.carModel = new CarModel(make: "American", model: "Rambler", year: "1968");

        errMsg = carService.validateNewCar(newCar)
        assert errMsg == "Mileage must be greater than zero"

        newCar.mileage = 34567
        carService.validateNewCar(newCar)

        newCar.events = new ArrayList<>()
        carService.validateNewCar(newCar)

    }
}