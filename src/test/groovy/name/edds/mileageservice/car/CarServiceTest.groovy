package name.edds.mileageservice.car

import groovy.transform.TypeChecked
import name.edds.mileageservice.car_model.CarModel
import name.edds.mileageservice.events.EventService
import name.edds.mileageservice.user.UserService
import org.bson.types.ObjectId
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 *  Test the construction and use of a Car
 */
@TypeChecked
@RunWith(SpringRunner.class)
@SpringBootTest
class CarServiceTest extends GroovyTestCase {

    @Autowired
    UserService userService
    @Autowired
    CarRepository carRepository

    CarServiceTest() {
    }

/**
     * A new Car needs a model and mileage and no events
     */
    @Test
    void testNewCarValidation() {

        CarService carService = new CarService(userService, carRepository)
        Car newCar = new Car()

        String errMsg = carService.validateNewCar(newCar)
        assert errMsg == "Car is missing an ID"

        newCar.id = new ObjectId()

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
