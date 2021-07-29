import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        MongoDatabase database = connect("aLevel", credential);*/

        MongoDatabase database = connect("aLevel");

        // CRUD / Create
        createData(database);

        // CRUD / Read
        readAllData(database);

        // CRUD / Update
        //readOneData(database);
        //updateData(database);
        //readOneData(database);

        // CRUD / Delete
        //readAllData(database);
        //deleteData(database);
        //readAllData(database);
    }

    private static void createData(MongoDatabase database) {
        final User user1 = new User("Bill", "Loney");
        final User user2 = new User("Chip", "Munk");
        final User user3 = new User("Forrest", "Green");

        final Document document1 = mapperFrom(user1);
        final Document document2 = mapperFrom(user2);
        final Document document3 = mapperFrom(user3);

        MongoCollection<Document> users = database.getCollection("users");
        List<Document> documents = Arrays.asList(document1, document2, document3);
        users.insertMany(documents);
    }

    private static void readAllData(MongoDatabase database) {
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find();
        for (Document document : documents) {
            System.out.println(document);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void readOneData(MongoDatabase database) {
        final Document filter = new Document();
        filter.append("firstName", "Bill");
        MongoCollection<Document> users = database.getCollection("users");
        FindIterable<Document> documents = users.find(filter);
        for (Document document : documents) {
            System.out.println(document);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void updateData(MongoDatabase database) {
        final Document filter = new Document();
        filter.append("firstName", "Bill");
        filter.append("lastName", "Bill");

        final Document newData = new Document();
        newData.append("lastName", "Loney999");

        final Document updateObject  = new Document();
        updateObject.append("$set", newData);

        MongoCollection<Document> users = database.getCollection("users");
        users.updateOne(filter, updateObject);
    }

    private static void deleteData(MongoDatabase database) {
        final Document filter = new Document();
        filter.append("firstName", "Bill");

        MongoCollection<Document> users = database.getCollection("users");
        users.deleteOne(filter);
    }

    private static MongoDatabase connect(String databaseName) {
        return getMongoClient(null).getDatabase(databaseName);
    }

    private static MongoDatabase connect(String databaseName, MongoCredential credential) {
        return getMongoClient(credential).getDatabase(databaseName);
    }

    private static MongoClient mongoClient;

    private static MongoClient getMongoClient(MongoCredential credential) {
        if (mongoClient == null) {
            final MongoClientOptions.Builder options = MongoClientOptions.builder();
            mongoClient = credential == null ? new MongoClient("localhost", 27017) :
                    new MongoClient(new ServerAddress("localhost", 27017), credential, options.build());
        }

        return mongoClient;
    }

    private static Document mapperFrom(User user) {
        final Document document = new Document();
        document.append("firstName", user.getFirstName());
        document.append("lastName", user.getLastName());
        return document;
    }

    private static User mapperTo(Document document) {
        final User user = new User(
                document.get("firstName", String.class),
                document.get("lastName", String.class)
        );
        user.setId(document.get("_id", String.class));
        return user;
    }
}
