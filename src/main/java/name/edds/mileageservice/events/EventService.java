package name.edds.mileageservice.events;

import name.edds.mileageservice.car.CarService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class EventService {

    EventRepository eventRepository;
    CarService carService;

    @Autowired
    public EventService(EventRepository eventRepository, CarService carService) {
        this.eventRepository = eventRepository;
        this.carService = carService;
    }

    /**
     * Add a fueling event to a car
     */

    public boolean saveNewFueling(ObjectId carId, FuelingDto fuelingDto) {
           return eventRepository.createFueling(carId, new Fueling(fuelingDto));
    }
}
