package name.edds.mileageservice;

import name.edds.mileageservice.car.CarController;
import name.edds.mileageservice.car_model.CarModelController;
import name.edds.mileageservice.user.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private CarController carController;

	@Autowired
	private CarModelController carModelController;

	@Autowired
	private UserController userController;

	@Test
	public void contextLoads() {

		assertThat(carController).isNotNull();
		assertThat(carModelController).isNotNull();
		assertThat(userController).isNotNull();
	}


}