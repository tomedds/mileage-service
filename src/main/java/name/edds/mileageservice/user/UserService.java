package name.edds.mileageservice.user;

import com.mongodb.client.MongoCollection;
import name.edds.mileageservice.Properties;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get a list of all users in the DB
     * TODO: add paging
     *
     * @return list of users
     */
    public List<User> findUsers() {
        return userRepository.findUsers();
    }

    /**
     * Add a new user to the DB
     *
     * @param user
     * @return
     */
    public Optional<ObjectId> createUser(User user) {
        // TODO: validate user
        return userRepository.createUser(user);
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    public Optional<User> findUser(ObjectId userObjectId) {
        return userRepository.findUser(userObjectId);
    }

    /**
     * Find the user with the specified identifier.
     *
     * @return the User matching the identifier if found
     * if not found, returns null
     */

    public Optional<User> findUser(String email) {
        return userRepository.findUser(email);
    }

    /**
     * Find the user with the specified ID
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    public Optional<User> getCurrentUser() {

        Optional<User> user = userRepository.findUser(Properties.CURRENT_USER_EMAIL);
        if (user.isPresent()) {
            return user;
        }
        else {
            throw new IllegalStateException("unable to find current user");
        }
    }

    /**
     * Based on solution at http://www.javapractices.com/topic/TopicAction.do?Id=180
     * <p>
     * Validate the form of an email address.
     */

    public boolean isValidEmailAddress(String aEmailAddress) {
        if (aEmailAddress == null) return false;
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(aEmailAddress);
            if (!hasNameAndDomain(aEmailAddress)) {
                result = false;
            }
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        return tokens.length == 2 && !tokens[0].isEmpty() && !tokens[1].isEmpty();
    }



}
