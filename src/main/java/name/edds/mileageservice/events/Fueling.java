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


}

