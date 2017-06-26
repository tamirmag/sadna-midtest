package Acceptance;

import DB.IUsersDB;
import Games.*;
import Loggers.IActionLogger;
import Loggers.IActiveGamesLogManager;
import Loggers.IErrorLogger;
import Loggers.IFinishedGamesManager;
import ServiceLayer.ServiceUser;
import Users.*;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class acctests {
    static Bridge bridge = Driver.getBridge();
    static ServiceUser u;


    @BeforeClass
    public static void configure() {
        IUsersDB.getInstance().changeDataStore("tests");
    }

    @After
    public void afterAnyTest() throws AlreadyLoggedOut, UserNotExists {
        bridge.clearUsers();
        IActionLogger.getInstance().clearLog();
        IErrorLogger.getInstance().clearLog();
        bridge.clearAllFinishedGameLogs();
    }

    @Before
    public void beforeAnyTest() throws AlreadyLoggedOut, UserNotExists {
        IFinishedGamesManager.getInstance().deleteAllFinishedGameLogs();
        IActiveGamesLogManager.getInstance().RemoveAllGameLoggers();
        bridge.clearUsers();
    }

    @AfterClass
    public static void afterAllTests() throws AlreadyLoggedOut, UserNotExists {
        bridge.clearUsers();
        IActionLogger.getInstance().clearLog();
        IErrorLogger.getInstance().clearLog();
        IFinishedGamesManager.getInstance().deleteAllFinishedGameLogs();
        IActiveGamesLogManager.getInstance().RemoveAllGameLoggers();
        bridge.clearAllFinishedGameLogs();
        IUsersDB.getInstance().changeDataStore("systemDatabase");
    }


    /**************register************/
    @Test
    public void successRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        assertEquals(u.getUsername(), "moshe");
        assertEquals(u.getPassword(), "1111");
        assertEquals(u.getEmail(), "noname@gmail.com");
        assertFalse(u.getUsername().equals(""));
        assertFalse(u.getPassword().equals(""));
        assertFalse(u.getEmail().equals(""));
        assertFalse(u.getWallet().getAmountOfMoney() < 0);
    }

    @Test(expected = EmailNotValid.class)
    public void emailNotValidRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "1111", "12323123123@!2", 100);
    }

    @Test(expected = NegativeValue.class)
    public void negativeValueRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "1111", "noname@gmail.com", -100);
    }

    @Test(expected = UsernameNotValid.class)
    public void usernameNotValidRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("", "1111", "noname@gmail.com", 100);
    }

    @Test(expected = PasswordNotValid.class)
    public void passwordNotValidRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("magnez", "", "noname@gmail.com", 100);
    }

    @Test(expected = UserAlreadyExists.class)
    public void userAlreadyExistsRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
    }

    /**************login***********************/
    @Test
    public void loginWithIncorrectPassword() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
        u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        String password = "1111";
        bridge.login("moshe", password);
        assertEquals(u.getPassword(), password);
    }

    @Test
    public void successLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
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
    public void alreadyLoggedInLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.login("moshe", "1111");
    }

    @Test(expected = UsernameNotValid.class)
    public void usernameNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.login("", "1111");
    }

    @Test(expected = PasswordNotValid.class)
    public void passwordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "");
    }

    @Test(expected = UsernameAndPasswordNotMatch.class)
    public void usernamePasswordNotMatchLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "2222");
        System.out.println("im in usernamePasswordNotMatchLogin");
    }

    @Test(expected = UserAlreadyExists.class)
    public void userAlreadyExistsLogin() throws UserAlreadyExists, UsernameNotValid, EmailNotValid, NegativeValue, PasswordNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsLogin() throws UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid {
        bridge.login("david", "1111");
    }

    /***********************logout********************/
    @Test
    public void successLogout() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        String uname = "moshe";
        bridge.logout(uname);
        assertFalse(uname.equals(""));
    }

    @Test(expected = AlreadyLoggedOut.class)
    public void alreadyLoggedOutLogout() throws UserAlreadyExists, UserNotExists, AlreadyLoggedOut, AlreadyLoggedIn, UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, EmailNotValid, NegativeValue {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.logout("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsLogout() throws UserNotExists, AlreadyLoggedOut {
        bridge.logout("david");
    }

    /************spectatableGames**************/
    @Test
    public void testFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("slava", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        int i1 = bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        int i2 = bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        int i3 = bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.joinGame(i1, "moshe");
        bridge.joinGame(i2, "moshe");
        bridge.joinGame(i3, "moshe");
        ArrayList<Integer> sGames = bridge.findSpectatableGames("moshe");
        assertEquals(sGames.size(), 3);
        bridge.terminateGame(i1);
        bridge.terminateGame(i2);
        bridge.terminateGame(i3);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findSpectatableGames("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsFindSpectatableGames() throws UserNotLoggedIn, UserNotExists {
        bridge.findSpectatableGames("jkdfandngoineap");
    }

    /*****************create game*****************/
    @Test
    public void successCreateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.register("stav", "2222", "yoyo@gmail.com", 100);
        String name = "moshe";
        int i = bridge.createGame(name, "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.terminateGame(i);

    }


    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInCreateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int i = bridge.createGame("moshe", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
        bridge.terminateGame(i);
        IErrorLogger.getInstance().writeToFile("in userNotLoggedInCreateGame , game " + i + " was terminated.");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsCreateGame() throws UserNotLoggedIn, UserNotExists {
        int i = bridge.createGame("moshe1", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
        bridge.terminateGame(i);
        IErrorLogger.getInstance().writeToFile("in userNotExistsCreateGame , game " + i + " was terminated.");
    }

    /******************join game**************/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInJoinGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.joinGame(22, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsJoinGame() throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin {
        bridge.joinGame(22, "moshe1");
    }

    @Test
    public void successJoinGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, NoMuchMoney, CantJoin, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        bridge.register("stav", "2222", "yoyo@gmail.com", 100);
        String name = "moshe";
        int num = bridge.createGame(name, "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.joinGame(num, "stav");
        bridge.terminateGame(num);
    }

    /****************spectateGame****************/

    @Test
    public void successSpectateGame() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotLoggedIn, NoMuchMoney, CantJoin, SpectatingNotAllowed {
        //REMEMBER: the real implementation of 'specatateGame' is actually the function 'spectateGame1' in ActiveGamesLogManager.
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "2222", "yoyo@gmail.com", 100);
        bridge.register("david", "2222", "yoyo@gmail.com", 100);
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true); //spectate allowed
        bridge.joinGame(i, "stav");
        bridge.spectateGame(i, "david");
        bridge.terminateGame(i);
    }

    @Test
    public void cannotSpectateGameSpectateGame() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotLoggedIn, NoMuchMoney, CantJoin, SpectatingNotAllowed {
        //REMEMBER: the real implementation of 'specatateGame' is actually the function 'spectateGame1' in ActiveGamesLogManager.
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "2222", "yoyo@gmail.com", 100);
        bridge.register("david", "2222", "yoyo@gmail.com", 100);
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        try {
            bridge.spectateGame(i, "david");
        } catch (SpectatingNotAllowed e) {
            bridge.terminateGame(i);
        }


    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsSpectateGame() throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed {
        bridge.spectateGame(22, "moshe1");
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInSpectateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut, SpectatingNotAllowed {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.spectateGame(22, "moshe");
    }

    /***********view replay***********/
    @Test
    public void successViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        String name = "moshe";
        int num = bridge.createGame(name, "NoLimitHoldem", 2, 2, 10, 2, 5, false);
        bridge.terminateGame(num);
        ArrayList<String> sGames = bridge.viewReplay(num, "moshe");
        assertEquals(sGames.size(), 1);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.viewReplay(22, "moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsViewReplay() throws UserNotLoggedIn, UserNotExists {
        bridge.viewReplay(22, "moshe1");
    }

    /*************find active games by min players**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByMinPlayersPolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByMinPlayersPolicy("moshe", 2);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByMinPlayersPolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByMinPlayersPolicy("moshe1", 2);
    }

    @Test
    public void successFindActiveGamesByMinPlayersPolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, false); //spectate not allowed
        ArrayList<Integer> games = bridge.findActiveGamesByMinPlayersPolicy("stav", 1);
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by max players**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByMaxPlayersPolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByMaxPlayersPolicy("moshe", 2);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByMaxPlayersPolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByMaxPlayersPolicy("moshe1", 2);
    }

    @Test
    public void successFindActiveGamesByMaxPlayersPolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, false); //spectate not allowed
        ArrayList<Integer> games = bridge.findActiveGamesByMaxPlayersPolicy("stav", 5);
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by player name**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByPlayerName() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByPlayerName("moshe", "stav");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByPlayerName() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByMaxPlayersPolicy("moshe1", 2);
    }

    @Test
    public void successFindActiveGamesByPlayerName() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, CantJoin {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        bridge.register("roy", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        ArrayList<Integer> games = bridge.findActiveGamesByPlayerName("roy", "moshe");
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by chip policy**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByChipPolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByChipPolicy("moshe", 1);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByChipPolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByChipPolicy("moshe1", 1);
    }

    @Test
    public void successFindActiveGamesByChipPolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, CantJoin {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        bridge.register("roy", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        ArrayList<Integer> games = bridge.findActiveGamesByChipPolicy("roy", 0);
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by buy-in policy**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByBuyInPolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByBuyInPolicy("moshe", 1);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByBuyInPolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByBuyInPolicy("moshe1", 1);
    }

    @Test
    public void successFindActiveGamesByBuyInPolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, CantJoin {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        bridge.register("roy", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        ArrayList<Integer> games = bridge.findActiveGamesByBuyInPolicy("roy", 0);
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by minimum bet policy**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByMinimumBetPolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByMinimumBetPolicy("moshe", 1);
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByMinimumBetPolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByMinimumBetPolicy("moshe1", 1);
    }

    @Test
    public void successFindActiveGamesByMinimumBetPolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, CantJoin {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        bridge.register("roy", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 2, 1, 6, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        ArrayList<Integer> games = bridge.findActiveGamesByMinimumBetPolicy("roy", 2);
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }

    /*************find active games by game type policy**********/
    @Test(expected = UserNotLoggedIn.class)
    public void userNotLoggedInActiveGamesByGameTypePolicy() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.findActiveGamesByGameTypePolicy("moshe", "NoLimitHoldem");
    }

    @Test(expected = UserNotExists.class)
    public void userNotExistsActiveGamesByGameTypePolicy() throws UserNotLoggedIn, UserNotExists {
        bridge.findActiveGamesByGameTypePolicy("moshe1", "NoLimitHoldem");
    }

    @Test
    public void successFindActiveGamesByGameTypePolicy() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, CantJoin {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        bridge.register("roy", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int i = bridge.createGame("moshe", "LimitHoldem", 0, 0, 2, 1, 6, false); //spectate not allowed
        bridge.joinGame(i, "stav");
        ArrayList<Integer> games = bridge.findActiveGamesByGameTypePolicy("roy", "LimitHoldem");
        ArrayList<Integer> debug = new ArrayList<>();
        debug.add(i);
        assertEquals(games, debug);
        bridge.terminateGame(i);
    }


    /*****************CHECK********/

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInCheck() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut, NotYourTurn {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        try {
            bridge.check("moshe", 2122);
        } catch (UserNotLoggedIn e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void UserNotExistsCheck() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney, NotYourTurn, PasswordNotValid, UsernameNotValid, UserAlreadyExists, EmailNotValid {
        bridge.register("moshe1", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        try {
            bridge.check("moshe2", num);
        } catch (UserNotExists e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void successCheck() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        bridge.terminateGame(num);
    }

    @Test
    public void notYourTurnCheck() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        try {
            bridge.check("moshe", num);
        } catch (NotYourTurn e) {
            bridge.terminateGame(num);
        }
    }

    /********* BET****************/

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInBet() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut, NotYourTurn, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.logout("moshe");
        bridge.bet("moshe", num, 20);
        bridge.terminateGame(num);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsBet() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney, NotYourTurn, NotLegalAmount {
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.bet("moshe1", 2122, 20);
        bridge.terminateGame(num);
    }

    @Test
    public void successBet() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        bridge.bet("moshe", num, 10);
        bridge.terminateGame(num);
    }

    @Test
    public void noMuchMoneyBet() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        try {
            bridge.bet("moshe", num, 1000000);
        } catch (NoMuchMoney e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void negativeValueBet() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        bridge.raise("moshe", num, 15);
        bridge.check("stav", num);
        try {
            bridge.bet("moshe", num, -1000000);
        } catch (NotLegalAmount e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void notYourTurnBet() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        try {
            bridge.bet("stav", num, 10);
        } catch (NotYourTurn e) {
            bridge.terminateGame(num);
        }
    }


    /**************RAISE****************/

    @Test
    public void UserNotLoggedInRaise() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, AlreadyLoggedOut, NotYourTurn, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.logout("moshe");
        try {
            bridge.raise("moshe", 2122, 5);
        } catch (UserNotLoggedIn e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void UserNotExistsRaise() throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotAllowedNumHigh, NotYourTurn, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, CantJoin, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        /*createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode)*/
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 1, 2, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 1);
        bridge.check("stav", num);
        bridge.fold("moshe", num);
        bridge.terminateGame(num);
    }

    @Test
    public void notYourTurnRaise() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        try {
            bridge.raise("stav", num, 10);
        } catch (NotYourTurn e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void negativeValueRaise() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        bridge.raise("moshe", num, 15);
        bridge.check("stav", num);
        try {
            bridge.raise("moshe", num, -1000000);
        } catch (NotLegalAmount e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void successRaise() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        bridge.terminateGame(num);
    }


    /****************All_In*********************/
    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInAllIn() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, AlreadyLoggedOut, NotYourTurn, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 2, 5, true);
        bridge.allIn("moshe", num);
        bridge.terminateGame(num);
    }

    @Test
    public void UserNotExistsAllIn() throws UserNotLoggedIn, UserNotExists, NegativeValue, NoMuchMoney, NotYourTurn, PasswordNotValid, UsernameNotValid, UserAlreadyExists, EmailNotValid, NotLegalAmount {
        bridge.register("moshe1", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe1", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        try {
            bridge.allIn("moshe", num);
        } catch (UserNotExists e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void successAllIn() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.allIn("moshe", num);
        bridge.terminateGame(num);
    }

    @Test
    public void notYourTurnAllIn() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 5);
        bridge.check("stav", num);
        try {
            bridge.allIn("stav", num);
        } catch (NotYourTurn e) {
            bridge.terminateGame(num);
        }
    }

    @Test
    public void negativeValueAllIn() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin, NotYourTurn, NotAllowedNumHigh, NotLegalAmount {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.register("stav", "1111", "noname@gmail.com", 100);
        int num = bridge.createGame("moshe", "NoLimitHoldem", 0, 0, 10, 1, 5, true);
        bridge.joinGame(num, "stav");
        bridge.startGame("moshe", num);
        bridge.raise("moshe", num, 90);
        bridge.check("stav", num);
        try {
            bridge.allIn("moshe", num);
        } catch (NotLegalAmount e) {
            bridge.terminateGame(num);
        }
    }

    /************fold**************/
    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInFold() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut, NotYourTurn {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.fold("moshe", 2122);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsFold() throws UserNotLoggedIn, UserNotExists, NegativeValue, NotYourTurn {
        bridge.fold("moshe1", 2122);
    }

    @Test
    public void Successfold() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotLoggedIn, NoMuchMoney, CantJoin, NotYourTurn, NotLegalAmount, NotAllowedNumHigh {
        bridge.register("slava", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        int i = bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.joinGame(i, "moshe");
        bridge.startGame("slava", i);
        bridge.fold("slava", i);
        bridge.terminateGame(i);
    }

    @Test
    public void foldNotYourTurn() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotLoggedIn, NoMuchMoney, CantJoin, NotYourTurn, NotLegalAmount {
        bridge.register("slava", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.login("moshe", "1111");
        int i = bridge.createGame("slava", "NoLimitHoldem", 0, 0, 1, 1, 5, true);
        bridge.joinGame(i, "moshe");
        bridge.startGame("slava", i);
        try {
            bridge.fold("moshe", i);
        } catch (NotYourTurn e) {
            bridge.terminateGame(i);
        }
    }

    /*****************statistics*********************************/


    /*****************getTop20GrossProfit*********************************/
    @Test
    public void successGetTop20GrossProfit() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);

        ArrayList<String> top = bridge.getTop20GrossProfit("moshe");
        assertEquals(top.size(), 5);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInGetTop20GrossProfit() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.getTop20GrossProfit("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsGetTop20GrossProfit() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.getTop20GrossProfit("moshe");
    }

    /*****************getTop20NumOfGames*********************************/

    @Test
    public void successGetTop20NumOfGames() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);

        ArrayList<String> top = bridge.getTop20NumOfGames("moshe");
        assertEquals(top.size(), 5);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedIngetTop20NumOfGames() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.getTop20NumOfGames("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsgetTop20NumOfGames() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.getTop20NumOfGames("moshe");
    }

    /*************************getTop20highestCashGained*******************/

    @Test
    public void successgetTop20highestCashGained() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);

        ArrayList<String> top = bridge.getTop20highestCashGained("moshe");
        assertEquals(top.size(), 5);
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedIngetTop20highestCashGained() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.getTop20highestCashGained("moshe");
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsgetTop20highestCashGained() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists, AlreadyLoggedOut {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.getTop20highestCashGained("moshe");
    }

    /****************************getUserAverageCashGain****************/

    @Test
    public void successGetUserAverageCashGain() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);

        double ans = bridge.getUserAverageCashGain("moshe" ,"slava1");
        assertTrue(ans == 0);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsGetUserAverageCashGain() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.getUserAverageCashGain("moshe" ,"slava1");
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedInGetUserAverageCashGain() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.getUserAverageCashGain("moshe" ,"slava1");
    }

    @Test(expected = UserNotExists.class)
    public void SearchedUserNotExistsGetUserAverageCashGain() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.getUserAverageCashGain("moshe" ,"slava1");
    }

    /****************************getUserAverageGrossProfit****************/

    @Test
    public void successgetUserAverageGrossProfit() throws PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, UserNotLoggedIn, UserNotExists {
        bridge.register("slava1", "1111", "slava@gmail.com", 200);
        bridge.register("slava2", "1111", "slava@gmail.com", 200);
        bridge.register("slava3", "1111", "slava@gmail.com", 200);
        bridge.register("slava4", "1111", "slava@gmail.com", 200);
        bridge.register("moshe", "1111", "noname@gmail.com", 100);

        double ans = bridge.getUserAverageGrossProfit("moshe" ,"slava1");
        assertTrue(ans == 0);
    }

    @Test(expected = UserNotExists.class)
    public void UserNotExistsgetUserAverageGrossProfit() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.getUserAverageGrossProfit("moshe" ,"slava1");
    }

    @Test(expected = UserNotLoggedIn.class)
    public void UserNotLoggedIngetUserAverageGrossProfit() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.logout("moshe");
        bridge.getUserAverageGrossProfit("moshe" ,"slava1");
    }

    @Test(expected = UserNotExists.class)
    public void SearchedUserNotExistsgetUserAverageGrossProfit() throws UserNotLoggedIn, UserNotExists, PasswordNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, EmailNotValid, AlreadyLoggedOut {
        bridge.register("moshe", "1111", "noname@gmail.com", 100);
        bridge.getUserAverageGrossProfit("moshe" ,"slava1");
    }



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



