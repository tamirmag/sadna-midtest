package UsersTests;

import Loggers.ActionLogger;
import Users.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserManagerTests {

    static UserManager u;
    static User roy = new User("roy", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User zerbib = new User("zerbib","123456",7,"rzarviv@gmail.com",new Wallet(100));
    @Test
    public void setUserName () throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        u.editProfile("roy1" , roy.getPassword(),roy.getEmail());
        u.logout();
        u= AccountManager.getInstance().login("roy1",roy.getPassword());
        assertEquals(u.getUser().getUsername() , "roy1");
    }

    @Test(expected = UsernameNotValid.class)
    public void UsernameNotValidSetUserName () throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        String INVALID_NAME = null;
        u.editProfile(INVALID_NAME , roy.getPassword(),roy.getEmail());
    }

    @Test
    public void setPassword () throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        u.editProfile(roy.getUsername(),"1234" ,roy.getEmail());
        u.logout();
        u= AccountManager.getInstance().login("roy","1234");
        assertEquals(u.getUser().getPassword() , "1234");
    }

    @Test(expected = PasswordNotValid.class)
    public void PasswordNotValidSetPassword () throws UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        String INVALID_PASSWORD = null;
        u.editProfile(roy.getUsername() , INVALID_PASSWORD,roy.getEmail());
    }
    @Test
    public void setEmail () throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        u.editProfile(roy.getUsername(),roy.getPassword() ,"rzarldjbkd@gmail.com");
        u.logout();
        u= AccountManager.getInstance().login(roy.getUsername(),roy.getPassword());
        assertEquals(u.getUser().getEmail() , "rzarldjbkd@gmail.com");
    }

    @Test(expected = EmailNotValid.class)
    public void EmailNotValidSetEmail () throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
        final String INVALID_EMAIL="   ";
        u.editProfile(roy.getUsername(),roy.getPassword() ,INVALID_EMAIL);

    }
  /*  @Test
    public void setDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserAlreadyInLeague, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking {
        zerbib.setHighestRanking(true);
        IAccountManager.getInstance().addUser(zerbib);
        UserManager zerbib1 = IAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
        zerbib1.setDefaultLeague(200);
        assertEquals(AccountManager.getInstance().getDefaultLeague(), 200);
    }

    @Test(expected = NegativeValue.class)
    public void checkFailedSetDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking {
        final int NEGATIVE_LEAGUE =-4;
        zerbib.setHighestRanking(true);
        IAccountManager.getInstance().addUser(zerbib);
        UserManager zerbib1 = IAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
        zerbib1.setDefaultLeague(NEGATIVE_LEAGUE);
    }

    @Test(expected = UserAlreadyInLeague.class)
    public void UserAlreadyInLeagueMoveUserToLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserAlreadyInLeague, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotInLeague, LeagueNotExists, NotHighestRanking {
        zerbib.setHighestRanking(true);
        IAccountManager.getInstance().addUser(zerbib);
        UserManager zerbib1 = IAccountManager.getInstance().login(zerbib.getUsername(), zerbib.getPassword());
        zerbib1.moveUserToLeague("roy", 100);
        zerbib1.moveUserToLeague("roy", 100);
    }*/


    @Before
    public void inTheBeginningOfEveryTest() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        u= AccountManager.getInstance().register(roy.getUsername(), roy.getPassword(), roy.getEmail(), roy.getWallet().getAmountOfMoney());
    }

    @After
    public void inTheEndOfEveryTest() throws NegativeValue {
        AccountManager.getInstance().clearLoggedInUsers();
        AccountManager.getInstance().clearUsers();
        AccountManager.getInstance().clearLeagues();
        //AccountManager.getInstance().setDefaultLeague(0);
        ActionLogger.getInstance().clearLog();
    }

}

