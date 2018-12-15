package name.edds.mileageservice.car_model;

import org.bson.types.ObjectId;

public class CarModel {
    ObjectId id;
    String make;
    String model;
    // use String for year so we match the imported data
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

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
