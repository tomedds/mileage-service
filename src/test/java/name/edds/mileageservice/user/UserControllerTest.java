package name.edds.mileageservice.user;

import name.edds.mileageservice.car.Car;
import name.edds.mileageservice.car.CarDto;
import name.edds.mileageservice.car.CarService;
import name.edds.mileageservice.car_model.CarModel;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    /* Mock the service tier. This means anything we call there needs to have a "given" definition */
    @MockBean
    private UserService mockUserService;

    @MockBean
    private CarService mockCarService;

    private final String USERS_ENDPOINT = "/api/users";

    @Test
    public void testFindUsers() throws Exception {

        List<User> userList = new ArrayList<>();
        userList.add(new User("testLast", "testFirst", "user@example.com", new ArrayList<>()));

        String userListAsJson = new JSONArray(userList).toString();

        given(this.mockUserService.findUsers()).willReturn(userList);

        this.mvc.perform(get(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(userListAsJson));
    }

    /**
     * Test the controller endpoint for finding a single user.
     * The identifier can be either an email address or a userid. Since we have to mcock the
     * call to isValidEmailAddress(), no distinction is made in the test.
     *
     * @throws Exception
     */
    @Test
    public void testFindSingleUser() throws Exception {

        String userEmail = "user2@example.com";

        String testUrl = USERS_ENDPOINT + "/" + userEmail;
        User newUser = new User("testLast", "testFirst", userEmail, new ArrayList<>());

        String userAsJson = new JSONObject(newUser).toString();

        /* define all the methods we need in userService */
        given(this.mockUserService.findUser(userEmail)).willReturn(Optional.of(newUser));
        given(this.mockUserService.isValidEmailAddress(any(String.class))).willReturn(Boolean.TRUE);

        this.mvc.perform(get(testUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(userAsJson));
    }

    @Test
    public void testAddUser() throws Exception {

        ObjectId objectId = new ObjectId();

        given(this.mockUserService.createUser(any(User.class))).willReturn(Optional.of(objectId));

        User newUser = new User("testLast", "testFirst", "user@example.com", new ArrayList<>());
        String userAsJson = new JSONObject(newUser).toString();

        this.mvc.perform(post(USERS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsJson))
                .andExpect(status().isCreated())
        ;
    }


    @Test
    public void testFindCarsForUser() throws Exception {

        String userEmail = "user2@example.com";

        String testUrl = getCarsEndPoint(userEmail);

        List<Car> noCars = new ArrayList<>();

        String carsAsJson = new JSONArray(noCars).toString();

        /* define all the methods we need for services */
        given(this.mockCarService.listCars(any(String.class))).willReturn(new ArrayList<>());

        this.mvc.perform(get(testUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(carsAsJson));
    }

    @Test
    public void testAddCarToUser() throws Exception {

        // Prep
        ObjectId objectId = new ObjectId();
        String testUrl = getCarsEndPoint(String.valueOf(objectId));

        CarModel carModel = new CarModel("Buick", "LeSabre", "1999");
        CarDto carDto = new CarDto(carModel, 12345, Boolean.TRUE);
        String carDtoAsJson = new JSONObject(carDto).toString();

        ResponseEntity<String> responseToReturn = new ResponseEntity<>("{\"id\": \"" + String.valueOf(objectId) + "\"}", HttpStatus.CREATED);

        given(this.mockCarService.addNewCar(any(ObjectId.class), any(CarDto.class))).willReturn(responseToReturn);

        String newCarId = new JSONObject(objectId).toString();

        // Run test and validate
        this.mvc.perform(post(testUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(carDtoAsJson))
                .andExpect(status().isCreated())
        ;
    }

    /**
     * Define this here since we need it multiple times
     * @param identifier
     * @return
     */
    private String getCarsEndPoint(String identifier) {
        return USERS_ENDPOINT + "/" + identifier + "/cars";
    }


}