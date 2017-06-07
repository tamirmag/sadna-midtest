package UsersTests;

import DB.IUsersDB;
import Loggers.IActionLogger;
import Loggers.IErrorLogger;
import Users.*;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserManagerTests {

    static UserManager u;
    static User roy = new User(1, "roy", "1235", 1, "rzarviv@gmail.com", new Wallet(100));

    @BeforeClass
    public static void configure() {
        IUsersDB.getInstance().changeDataStore("tests");
    }

    @Before
    public void inTheBeginningOfEveryTest() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
    }

    @After
    public void inTheEndOfEveryTest() throws NegativeValue {
        IAccountManager.getInstance().clearUsers();
        IAccountManager.getInstance().clearLeagues();
    }

    @AfterClass
    public static void reconfigure() {
        IUsersDB.getInstance().changeDataStore("system");
        IActionLogger.getInstance().clearLog();
        IErrorLogger.getInstance().clearLog();
    }

    //tests
    @Test
    public void setUserName() throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        u.editProfile("roy1", roy.getPassword(), roy.getEmail());
        u.logout();
        u = IAccountManager.getInstance().login("roy1", roy.getPassword());
        assertEquals(u.getUser().getUsername(), "roy1");
    }

    @Test(expected = UsernameNotValid.class)
    public void UsernameNotValidSetUserName() throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        String INVALID_NAME = null;
        u.editProfile(INVALID_NAME, roy.getPassword(), roy.getEmail());
    }

    @Test
    public void setPassword() throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        u.editProfile(roy.getUsername(), "1234", roy.getEmail());
        u.logout();
        u = IAccountManager.getInstance().login("roy", "1234");
        assertEquals(u.getUser().getPassword(), "1234");
    }

    @Test(expected = PasswordNotValid.class)
    public void PasswordNotValidSetPassword() throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        String INVALID_PASSWORD = null;
        u.editProfile(roy.getUsername(), INVALID_PASSWORD, roy.getEmail());
    }

    @Test
    public void setEmail() throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        u.editProfile(roy.getUsername(), roy.getPassword(), "rzarldjbkd@gmail.com");
        u.logout();
        u = IAccountManager.getInstance().login(roy.getUsername(), roy.getPassword());
        assertEquals(u.getUser().getEmail(), "rzarldjbkd@gmail.com");
    }

    @Test(expected = EmailNotValid.class)
    public void EmailNotValidSetEmail() throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        final String INVALID_EMAIL = "   ";
        u.editProfile(roy.getUsername(), roy.getPassword(), INVALID_EMAIL);

    }

    @Test
    public void getTop20GrossProfit() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        IAccountManager.getInstance().register("tamir", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("mor", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("nofar", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("yoni", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());

        ArrayList<User> users = IAccountManager.getInstance().getTop20GrossProfit();
        assertEquals(users.size(), 5);
    }

    @Test
    public void getTop20highestCashGained() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        IAccountManager.getInstance().register("tamir", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("mor", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("nofar", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("yoni", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());

        ArrayList<User> users = IAccountManager.getInstance().getTop20highestCashGained();
        assertEquals(users.size(), 5);
    }

    @Test
    public void getTop20NumOfGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        IAccountManager.getInstance().register("tamir", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("mor", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("nofar", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        IAccountManager.getInstance().register("yoni", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());

        ArrayList<User> users = IAccountManager.getInstance().getTop20NumOfGames();
        assertEquals(users.size(), 5);
    }

    @Test
    public void getUserAverageCashGain() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists {
        IAccountManager.getInstance().register("tamir", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        double avg = u.getUserAverageCashGain("tamir");
        assertTrue(avg == 0);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistGetUserAverageCashGain() throws UserNotExists {
        double avg = u.getUserAverageCashGain("tamir");
    }

    @Test
    public void getUserAverageGrossProfit() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists {
        IAccountManager.getInstance().register("tamir", roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        double avg = u.getUserAverageGrossProfit("tamir");
        assertTrue(avg == 0);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistgetUserAverageGrossProfit() throws UserNotExists {
        double avg = u.getUserAverageGrossProfit("tamir");
    }


}




  /*  @Test
      public void setDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserAlreadyInLeague, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking {
          zerbib.setHighestRanking(true);
          IIAccountManager.getInstance().addUser(zerbib);
          UserManager zerbib1 = IIAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
          zerbib1.setDefaultLeague(200);
          assertEquals(IAccountManager.getInstance().getDefaultLeague(), 200);
      }

      @Test(expected = NegativeValue.class)
      public void checkFailedSetDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking {
          final int NEGATIVE_LEAGUE =-4;
          zerbib.setHighestRanking(true);
          IIAccountManager.getInstance().addUser(zerbib);
          UserManager zerbib1 = IIAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
          zerbib1.setDefaultLeague(NEGATIVE_LEAGUE);
      }

      @Test(expected = UserAlreadyInLeague.class)
      public void UserAlreadyInLeagueMoveUserToLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserAlreadyInLeague, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotInLeague, LeagueNotExists, NotHighestRanking {
          zerbib.setHighestRanking(true);
          IIAccountManager.getInstance().addUser(zerbib);
          UserManager zerbib1 = IIAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
          zerbib1.moveUserToLeague("roy", 100);
          zerbib1.moveUserToLeague("roy", 100);
      }*/