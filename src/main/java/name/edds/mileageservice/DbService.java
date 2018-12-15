package name.edds.mileageservice;

import com.mongodb.client.MongoCollection;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    UserRepository userRepository;

    @Autowired
    public DbService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MongoCollection<User> getUserCollection() {
        return userRepository.getUserCollection();
    }

}
