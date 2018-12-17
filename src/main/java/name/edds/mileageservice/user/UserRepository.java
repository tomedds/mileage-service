package name.edds.mileageservice.user;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import name.edds.mileageservice.DbService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static jdk.nashorn.internal.objects.Global.println;

@Repository
public class UserRepository {

    @Autowired
    DbService dbService;

    /**
     * Add a new user to the DB
     *
     * @param user
     * @return
     */
    public Optional<ObjectId> createUser(User user) {

        MongoCollection<User> userCollection = dbService.getUserCollection();

        try {
            ObjectId newId = new ObjectId();
            user.setId(newId);
            userCollection.insertOne(user);
            return Optional.of(user.getId());
        } catch (MongoWriteException ex) {
            // TODO: add logger
            println(ex);
            return Optional.empty();
        }

    }

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    public List<User> findUsers() {

        MongoCollection<User> userCollection = dbService.getUserCollection();

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
        MongoCollection<User> userCollection = dbService.getUserCollection();
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
        MongoCollection<User> userCollection = dbService.getUserCollection();
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
