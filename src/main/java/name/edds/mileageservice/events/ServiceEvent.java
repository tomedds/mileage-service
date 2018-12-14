package name.edds.mileageservice.events;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Common data for refueling, service, etc
 */

public abstract class ServiceEvent {

    ObjectId id;
    EventType eventType;
    Date date;
    int odometer;
    BigDecimal totalCost;

}

