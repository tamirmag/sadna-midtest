package UsersTests;

import DB.IUsersDB;
import Loggers.ActionLogger;
import Users.*;
import org.junit.*;

import static junit.framework.TestCase.assertTrue;


public class AccountManagerTest {

    @Test
    public void SuccessRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        User u = AccountManager.getInstance().getUser("roy");
        assertTrue(u.getUsername().equals("roy"));

    }

    @Test
    public void checkSuccessLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
        UserManager u = AccountManager.getInstance().login("roy", "1235");
        assertTrue(u.getUser().getUsername().equals("roy"));
    }


    @Test(expected = AlreadyLoggedIn.class)
    public void checkAlreadyLoggedInLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().login("roy", "1235");

    }

    @Test(expected = UsernameNotValid.class)
    public void checkUsernameNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        final String EMPTY_USERNAME = "";
        AccountManager.getInstance().login(EMPTY_USERNAME, "1235");

    }

    @Test(expected = PasswordNotValid.class)
    public void checkPasswordNotValidLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        final String EMPTY_PASSWORD = "";
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
        AccountManager.getInstance().login("roy", EMPTY_PASSWORD);

    }

    @Test(expected = UsernameAndPasswordNotMatch.class)
    public void checkUsernamePasswordNotValidLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
        AccountManager.getInstance().login("roy", "1234");
    }

    @Test(expected = UserAlreadyExists.class)
    public void checkUserAlreadyExistsLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
    }

    @Test
    public void checkSuccessLogout() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
    }

    @Test(expected = AlreadyLoggedOut.class)
    public void checkAlreadyLoggedOutLogout() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
        AccountManager.getInstance().logout("roy");
    }

    @Test(expected = UserNotExists.class)
    public void checkUserNotExistsLogout() throws UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().logout("roy");
    }

    @Test(expected = EmailNotValid.class)
    public void checkEmailNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String STAM_EMAIL = "@@@@@dkjng";
        AccountManager.getInstance().register("roy", "1235", STAM_EMAIL, 100);
    }

    @Test(expected = NegativeValue.class)
    public void checkNegativeValueRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final int NEGATIVE_WALLET = -1;
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", NEGATIVE_WALLET);
    }

    @Test(expected = UsernameNotValid.class)
    public void checkUsernameNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String EMPTY_STRING = "";
        AccountManager.getInstance().register(EMPTY_STRING, "1235", "rzarviv@gmail.com", 100);
    }

    @Test(expected = PasswordNotValid.class)
    public void checkPasswordNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String EMPTY_STRING = "";
        AccountManager.getInstance().register("roy", EMPTY_STRING, "rzarviv@gmail.com", 100);
    }

    @Test(expected = UserAlreadyExists.class)
    public void checkUserAlreadyExistsRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
    }

    @Test
    public void logInLogOut() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        AccountManager.getInstance().register("roy", "1235", "rzarviv@gmail.com", 100);
        AccountManager.getInstance().logout("roy");
        AccountManager.getInstance().login("roy", "1235");
        AccountManager.getInstance().logout("roy");
        AccountManager.getInstance().login("roy", "1235");
        AccountManager.getInstance().logout("roy");

    }
    @BeforeClass
    public static void configure() {
        IUsersDB.getInstance().changeDataStore("tests");
    }
    @AfterClass
    public static void reconfigure() {
        IUsersDB.getInstance().changeDataStore("systemDatabase");
    }


    @Before
    public void start() {
        ActionLogger.getInstance().clearLog();
        AccountManager.getInstance().clearUsers();
    }

    @After
    public void end() {
        ActionLogger.getInstance().clearLog();
        AccountManager.getInstance().clearUsers();
    }


}
