package dao;

import com.mongodb.client.MongoDatabase;
import model.Account;
import org.bson.Document;

import java.util.List;

public class AccountDao extends AbstractDao<Account> {
    private static final String COLLECTION_NAME = "accounts";

    public AccountDao(MongoDatabase db) {
        super(db, COLLECTION_NAME);
    }

    public List<Account> findAll() {
        return super.findAll(Account.class);
    }

    public void updateOwner(String accountId, String ownerId) {
        final Document filter = new Document();
        filter.append("id", accountId);

        final Document update = new Document();
        update.append("ownerId", ownerId);

        final Document doc = new Document();
        doc.append("$set", update);

        collection.updateOne(filter, doc);
    }

}
