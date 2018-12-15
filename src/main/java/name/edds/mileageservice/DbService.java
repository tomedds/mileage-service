package name.edds.mileageservice;

import com.mongodb.client.MongoCollection;
import name.edds.mileageservice.car_model.CarModel;
import name.edds.mileageservice.user.User;
import name.edds.mileageservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Value("${mileage.mongo.db_name}")
    String dbName;

    @Value("${mileage.mongo.user_collection_name}")
    String userCollectionName;

    @Value("${mileage.mongo.car_model_collection_name}")
    String carModelCollectionName;

    public MongoCollection<User> getUserCollection() {
        return Client.getInstance()
                .getDatabase(dbName)
                .getCollection(userCollectionName, User.class);
    }

    public MongoCollection<CarModel> getCarModelCollection() {
        return Client.getInstance()
                .getDatabase(dbName)
                .getCollection(carModelCollectionName, CarModel.class);
    }


}
