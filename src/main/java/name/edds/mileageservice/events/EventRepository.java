package name.edds.mileageservice.events;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import name.edds.mileageservice.user.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public final class EventRepository {

    /**
     * Add a fueling event to a car
     *
     * @return
     */

   public String createFueling(MongoCollection<User> userCollection, ObjectId carId, Fueling fueling) {

        UpdateResult ur = userCollection.updateOne(
                Filters.eq("cars._id", carId),
                Updates.push("cars.$[currentCar].events", fueling),
                new UpdateOptions().arrayFilters(
                        Collections.singletonList(
                                Filters.eq("currentCar._id", carId))));

        if (1 == ur.getModifiedCount()) {
            return "";
        } else {
            return "error adding fueling event to car";
        }

    }

}
