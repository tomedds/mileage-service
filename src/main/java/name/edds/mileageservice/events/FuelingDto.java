package name.edds.mileageservice.events;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data for a fueling event
 */

public class FuelingDto {

    private Date serviceDate;
    private BigDecimal pricePerGallon;
    private BigDecimal numberOfGallons;
    private int mileage;
    private BigDecimal totalCost;
    private boolean partialFill;

    public FuelingDto() {
    }

    public FuelingDto(Date serviceDate, BigDecimal pricePerGallon, BigDecimal numberOfGallons, int mileage, BigDecimal totalCost, boolean partialFill) {

        this.serviceDate = null != serviceDate ? serviceDate : new Date();
        this.pricePerGallon = pricePerGallon;
        this.numberOfGallons = numberOfGallons;
        this.mileage = mileage;
        this.totalCost = totalCost;
        this.partialFill = partialFill;
    }

    /**
     * see if the contents of the fueling is valid
     *
     * @return
     */

    String isValid() {
        if (null == this.getServiceDate()) {
            return "FUELING event is missing service date.";
        }

        if (0 <= this.getPricePerGallon().compareTo(BigDecimal.ZERO)) {
            return "Price per gallon must be greater than zero.";
        }

        if (0 <= this.getNumberOfGallons().compareTo(BigDecimal.ZERO)) {
            return "Number of gallons must be greater than zero.";
        }

        if (this.getMileage() <= 0) {
            return "Odometer reading must be greater than zero.";
        }

        BigDecimal calcTotalCost = this.pricePerGallon.multiply(this.numberOfGallons);

        if (0 != this.getTotalCost().compareTo(calcTotalCost)) {
            return "Total cost is incorrect for fueling event.";
        }

        return "";
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
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

