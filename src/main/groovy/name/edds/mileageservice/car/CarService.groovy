package name.edds.mileageservice.car

class CarService {

    /**
     * Validate that we have sufficient data to create a new Car
     *
     * @param car
     * @return "" or error messages
     */
    String validateNewCar(Car car) {

        if (null == car._id) {
            return "Car is missing an ID"
        }

        if (null == car.carModel) {
            return "Car is missing model information"
        }

        if (0 >= car.mileage) {
            return "Mileage must be greater than zero"
        }

        if (null != car.events && 0 <= car.events.size()) {
            return "A new car should not have any events"
        }

        return ""

    }


}
