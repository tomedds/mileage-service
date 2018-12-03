package name.edds.mileageservice.events

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import groovy.transform.TypeChecked
import name.edds.mileageservice.user.User
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@TypeChecked
class EventService {

    @Autowired
    EventRepository eventRepository

    /**
     * Add a fueling event to a car
     *
     * @param user
     * @param car
     * @param fueling
     * @return
     */

    String addFueling(MongoCollection<User> userCollection, ObjectId carId, Fueling fueling) {

        fueling.date = new Date()
        fueling.eventType = EventType.Fueling
        fueling.id = new ObjectId()

        String errMsg = fueling.isValid()

        if (errMsg.isEmpty()) {
            errMsg = eventRepository.createFueling(userCollection, carId, fueling)
        }

        return errMsg

    }
}
