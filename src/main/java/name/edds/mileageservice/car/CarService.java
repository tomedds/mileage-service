package name.edds.mileageservice.car;

import name.edds.mileageservice.Formatter;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Add a new car to the database
     *
     * @param userObjectId
     * @param carDto
     * @return
     */

    public ResponseEntity<String> addNewCar(ObjectId userObjectId, CarDto carDto) {

        String validationMessage = this.validateDto(carDto);

        if (validationMessage.isEmpty()) {

            Optional<User> user = userService.findUser(userObjectId);

            if (user.isPresent()) {

                /* add car and make it the default */
                // FIXME: currently the most recently added car is the default

                String newCarId = carRepository.create(user.get(), new Car(carDto));

                if (ObjectId.isValid(newCarId)) {
                    return new ResponseEntity<>("{\"id\": \"" + newCarId + "\"}", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(Formatter.formatErrorAsJson("create failed for new car"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(Formatter.formatErrorAsJson("User not found"), HttpStatus.NOT_FOUND);
            }

        } else {
            // DTO has invalid data
            return new ResponseEntity<>(Formatter.formatErrorAsJson(validationMessage), HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * Validate that we have sufficient data to create a new Car
     *
     * @param carDto
     * @return "" or error messages
     */

    public String validateDto(CarDto carDto) {

        // TODO: add more detailed validation of carModel
        if (null == carDto.getCarModel()) {
            return "Car is missing model information";
        }

        if (0 >= carDto.getMileage()) {
            return "Mileage must be greater than zero";
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

