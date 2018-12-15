package name.edds.mileageservice.user;

import name.edds.mileageservice.car.Car;
import name.edds.mileageservice.car.CarDto;
import name.edds.mileageservice.car.CarRepository;
import name.edds.mileageservice.car.CarService;
import name.edds.mileageservice.car_model.CarModel;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository mockUserRepository;

    @Test
    public void detectsInvalidEmailAddress() {

        Assert.assertTrue(userService.isValidEmailAddress("user@example.com"));

        Assert.assertFalse(userService.isValidEmailAddress("username"));
        Assert.assertFalse(userService.isValidEmailAddress("username@example"));
        Assert.assertFalse(userService.isValidEmailAddress("user name@example.com"));
    }

    @Test
    public void noUsersShouldReturnEmptyList() {

        // Prep
        when(mockUserRepository.findUsers()).thenReturn(new ArrayList<>());

        // Run test
        UserService userService = new UserService(mockUserRepository);
        List<User> allUsers = userService.findUsers();

        // Validation
        Assert.assertTrue(allUsers.isEmpty());

    }

    @Test
    public void successfulCreateShouldReturnId() throws InvalidUserException {

        // Prep

        User testUser = new User("testLast", "testFirst", "user@example.com", new ArrayList<>());
        Optional<ObjectId> userObjectId = Optional.of(new ObjectId());
        when(mockUserRepository.createUser(any(User.class))).thenReturn(userObjectId);

        // Run test
        UserService userService = new UserService(mockUserRepository);

        Optional<ObjectId> newUserId = userService.createUser(testUser);

        // Validation
        Assert.assertEquals(userObjectId.get(), newUserId.get());

    }

    @Test
    public void failedCreateShouldProvideRelevantMessage() {

        // Prep

        User noLastUser = new User("", "testFirst", "user@example.com", new ArrayList<>());
        User noFirstUser = new User("testLast", "", "user@example.com", new ArrayList<>());
        User noEmailUser = new User("testLast", "testFirst", "", new ArrayList<>());
        User invalidEmailUser = new User("testLast", "testFirst", "username", new ArrayList<>());

        Optional<ObjectId> userObjectId = Optional.of(new ObjectId());
        when(mockUserRepository.createUser(any(User.class))).thenReturn(userObjectId);

        // Run test and validate
        UserService userService = new UserService(mockUserRepository);

        try {
            Optional<ObjectId> newUserId = userService.createUser(noLastUser);
            Assert.assertEquals("should never get here", "");
        }
        catch (InvalidUserException ex) {
            Assert.assertEquals("last name is missing.", ex.getMessage());
        }

        try {
            Optional<ObjectId> newUserId = userService.createUser(noFirstUser);
            Assert.assertEquals("should never get here", "");
        }
        catch (InvalidUserException ex) {
            Assert.assertEquals("first name is missing.", ex.getMessage());
        }

        try {
            Optional<ObjectId> newUserId = userService.createUser(noEmailUser);
            Assert.assertEquals("should never get here", "");
        }
        catch (InvalidUserException ex) {
            Assert.assertEquals("email is missing.", ex.getMessage());
        }

        try {
            Optional<ObjectId> newUserId = userService.createUser(invalidEmailUser);
            Assert.assertEquals("should never get here", "");
        }
        catch (InvalidUserException ex) {
            Assert.assertEquals("email is invalid.", ex.getMessage());
        }



    }



    /* To test:

    findUser(OBjectId)
    findUser(Email)

     */

}