package name.edds.mileageservice.user;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import name.edds.mileageservice.Client;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static jdk.nashorn.internal.objects.Global.println;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Repository
public class UserRepository {

    @Value("${mileage.mongo.db_name}")
    String dbName;

    @Value("${mileage.mongo.user_collection_name}")
    String userCollectionName;

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    public User test1() {
        User user1 = new User("last", "first", "email@example.com", null);
        return user1;
    }
    /**
     * Get the collection for use with queries
     *
     * @return
     */
    public MongoCollection<User> getUserCollection() {
       return Client.getInstance()
                .getDatabase(dbName).withCodecRegistry(pojoCodecRegistry)
                .getCollection(userCollectionName, User.class);
    }

    /**
     * Add a new user to the DB
     *
     * @param user
     * @return
     */
    public ObjectId createUser(User user) {

        MongoCollection<User> userCollection = getUserCollection();

        try {
            userCollection.insertOne(user);
            return user.getId();
        } catch (MongoWriteException e) {
            // TODO: add logger
            println(e);
            return null;
        }


        /*            * @throws com.mongodb.MongoWriteException        if the write failed due some other failure specific to the insert command
         * @throws com.mongodb.MongoWriteConcernException if the write failed due being unable to fulfil the write concern
         * @throws com.mongodb.MongoException

         */
    }

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    public List<User> findUsers() {

        MongoCollection<User> userCollection = getUserCollection();

        List<User> users = new ArrayList<>();

        MongoCursor<User> userCursor = userCollection.find().iterator();

        try {
            while (userCursor.hasNext()) {
                users.add(userCursor.next());
            }
        } finally {
            userCursor.close();
        }

        return users;
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    public Optional<User> findUser(ObjectId userObjectId) {
        MongoCollection<User> userCollection = getUserCollection();
        User user = userCollection.find(eq("_id", userObjectId)).first();
        return (null != user) ? Optional.of(user) : Optional.empty();

    }

    /**
     * Find the user with the specified identifier.
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    public Optional<User> findUser(String email) {
        MongoCollection<User> userCollection = getUserCollection();
        User user = userCollection.find(eq("email", email)).first();
        return (null != user) ? Optional.of(user) : Optional.empty();
    }

    /**
     * Update the user record with a modified version
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    public User updateUser(User user) {
        throw new IllegalStateException("method not implemented");
    }

    /**
     * Delete the user record with a modified version
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    public User deleteUser(ObjectId userId) {
        throw new IllegalStateException("method not implemented");
    }


}
