package name.edds.mileageservice.car;

import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public final class CarService {

    UserService userService;
    CarRepository carRepository;

    @Autowired
    CarService(UserService userService, CarRepository carRepository) {
        this.userService = userService;
        this.carRepository = carRepository;
    }

    /**
     * Validate that we have sufficient data to create a new Car
     *
     * @param car
     * @return "" or error messages
     */
    public String validateNewCar(Car car) {

        if (null == car.getId()) {
            return "Car is missing an ID";
        }

        if (null == car.getCarModel()) {
            return "Car is missing model information";
        }

        if (0 >= car.getMileage()) {
            return "Mileage must be greater than zero";
        }

        if (null != car.getEvents() && 0 < car.getEvents().size()) {
            return "A new car should not have any events";
        }

        return "";

    }

    /**
     * List cars for the specified user
     *
     * @return
     */

    public List<Car> listCars(String identifier) {
        Optional<User> user = userService.findUser(identifier);
        if (user.isPresent()) {
            return user.get().getCars();
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * List cars for the specified user
     *
     * @return
     */

    public Optional<Car> find(String identifier) {
        return carRepository.find(new ObjectId(identifier));
    }


    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @return
     */
    public String addCar(ObjectId userId, CarDto newCarDto) {

        /* Validate parameters */

        Car newCar = new Car(newCarDto);
        String message = this.validateNewCar(newCar);

        if (!message.isEmpty()) {
            return message;
        }

        Optional<User> user = userService.findUser(userId);

        if (!user.isPresent()) {
            return "user not found";
        }

        return carRepository.create(userService.getUserCollection(), user.get(), newCar);
    }

    /**
     * Find the default car for this user
     *
     * @param user
     * @return
     */
    public Optional<Car> findDefaultCar(User user) {

        return user.getCars().stream().filter(
                car -> car.isDefault()
        ).findFirst();
    }
}

