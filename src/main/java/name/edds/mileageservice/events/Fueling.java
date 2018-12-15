package name.edds.mileageservice.events;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data for a fueling event
 */

public final class Fueling extends ServiceEvent {

    private BigDecimal pricePerGallon;
    private BigDecimal numberOfGallons;
    private int mileage;
    private BigDecimal totalCost;
    private boolean partialFill;

    public Fueling() {
    }

    public Fueling(FuelingDto fuelingDto) {
        id = new ObjectId();
        eventType = EventType.FUELING;
        date = new Date();
        pricePerGallon = fuelingDto.getPricePerGallon();
        numberOfGallons = fuelingDto.getNumberOfGallons();
        mileage = fuelingDto.getMileage();
        totalCost = fuelingDto.getTotalCost();
        this.partialFill = fuelingDto.isPartialFill();
    }

    public BigDecimal getPricePerGallon() {
        return pricePerGallon;
    }

    public void setPricePerGallon(BigDecimal pricePerGallon) {
        this.pricePerGallon = pricePerGallon;
    }

    public BigDecimal getNumberOfGallons() {
        return numberOfGallons;
    }

    public void setNumberOfGallons(BigDecimal numberOfGallons) {
        this.numberOfGallons = numberOfGallons;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPartialFill() {
        return partialFill;
    }

    public void setPartialFill(boolean partialFill) {
        this.partialFill = partialFill;
    }
}

