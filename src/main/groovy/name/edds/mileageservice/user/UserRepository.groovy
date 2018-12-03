package name.edds.mileageservice.user

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import groovy.transform.TypeChecked
import name.edds.mileageservice.Client
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

import static com.mongodb.client.model.Filters.eq
import static org.bson.codecs.configuration.CodecRegistries.fromProviders
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries

@Repository
@TypeChecked
class UserRepository {

    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.user_collection_name}')
    String userCollectionName

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    /**
     * Get the collection for use with queries
     * @return
     */
    MongoCollection<User> getUserCollection() {
        Client.getInstance().getDatabase(dbName).withCodecRegistry(pojoCodecRegistry)
                .getCollection(userCollectionName, User.class)
    }

    /**
     * Add a new user to the DB
     * @param user
     * @return
     */
    ObjectId createUser(User user) {

        MongoCollection<User> userCollection = getUserCollection()

        try {
            userCollection.insertOne(user)
            return user.id
        } catch (e) {
            // TODO: add logger
            println(e);
        }

    }

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    List<User> findUsers() {

        MongoCollection<User> userCollection = getUserCollection()

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
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User findUser(ObjectId userObjectId) {
        MongoCollection<User> userCollection = getUserCollection()
        userCollection.find(eq("_id", userObjectId)).first()
    }

    /**
     * Find the user with the specified identifier.
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    User findUser(String email) {
        MongoCollection<User> userCollection = getUserCollection()
        userCollection.find(eq("email", email)).first()
    }

    /**
     * Update the user record with a modified version
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    User updateUser(User user) {
        throw new IllegalStateException("method not implemented")
    }

    /**
     * Delete the user record with a modified version
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    User deleteUser(ObjectId userId) {
        throw new IllegalStateException("method not implemented")
    }


}
