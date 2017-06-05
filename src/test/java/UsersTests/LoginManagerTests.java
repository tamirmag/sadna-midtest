package UsersTests;

import Loggers.ActionLogger;
import Users.*;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LoginManagerTests {

    static User roy = new User(1,"roy", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User zerbib = new User(2,"zerbib", "123456", 7, "rzarviv@gmail.com", new Wallet(100));

    @Test
    public void checkSuccessLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        IAccountManager.getInstance().addUser(roy);
        UserManager u = IAccountManager.getInstance().login("roy", "1235");
        assertEquals(u.getUser(), roy);
    }


    @Test(expected = AlreadyLoggedIn.class)
    public void checkAlreadyLoggedInLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        UserManager u = IAccountManager.getInstance().login("roy", "1235");

    }

    @Test(expected = UsernameNotValid.class)
    public void checkUsernameNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        final String EMPTY_USERNAME = "";
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        UserManager u = IAccountManager.getInstance().login(EMPTY_USERNAME, "1235");

    }

    @Test(expected = PasswordNotValid.class)
    public void checkPasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        final String EMPTY_PASSWORD = "";
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        UserManager u = IAccountManager.getInstance().login("roy", EMPTY_PASSWORD);

    }

    @Test(expected = UsernameAndPasswordNotMatch.class)
    public void checkUsernamePasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists {
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        UserManager u = IAccountManager.getInstance().login("roy", "1234");

    }

    @Test(expected = UserAlreadyExists.class)
    public void checkUserAlreadyExistsLogin() throws UserAlreadyExists, UsernameNotValid {
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addUser(roy);
    }

    @Test
    public void checkSuccessLogout() throws AlreadyLoggedIn, UserAlreadyExists, UserNotExists, AlreadyLoggedOut, UsernameNotValid {
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        IAccountManager.getInstance().logout(roy);
    }

    @Test(expected = AlreadyLoggedOut.class)
    public void checkAlreadyLoggedOutLogout() throws UserAlreadyExists, UserNotExists, AlreadyLoggedOut, AlreadyLoggedIn, UsernameNotValid {
        IAccountManager.getInstance().addUser(roy);
        IAccountManager.getInstance().addLoggedInUser(roy);
        IAccountManager.getInstance().logout(roy);
        IAccountManager.getInstance().logout(roy);
    }

    @Test(expected = UserNotExists.class)
    public void checkUserNotExistsLogout() throws UserNotExists, AlreadyLoggedOut {
        IAccountManager.getInstance().logout(roy);
    }

    @Test
    public void checkSuccessRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        assertEquals(u.getUser(), roy);
    }

    @Test(expected = EmailNotValid.class)
    public void checkEmailNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String STAM_EMAIL = "@@@@@dkjng";
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), STAM_EMAIL, roy.getWallet().getAmountOfMoney());

    }

    @Test(expected = NegativeValue.class)
    public void checkNegativeValueRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final int NEGATIVE_WALLET = -1;
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), NEGATIVE_WALLET);
    }

    @Test(expected = UsernameNotValid.class)
    public void checkUsernameNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String EMPTY_STRING = "";
        UserManager u = IAccountManager.getInstance().register(EMPTY_STRING, roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
    }

    @Test(expected = PasswordNotValid.class)
    public void checkPasswordNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        final String EMPTY_STRING = "";
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), EMPTY_STRING, roy.getEmail(), roy.getWallet().getAmountOfMoney());
    }

    @Test(expected = UserAlreadyExists.class)
    public void checkUserAlreadyExistsRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        UserManager u1 = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
    }

    @Test
    public void logInLogOut() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        UserManager u = IAccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
        u.logout();
        u = IAccountManager.getInstance().login(roy.getUsername(), roy.getPassword());
        u.logout();
        u = IAccountManager.getInstance().login(roy.getUsername(), roy.getPassword());
        u.logout();
    }


    @After
    public void inTheEndOfEveryTest() throws NegativeValue {
        IAccountManager.getInstance().clearLoggedInUsers();
        IAccountManager.getInstance().clearUsers();
        IAccountManager.getInstance().clearLeagues();
        ActionLogger.getInstance().clearLog();

    }



}

