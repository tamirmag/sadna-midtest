package Acceptance;

import static org.junit.Assert.*;
import java.util.ArrayList;
import Games.CantJoin;
import Games.NotAllowedNumHigh;
import Loggers.IActionLogger;
import Loggers.IActiveGamesLogManager;
import Loggers.IErrorLogger;
import Loggers.IFinishedGamesManager;
import Users.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ServiceLayer.ServiceUser;

public class acctests {
    static Bridge bridge = Driver.getBridge();
    static ServiceUser u;

    @After
    public void afterAnyTest() throws AlreadyLoggedOut, UserNotExists {
        bridge.clearLoggedInUsers();
        bridge.clearUsers();
        IActionLogger.getInstance().clearLog();
        IErrorLogger.getInstance().clearLog();
        bridge.clearAllFinishedGameLogs();
    }

    @AfterClass
    public static void afterAllTests() throws AlreadyLoggedOut, UserNotExists {
        bridge.clearLoggedInUsers();
        bridge.clearUsers();
        IActionLogger.getInstance().clearLog();
        IErrorLogger.getInstance().clearLog();
        IFinishedGamesManager.getInstance().deleteAllFinishedGameLogs();
        IActiveGamesLogManager.getInstance().RemoveAllGameLoggers();
        bridge.clearAllFinishedGameLogs();
    }


    @Before
    public void atStart() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists {
      //  u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
      //  bridge.logout("moshe");
    }

    @Test
    public void testRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        assertEquals(u.getUsername(), "moshe");
        assertEquals(u.getPassword(), "1111");
        assertEquals(u.getEmail(), "noname@gmail.com");
        assertFalse(u.getUsername().equals(""));
        assertFalse(u.getPassword().equals(""));
        assertFalse(u.getEmail().equals(""));
        assertFalse(u.getWallet().getAmountOfMoney() < 0);
    }

    @Test
    public void testLoginWithIncorrectPassword() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
        u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        String password = "1111";
        bridge.login("moshe", password);
        assertEquals(u.getPassword(), password);
    }

    @Test
    public void testLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
        //ServiceUser u2 = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        ServiceUser u1 = bridge.login("moshe", "1111");
        assertEquals(u1.getUsername(), "moshe");
        assertEquals(u1.getPassword(), "1111");
        assertFalse(u1.getUsername().equals(""));
        assertFalse(u1.getPassword().equals(""));
        assertTrue((u1.getUsername().equals(u.getUsername()) && u1.getPassword().equals(u.getPassword())) || u1.getUsername().equals(u.getUsername()));
    }

    @Test(expected = AlreadyLoggedIn.class)
    public void AlreadyLoggedInLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.login("moshe", "1111");
    }

    @Test(expected = UsernameNotValid.class)
    public void UsernameNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.login("", "1111");
    }

    @Test(expected = PasswordNotValid.class)
    public void PasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "");
    }

    @Test(expected = UsernameAndPasswordNotMatch.class)
    public void UsernamePasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "2222");
    }

    @Test(expected = UserAlreadyExists.class)
    public void UserAlreadyExistsLogin() throws UserAlreadyExists, UsernameNotValid, EmailNotValid, NegativeValue, PasswordNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
    }

    @Test
    public void testLogOut() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        String uname = "moshe";
        bridge.logout(uname);
        assertFalse(uname.equals(""));
    }

    @Test(expected = AlreadyLoggedOut.class)
    public void AlreadyLoggedOutLogout() throws UserAlreadyExists, UserNotExists, AlreadyLoggedOut, AlreadyLoggedIn, UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, EmailNotValid, NegativeValue {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.logout("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsLogout() throws UserNotExists, AlreadyLoggedOut {
        bridge.logout("david");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsLogin() throws UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid {
        bridge.login("david", "1111");
    }

    @Test(expected = EmailNotValid.class)
    public void EmailNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "1111", "12323123123@!2", 100);
    }

    @Test(expected = NegativeValue.class)
    public void NegativeValueRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "1111", "noname@gmail.com", -100);
    }

    @Test(expected = UsernameNotValid.class)
    public void UsernameNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("", "1111", "noname@gmail.com", 100);
    }

    @Test(expected = PasswordNotValid.class)
    public void PasswordNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "", "noname@gmail.com", 100);
    }

    @Test(expected = UserAlreadyExists.class)
    public void UserAlreadyExistsRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
    }

    @Test
    public void testFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("slava", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        ServiceUser u1 = bridge.login("moshe", "1111");
        assertEquals(u1.getUsername(), "moshe");
        assertEquals(u1.getPassword(), "1111");
        assertFalse(u1.getUsername().equals(""));
        assertFalse(u1.getPassword().equals(""));
        bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.joinGame(1, "moshe");
        bridge.joinGame(2, "moshe");
        bridge.joinGame(3, "moshe");
        ArrayList<Integer> sGames = bridge.findSpectatableGames("moshe");
        assertTrue(sGames.size() == 3);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findSpectatableGames("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsFindSpectatableGames() throws UserNotLoggedIn, UserNotExists {
        bridge.findSpectatableGames("jkdfandngoineap");
    }

    @Test
    public void testCreateGameAndJoin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        ServiceUser u1 = bridge.login("moshe", "1111");
        ServiceUser u3 = bridge.register("stav", "2222", "yoyo@gmail.com", 100);
        assertEquals(u1.getUsername(), "moshe");
        assertEquals(u1.getPassword(), "1111");
        assertFalse(u1.getUsername().equals(""));
        assertFalse(u1.getPassword().equals(""));
        assertEquals(u3.getUsername(), "stav");
        assertEquals(u3.getPassword(), "2222");
        String name = "moshe";
        int num = bridge.createGame(name, "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.joinGame(num, "stav");
        ArrayList<Integer> sGames = bridge.findSpectatableGames("stav");
       // assertEquals(sGames.size() ,1);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedIncreateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.createGame("moshe", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsCreateGame() throws UserNotLoggedIn, UserNotExists {
        bridge.createGame("moshe1", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInJoinGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.joinGame(22, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsJoinGame() throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin {
        bridge.joinGame(22, "moshe1");
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInSpectateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.spectateGame(22, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsSpectateGame() throws UserNotLoggedIn, UserNotExists {
        bridge.spectateGame(22, "moshe1");
    }

    @Test
    public void testViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        ServiceUser u1 = bridge.login("moshe", "1111");
        assertFalse(u1.getUsername().equals(""));
        assertFalse(u1.getPassword().equals(""));
        String name = "moshe";
        int num = bridge.createGame(name, "NoLimitHoldem", 2, 2, 10, 2, 5, true);
        bridge.terminateGame(num);
        ArrayList<String> sGames = bridge.viewReplay(num, "moshe");
        assertEquals(sGames.size(), 1);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        //bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.viewReplay(22, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsViewReplay() throws UserNotLoggedIn, UserNotExists {
        bridge.viewReplay(22, "moshe1");
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInFindActiveGamesByPotSize() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        // bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByPotSize(100, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsFindActiveGamesByPotSize() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByPotSize(100, "moshe1");
    }


    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInCheck() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.check("moshe", 2122);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsCheck() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney {
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.check("moshe1", 2122);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInBet() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.bet("moshe", 2122, 20);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsBet() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney {
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.bet("moshe1", 2122, 20);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInRaise() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.raise("moshe", 2122, 5);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsRaise() throws UserNotLoggedIn, UserNotExists, NegativeValue, NotAllowedNumHigh, NoMuchMoney {
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.raise("moshe1", 2122, 5);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInAllIn() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.allIn("moshe", 2122);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsAllIn() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney {
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.allIn("moshe1", 2122);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInFold() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.fold("moshe", 2122);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsFold() throws UserNotLoggedIn, UserNotExists, NegativeValue {
        bridge.fold("moshe1", 2122);
    }


 /*   @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInSetDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NotHighestRanking, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.setDefaultLeague("moshe", 1);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsSetDefaultLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
        bridge.setDefaultLeague("moshe1", 1);
    }

    @Test(expected = NotHighestRanking.class)
    public void NegativeValueSetDefaultLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.setDefaultLeague("moshe", -12);
    }

    @Test(expected = NotHighestRanking.class)
    public void UserNotLoggedInSetCriteria() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NotHighestRanking, UsernameAndPasswordNotMatch, AlreadyLoggedIn, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.setCriteria("moshe", 1);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsSetCriteria() throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
        bridge.setCriteria("moshe1", 1);
    }

    @Test(expected = NegativeValue.class)
    public void NegativeValueSetCriteria() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, NotHighestRanking {

        bridge.login("moshe", "1111");
        bridge.setCriteria("moshe", -12);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInMoveToLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague, UsernameAndPasswordNotMatch, AlreadyLoggedIn, AlreadyLoggedOut, NotHighestRanking {

        bridge.login("moshe", "1111");
        bridge.setDefaultLeague("moshe", 1);
        bridge.logout("moshe");
        bridge.moveToLeague("moshe", "moshe", 2);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
        bridge.moveToLeague("moshe", "moshe", 2);
    }

    @Test(expected = NegativeValue.class)
    public void NegativeValueMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.login("moshe", "1111");
        bridge.setDefaultLeague("moshe", 1);
        bridge.moveToLeague("moshe", "moshe", -2);
    }

    @Test(expected = NegativeValue.class)
    public void UserAlreadyInLeagueMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.login("moshe", "1111");
        bridge.setDefaultLeague("moshe", 1);
        bridge.moveToLeague("moshe", "moshe", 1);
    }

*/
     /*  @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInSaveFavoriteTurn() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        // bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.saveFavoriteTurn(22, "moshe", "favoriteTurn1");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsSaveFavoriteTurn() throws UserNotLoggedIn, UserNotExists {
        bridge.saveFavoriteTurn(22, "moshe1", "favoriteTurn1");
    }*/



}
