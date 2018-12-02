package name.edds.mileageservice.user


import com.mongodb.client.*

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import groovy.transform.TypeChecked
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress
import name.edds.mileageservice.Properties
import name.edds.mileageservice.mongo.Client
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@TypeChecked
@Component
class UserService {

    @Value('${mileage.mongo.db_name}')
    String dbName;

    @Value('${mileage.mongo.user_collection_name}')
    String userCollectionName

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    List<User> listUsers() {

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
     * Add a new user to the DB
     * @param user
     * @return
     */
    ObjectId addUser(User user) {

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
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User getCurrentUser() {
        MongoCollection<User> userCollection = getUserCollection()
        userCollection.find(eq("email", Properties.CURRENT_USER_EMAIL)).first();
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

    MongoCollection<User> getUserCollection() {
        Client.getInstance().getDatabase(dbName).withCodecRegistry(pojoCodecRegistry)
                .getCollection(userCollectionName, User.class)
    }

    /**
     * Based on solution at http://www.javapractices.com/topic/TopicAction.do?Id=180
     *
     * Validate the form of an email address.
     */

    boolean isValidEmailAddress(String aEmailAddress) {
        if (aEmailAddress == null) return false;
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(aEmailAddress);
            if (!hasNameAndDomain(aEmailAddress)) {
                result = false;
            }
        }
        catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        return tokens.length == 2 && !tokens[0].isEmpty() && !tokens[1].isEmpty()
    }


}
