package name.edds.mileageservice.car_model;

import org.bson.types.ObjectId;

public class CarModel {
    ObjectId id;
    String make;
    String model;
    // use String for yearso we match the data obtained from external source
    String year;

    public CarModel() {
    }

    public CarModel(String make, String model, String year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public CarModel(ObjectId id, String make, String model, String year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
