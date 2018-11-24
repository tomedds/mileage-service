package name.edds.mileageservice.user

import com.mongodb.MongoClientSettings
import com.mongodb.client.*

import groovy.transform.TypeChecked
import name.edds.mileageservice.Properties
import name.edds.mileageservice.car.Car
import name.edds.mileageservice.car.CarService
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Updates.*
import static org.bson.codecs.configuration.CodecRegistries.fromProviders
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries

@TypeChecked
@Component
class UserService {

    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.user_collection_name}')
    String userCollectionName

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    List<User> listUsers() {

        MongoCollection<User> userCollection = setupUserCollection()

        List<User> users = new ArrayList<>()

        MongoCursor<User> userCursor = userCollection.find().iterator()

        try {
            while (userCursor.hasNext()) {
                users.add(userCursor.next())
            }
        } finally {
            userCursor.close();
        }

        return users
    }

    /**
     * Add a new user to the DB
     * @param user
     * @return
     */
    ObjectId addUser(User user) {

        MongoCollection<User> userCollection = setupUserCollection()

        try {
            userCollection.insertOne(user)
            return user._id
        } catch (e) {
            // TODO: add logger
            println(e);
        }

    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User findUser(ObjectId userObjectId) {
        MongoCollection<User> userCollection = setupUserCollection()
        userCollection.find(eq("_id", userObjectId)).first();
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User getCurrentUser() {
        MongoCollection<User> userCollection = setupUserCollection()
        userCollection.find(eq("email", Properties.CURRENT_USER_EMAIL)).first();
    }

    /**
     * Find the user with the specified mail
     *
     * @return the User matching the email if found
     * if not found, returns ??
     */
    User findUser(String email) {
        MongoCollection<User> userCollection = setupUserCollection()
        userCollection.find(eq("email", email)).first()
    }

    /**
     * Add a car for this user. These are stored as embedded records within the User record.
     * If this car is going to be the default, we need to reset the flag for any current default car
     *
     * @param id
     * @param car
     * @return
     */
    String addCarToUser(ObjectId id, Car car) {

        /* Validate parameters */

        CarService carService = new CarService()
        car._id = new ObjectId()
        String message = carService.validateNewCar(car)

        if (message) {
            return message
        }

        User user = findUser(id)

        if (null == user) {
            return "user not found"
        }

        /* Proceed with adding this car to this user */
        if (null == user.cars) {
            user.cars = new ArrayList<Car>()
        }

        /* reset the default flag if needed */

        user.cars.each {
            thisCar ->
                if (thisCar.isDefault) {
                    thisCar.isDefault = false
                }
        }
        car.dateAdded = new Date()
        user.cars.add(car)

        MongoCollection<User> userCollection = setupUserCollection()

        userCollection.updateOne(eq("_id", id),
                combine(set("cars", user.cars)))

        return ""
    }

    MongoCollection<User> setupUserCollection() {

        MongoClient mongoClient = MongoClients.create();

        // create codec registry for POJOs
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry)

        mileageDb.getCollection(userCollectionName, User.class)

    }

    /**
     * Create a User from a document
     * @param document
     * @return user
     */
/*    User createUserFromDocument(Document userDocument) {

        if (null == userDocument) {
            return null
        }

        new User(_id: userDocument._id as ObjectId,
                lastName: userDocument.lastName as String,
                firstName: userDocument.firstName as String,
                email: userDocument.email as String)
    }*/


}
