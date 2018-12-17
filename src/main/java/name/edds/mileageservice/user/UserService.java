package name.edds.mileageservice.user;

import name.edds.mileageservice.Formatter;
import name.edds.mileageservice.Properties;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get a list of all users in the DB
     * FIXME: add paging using start and count parameters to work with Mongo cursor.
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
    public  ResponseEntity<String> createUser(User user) throws InvalidUserException {

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidUserException("first name is missing.");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidUserException("last name is missing.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidUserException("email is missing.");
        }

        if (!isValidEmailAddress(user.getEmail())) {
            throw new InvalidUserException("email is invalid.");
        }

        // Cars can be empty but no null
        if (user.getCars() == null) {
            throw new InvalidUserException("list of cars is null.");
        }

        // TODO: need to format ID returned as JSON
        Optional<ObjectId> newUserId = userRepository.createUser(user);

        if (newUserId.isPresent()) {
            return new ResponseEntity<>("{\"id\": \"" + String.valueOf(newUserId.get()) + "\"}", HttpStatus.CREATED);
         } else {
            return new ResponseEntity<>(Formatter.formatErrorAsJson("user create failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
     * <p>
     * FIXME: this needs to come from a real source instead of the current hack.
     *
     * @return the User matching the ID if found
     * if not found, returns ??
     */
    public Optional<User> getCurrentUser() {

        Optional<User> user = userRepository.findUser(Properties.CURRENT_USER_EMAIL);
        if (user.isPresent()) {
            return user;
        } else {
            throw new IllegalStateException("unable to find current user");
        }
    }

    /**
     * Based on solution at http://www.javapractices.com/topic/TopicAction.do?Id=180
     * <p>
     * Validate the form of an email address.
     */

    public boolean isValidEmailAddress(String emailAddr) {

        LOGGER.debug("validating [" + emailAddr + "] as an email address.");

        if (emailAddr == null) {
            return false;
        }

        boolean isValid = true;

        try {
            InternetAddress emailAddr2 = new InternetAddress(emailAddr);
            LOGGER.debug("successfully created [" + emailAddr2.toString() + "] as an email address.");
            if (!hasNameAndDomain(emailAddr)) {
                isValid = false;
            }
        } catch (AddressException ex) {
            isValid = false;
        }
        return isValid;
    }

    private boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        if (2 > tokens.length || tokens[0].isEmpty() || tokens[1].isEmpty()) {
            return false;
        }

        String[] domainParts = tokens[1].split("\\.");
        if (2 > domainParts.length) {
            return false;
        }

        return true;
    }


}
