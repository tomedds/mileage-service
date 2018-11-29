package name.edds.mileageservice.carmodel

import groovy.transform.TypeChecked
import org.junit.Test

import static io.restassured.RestAssured.given

@TypeChecked
class CarModelControllerTest {

    String baseUrl = "http://localhost:8081/api"
    String svcUrl = baseUrl + "/carModels"
    @Test
    public void testGetAllCarModels() {
        given().when().get(svcUrl).then().statusCode(200);
    }

}
