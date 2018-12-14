package name.edds.mileageservice.events;

import name.edds.mileageservice.car.Car;
import name.edds.mileageservice.car.CarService;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class EventService {

    EventRepository eventRepository;
    UserService userService;
    CarService carService;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService, CarService carService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.carService = carService;
    }

    /**
     * Add a fueling event to a car
     */

    public String saveNewFueling(User user, FuelingDto fuelingDto) {

        Optional<Car> defaultCar = carService.findDefaultCar(user);
        if (defaultCar.isPresent()) {
            return eventRepository.createFueling(userService.getUserCollection(), defaultCar.get().getId(), new Fueling(fuelingDto));
        } else {
            throw new IllegalStateException("no default car found for user=" + user.getEmail());
        }
    }
}
