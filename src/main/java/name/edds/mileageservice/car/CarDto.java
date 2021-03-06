package name.edds.mileageservice.car;

import name.edds.mileageservice.car_model.CarModel;
import name.edds.mileageservice.events.ServiceEvent;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarDto {

    private CarModel carModel;
    private int mileage;
    private boolean isDefault;

    public CarDto() {
    }

    public CarDto(CarModel carModel, int mileage, boolean isDefault) {
        this.carModel = carModel;
        this.mileage = mileage;
        this.isDefault = isDefault;

    }

    public CarModel getCarModel() {
        return carModel;
    }

    public int getMileage() {
        return mileage;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
