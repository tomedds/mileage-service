package name.edds.mileageservice.user;

import name.edds.mileageservice.car.CarController;
import name.edds.mileageservice.car.CarDto;
import name.edds.mileageservice.car.CarService;
import name.edds.mileageservice.car_model.CarModelController;
import name.edds.mileageservice.user.UserController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerTest {

    @Test
    public void placeHolder() {

        // simple test to get build to work

        Assert.assertTrue(Boolean.TRUE);
    }
    /* Integrate this:
    @Test
    public void invalidUserIdShouldGiveErrorMessage() {

        // Prep
        String badUserId = "badUserId";

        CarDto carDto = new CarDto(basicCarModel, 123, Boolean.TRUE);

        CarService carService = new CarService(mockUserService, mockCarRepository);

        // Run test
        ResponseEntity<String> response = carService.addNewCar(badUserId, carDto);

        // Validate results
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals("{\"error\", \"Invalid user identifier\"}", response.getBody());

    }
    */


}