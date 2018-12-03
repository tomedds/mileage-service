package name.edds.mileageservice.events

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import groovy.transform.TypeChecked
import name.edds.mileageservice.user.User
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

@Repository
@TypeChecked
class EventRepository {

    /**
     * Add a fueling event to a car
     *
     *
     * @param user
     * @param car
     * @param fueling
     * @return
     */

    String createFueling(MongoCollection<User> userCollection, ObjectId carId, Fueling fueling) {

        UpdateResult ur = userCollection.updateOne(
                Filters.eq("cars._id", carId),
                Updates.push('''cars.$[currentCar].events''', fueling),
                new UpdateOptions().arrayFilters(
                        Collections.singletonList(
                                Filters.eq("currentCar._id", carId)))
        )

        if (1 == ur.modifiedCount) {
            return ""
        } else {
            return "error adding fueling event to car"
        }

    }

}
