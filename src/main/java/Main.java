import com.mongodb.client.MongoDatabase;
import dao.AccountDao;
import dao.UserDao;
import services.UserService;
import utils.MongoUtil;


public class Main {
    private static final MongoDatabase db = MongoUtil.connect("alevel");

    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao(db);
        UserDao userDao = new UserDao(db);
        UserService service = new UserService(accountDao, userDao);
        service.run();
    }

}
