import com.mongodb.client.MongoDatabase;
import dao.AccountDao;
import dao.UserDao;
import model.Account;
import model.User;
import utils.MongoUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Main {
    private AccountDao accountDao;
    private UserDao userDao;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        MongoDatabase db = MongoUtil.connect("alevel");
        accountDao = new AccountDao(db);
        userDao = new UserDao(db);

//        createUsers();
//        createAccounts();
//        bindAccounts();
//        findByName();
//        findByAge();
//        findByCity();
        findByAccounts();
    }

    private void createUsers() {
        final User user1 = new User("FN1", "LN1", (short) 1981, "Company 1", "City 1");
        final User user2 = new User("FN2", "LN2", (short) 1934, "Company 2", "City 1");
        final User user3 = new User("FN3", "LN3", (short) 1965, "Company 1", "City 2");
        final User user4 = new User("FN4", "LN4", (short) 1995, "Company 2", "City 3");
        userDao.insertMany(Arrays.asList(user1, user2, user3, user4));
    }

    private void createAccounts() {
        final Account acc1 = new Account(1200.0f);
        final Account acc2 = new Account(5400f);
        final Account acc3 = new Account(32879f);
        final Account acc4 = new Account(780.5f);
        final Account acc5 = new Account(44.3f);
        accountDao.insertMany(Arrays.asList(acc1, acc2, acc3, acc4, acc5));
    }

    private void bindAccounts() {
        List<Account> accounts = accountDao.findAll();
        List<User> users = userDao.findAll();

        Account acc1 = accounts.get(0);
        Account acc2 = accounts.get(1);
        Account acc3 = accounts.get(2);
        Account acc4 = accounts.get(3);
        Account acc5 = accounts.get(4);

        User u1 = users.get(0);
        User u2 = users.get(1);
        User u3 = users.get(3);

        userDao.updateAccounts(u1.getId(), Arrays.asList(acc1.getId(), acc2.getId()));
        userDao.updateAccounts(u2.getId(), Arrays.asList(acc3.getId(), acc4.getId()));
        userDao.updateAccounts(u3.getId(), Collections.singletonList(acc5.getId()));

        accountDao.updateOwner(acc1.getId(), u1.getId());
        accountDao.updateOwner(acc2.getId(), u1.getId());
        accountDao.updateOwner(acc3.getId(), u2.getId());
        accountDao.updateOwner(acc4.getId(), u2.getId());
        accountDao.updateOwner(acc5.getId(), u3.getId());
    }

    private void findByName() {
        System.out.println(
                userDao.findManyByName("FN2").toString()
        );
        System.out.println(
                userDao.findManyByName("LN3").toString()
        );
    }

    private void findByAge() {
        System.out.println(
                userDao.findManyByAge((short) 40)
        );
    }

    private void findByCity() {
        System.out.println(
                userDao.findManyByCity("City 1")
        );
    }

    private void findByAccounts() {
        System.out.println(
                userDao.findManyByAccountsCount(1)
        );
    }
}
