package name.edds.mileageservice.car;

import name.edds.mileageservice.car_model.CarModel;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @MockBean
    private UserService mockUserService;

    @MockBean
    private CarRepository mockCarRepository;

    private CarModel basicCarModel = new CarModel("Ford", "Pinfo", "1975");

    private final static String ERROR_PREFIX = "\"error\", ";
    @Test
    public void userNotFoundShouldGiveErrorMessage()  {

        // Prep
        String expectedErrorMsg = "\"User not found\"";

        ObjectId userObjectId = new ObjectId();
        CarDto carDto = new CarDto(basicCarModel, 123, Boolean.TRUE);

        CarService carService = new CarService(mockUserService, mockCarRepository);

        when(mockUserService.findUser(userObjectId)).thenReturn(Optional.empty());

        // Run test
        ResponseEntity<String> response = carService.addNewCar(userObjectId, carDto);

        // Validation

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertEquals("{" + ERROR_PREFIX + expectedErrorMsg + "}", response.getBody());

    }
}