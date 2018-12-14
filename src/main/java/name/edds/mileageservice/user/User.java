package name.edds.mileageservice.user;
import name.edds.mileageservice.car.Car;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public final class User {

    /* Codec returns null if this is "id" instead of "id" */
    private ObjectId id;
    private String lastName;
    private String firstName;
    private String email;
    private List<Car> cars = new ArrayList<>();

    /* ?? Use this for DTO? */
    public User(String lastName, String firstName, String email, List<Car> cars) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.cars = cars;
    }

    public User(User user, List<Car> newCars) {
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.cars = newCars;
    }

    /* TODO: implement equals(), hashCode(), toString() */

    public ObjectId getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public List<Car> getCars() {
        return cars;
    }
}
