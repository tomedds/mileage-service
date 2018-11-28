package name.edds.mileageservice.user

import groovy.transform.TypeChecked
import name.edds.mileageservice.car.Car
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId;

@TypeChecked
class User {

    /* Codec returns null if this is "id" instead of "id" */
    ObjectId id


    String lastName
    String firstName
    String email
    List<Car> cars

    /* TODO: implement equals(), hashCode(), toString() */

}
