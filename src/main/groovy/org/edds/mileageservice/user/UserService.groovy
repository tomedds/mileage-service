package org.edds.mileageservice.user

import com.mongodb.client.*
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

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

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName)
        MongoCollection<Document> userCollection = mileageDb.getCollection(userCollectionName)

        MongoCursor<Document> cursor = userCollection.find().iterator()

        List<User> users = new ArrayList<>()

        try {
            while (cursor.hasNext()) {
                def userDocument = cursor.next()
                User user = new User(lastName: userDocument.lastName,
                        firstName: userDocument.firstName,
                        email: userDocument.email)
                users.add(user)
            }
        } finally {
            cursor.close();
        }

        return users
    }

    /**
     *
     * @param user
     * @return
     */
    ObjectId addUser(User user) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mileageDb = mongoClient.getDatabase(dbName)
        MongoCollection<Document> userCollection = mileageDb.getCollection(userCollectionName)

        try {
            Document userDoc = new Document("lastName", user.lastName).append("firstName", user.firstName).append("email", user.email)
                       userCollection.insertOne(userDoc)
            return userDoc._id
        } catch (e) {
            // TODO: add logger
            println(e);
        }

    }


}
