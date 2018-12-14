import static io.restassured.RestAssured.given;
import groovy.transform.*;
import junit.framework.Test;

@TypeChecked
public class HelloWorldRestAssured {

    @org.junit.Test
    public void makeSureThatGoogleIsUp() {
        io.restassured.RestAssured.given().when().get("http://www.google.com").then().statusCode(200);
    }

}