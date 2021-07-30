package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract public class AbstractDao<T> {
    protected static final Gson gson = new Gson();
    protected final MongoCollection<Document> collection;

    public AbstractDao(MongoDatabase db, String collectionName) {
        this.collection = db.getCollection(collectionName);
    }

    protected static <T> Document mapFrom(T item) {
        return Document.parse(gson.toJson(item));
    }

    public void insert(T user) {
        collection.insertOne(mapFrom(user));
    }

    public void insertMany(List<T> list) {
        List<Document> docs = list.stream()
                .map(AbstractDao::mapFrom)
                .collect(Collectors.toList());

        collection.insertMany(docs);
    }

    public List<T> findAll(Class<T> type) {
        return collection.find()
                .map(x -> gson.fromJson(x.toJson(), type))
                .into(new ArrayList<>());
    }

    public List<T> findAll(Class<T> type, Bson filter) {
        return collection.find(filter)
                .map(x -> gson.fromJson(x.toJson(), type))
                .into(new ArrayList<>());
    }
}
