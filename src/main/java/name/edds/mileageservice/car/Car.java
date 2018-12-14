package name.edds.mileageservice.car;

import name.edds.mileageservice.car_model.CarModel;
import name.edds.mileageservice.events.ServiceEvent;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Car {

    private ObjectId id;
    private CarModel carModel;
    private int mileage;
    private Date dateAdded;
    private List<ServiceEvent> events = new ArrayList<>();
    private boolean isDefault;

    public Car(ObjectId id, CarModel carModel, int mileage, Date dateAdded, List<ServiceEvent> events, boolean isDefault) {
        this.id = id;
        this.carModel = carModel;
        this.mileage = mileage;
        this.dateAdded = dateAdded;
        this.events = events;
        this.isDefault = isDefault;
    }

    public Car(CarDto carDto) {
        this.id = new ObjectId();
        this.dateAdded = new Date();
        this.mileage = carDto.getMileage();
        this.isDefault = carDto.isDefault();
        this.carModel = carDto.getCarModel();
    }

    /**
     * Use this to create a Car with a different value for isDefault
     * @param isDefault
     */
    public Car(Car car, boolean isDefault) {
        this.id = car.id;
        this.carModel = car.carModel;
        this.mileage = car.mileage;
        this.dateAdded = car.dateAdded;
        this.events = car.events;
        this.isDefault = isDefault;
    }

    /*
      after there are documents for fill ups, consider adding other fields that contain the cumulative values for those fields
      (mileage tracked, money spent on gas, number of gallson) so they don't have to be computred each time.
      Or does the overhead of keeping this accurate without having to using mongo do the math when there is a query.
      
     */

    public ObjectId getId() {
        return id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public int getMileage() {
        return mileage;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public List<ServiceEvent> getEvents() {
        return events;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
