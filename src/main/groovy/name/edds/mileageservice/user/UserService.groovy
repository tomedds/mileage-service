package name.edds.mileageservice.user


import com.mongodb.client.MongoCollection
import groovy.transform.TypeChecked
import name.edds.mileageservice.Properties
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

@TypeChecked
@Service
class UserService {

    @Autowired
    UserRepository userRepository

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    List<User> findUsers() {
        userRepository.findUsers()
    }

    /**
     * Add a new user to the DB
     * @param user
     * @return
     */
    ObjectId createUser(User user) {
        // TODO: validate user
        userRepository.createUser(user)
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User findUser(ObjectId userObjectId) {
        userRepository.findUser(userObjectId)
    }

    /**
     * Find the user with the specified identifier.
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    User findUser(String email) {
        userRepository.findUser(email)
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    User getCurrentUser() {
        userRepository.findUser( Properties.CURRENT_USER_EMAIL)
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


    /* Service-level method to return collection

     */
     MongoCollection<User> getUserCollection() {
        userRepository.getUserCollection()
    }




}
