package name.edds.mileageservice.car;

import name.edds.mileageservice.car_model.CarModel;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @MockBean
    private UserService mockUserService;

    @MockBean
    private CarRepository mockCarRepository;

    private CarModel basicCarModel = new CarModel("Ford", "Pinto", "1975");

    private final static String ERROR_PREFIX = "\"error\": ";
    private final static String ID_PREFIX = "\"id\": ";

    // TODO: add more detailed validation of carModel

    @Test
    public void invalidCarDtoShouldGiveErrorMessage() {

        // Prep
        String expectedErrorMsg1 = "\"Car is missing model information\"";
        String expectedErrorMsg2 = "\"Mileage must be greater than zero\"";

        ObjectId userObjectId = new ObjectId();

        CarDto carDtoMissingModel = new CarDto(null, 123, Boolean.TRUE);
        CarDto carDtoMissingMileage = new CarDto(basicCarModel, 0, Boolean.TRUE);


        // Run test
        CarService carService = new CarService(mockUserService, mockCarRepository);
        ResponseEntity<String> response = carService.addNewCar(userObjectId, carDtoMissingModel);

        // Validation
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals("{" + ERROR_PREFIX + expectedErrorMsg1 + "}", response.getBody());

        // Run test
        response = carService.addNewCar(userObjectId, carDtoMissingMileage);

        // Validation
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals("{" + ERROR_PREFIX + expectedErrorMsg2 + "}", response.getBody());

    }

    @Test
    public void userNotFoundShouldGiveErrorMessage() {

        // Prep
        String expectedErrorMsg = "\"User not found\"";

        ObjectId userObjectId = new ObjectId();
        CarDto carDto = new CarDto(basicCarModel, 123, Boolean.TRUE);


        when(mockUserService.findUser(userObjectId)).thenReturn(Optional.empty());

        // Run test
        CarService carService = new CarService(mockUserService, mockCarRepository);
        ResponseEntity<String> response = carService.addNewCar(userObjectId, carDto);

        // Validation
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertEquals("{" + ERROR_PREFIX + expectedErrorMsg + "}", response.getBody());

    }

    @Test
    public void failedCreateFoundShouldGiveErrorMessage() {

        // Prep
        String expectedErrorMsg = "\"create failed for new car\"";

        User testUser = new User("testLast", "testFirst", "user@example.com", new ArrayList<Car>());
        CarDto carDto = new CarDto(basicCarModel, 123, Boolean.TRUE);

        ObjectId userObjectId = new ObjectId();

        when(mockUserService.findUser(userObjectId)).thenReturn(Optional.of(testUser));
        when(mockCarRepository.create(any(User.class), any(Car.class))).thenReturn("invalidId");

        // Run test
        CarService carService = new CarService(mockUserService, mockCarRepository);
        ResponseEntity<String> response = carService.addNewCar(userObjectId, carDto);

        // Validation
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertEquals("{" + ERROR_PREFIX + expectedErrorMsg + "}", response.getBody());

    }

    @Test
    public void successfulCreateFoundShouldGiveSuccessMessage() {

        // Prep
        User testUser = new User("testLast", "testFirst", "user@example.com", new ArrayList<Car>());
        CarDto carDto = new CarDto(basicCarModel, 123, Boolean.TRUE);

        ObjectId userObjectId = new ObjectId();
        ObjectId carObjectId = new ObjectId();

        when(mockUserService.findUser(userObjectId)).thenReturn(Optional.of(testUser));
        when(mockCarRepository.create(any(User.class), any(Car.class))).thenReturn(carObjectId.toString());

        // Run test
        CarService carService = new CarService(mockUserService, mockCarRepository);
        ResponseEntity<String> response = carService.addNewCar(userObjectId, carDto);

        // Validation
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals("{" + ID_PREFIX + "\"" + carObjectId.toString() + "\"}", response.getBody());

    }


    //     String newCarId = carRepository.create(userService.getUserCollection(), user.get(), new Car(carDto));
}