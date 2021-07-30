package dao;

import com.mongodb.client.MongoDatabase;
import model.User;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Написать методы:
 * - Найти пользователей с конкретным именем
 * - Найти пользователей меньше или равно заданному возрасту
 * - Найти всех пользователей в конкретном городе
 * - Реализовать метод поиска пользователей с >2 аккаунтами и отобразить информацию
 */
public class UserDao extends AbstractDao<User> {
    private final static String COLLECTION_NAME = "users";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String BIRTH_YEAR = "birthYear";
    private final static String CITY = "city";
    private final static String ACCOUNTS = "accounts";

    public UserDao(MongoDatabase db) {
        super(db, COLLECTION_NAME);
    }

    public List<User> findAll() {
        return super.findAll(User.class);
    }

    public void updateAccounts(String userId, List<String> accounts) {
        final Document filter = new Document();
        filter.append("id", userId);

        final Document update = new Document();
        update.append("accounts", accounts);

        final Document doc = new Document();
        doc.append("$set", update);

        collection.updateOne(filter, doc);
    }

    /**
     * Search by the first or last name.
     */
    public List<User> findManyByName(String name) {
        return super.findAll(User.class, or(eq(FIRST_NAME, name), eq(LAST_NAME, name)));
    }

    /**
     * Search by the first or last name.
     */
    public List<User> findManyByCity(String city) {
        return super.findAll(User.class, eq(CITY, city));
    }

    /**
     * Search by accounts number (more that given count).
     */
    public List<User> findManyByAccountsCount(int count) {
        String query = "this.accounts.length > " + count;
        return super.findAll(User.class, and(exists(ACCOUNTS), where(query)));
    }

    /**
     * Search by age given or younger.
     */
    public List<User> findManyByAge(short age) {
        short bornIn = (short) (LocalDate.now().getYear() - age);
        return super.findAll(User.class, gte(BIRTH_YEAR, bornIn));
    }

}
