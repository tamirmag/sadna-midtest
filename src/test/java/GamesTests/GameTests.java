package GamesTests;

import DB.IUsersDB;
import Games.*;
import Loggers.ActionLogger;
import Users.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tamir2 on 26/04/2017.
 */
public class GameTests {

    ActiveGamesManager man;
    static User roy;
    static User tamir;
    static User nofar;
    static User mor;
    static User yoni;
    Preferences p;



    @Before
    public void inTheBeginningOfEveryTest() throws NegativeValue, UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
        man = ActiveGamesManager.getInstance();
        roy = new User(1,"roy", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
        tamir = new User(2,"tamir", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
        nofar = new User(3,"nofar", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
        mor = new User(4,"mor", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
        yoni = new User(5,"yoni", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
        p = new Preferences();
    }

    @After
    public void inTheEndOfEveryTest() throws NegativeValue {
        AccountManager.getInstance().clearLoggedInUsers();
        AccountManager.getInstance().clearUsers();
        AccountManager.getInstance().clearLeagues();
        ActionLogger.getInstance().clearLog();
    }

    @AfterClass
    public static void reconfigure()
    {
        IUsersDB.getInstance().changeDataStore("systemDatabase");
    }

    @BeforeClass
    public static void configure()
    {
        IUsersDB.getInstance().changeDataStore("tests");
    }

    @Test
    public void createGameTest() {
        p.setNoLimitHoldem(true);
        int game  = man.createGame(roy, p);
        assertTrue(game > 0);
        int game2  = man.createGame(roy, p);
        assertTrue(game2 > game);

    }

    @Test
    public void joinGameTest() throws CantJoin, NoMuchMoney {
        p.setNoLimitHoldem(true);
        int game  = man.createGame(roy, p);
        man.JoinGame(game, tamir);
        List<IGame> games = man.findActiveGamesByPlayer(tamir.getUsername());
        List<Integer> ourGames = new ArrayList<Integer>();
        for (IGame g : games) {
            if (g.isPlayerInGame(tamir.getUsername()))
                ourGames.add(game);
        }
        assertTrue(ourGames.contains(game));
    }

    @Test
    public void startGameTest() throws CantJoin, NoMuchMoney, NotYourTurn, NotLegalAmount {
        p.setNoLimitHoldem(true);
        int game  = man.createGame(roy, p);
        man.JoinGame(game, tamir);
        man.startGame(game);
        assertTrue(man.isLocked(game));
    }

    @Test
    public void foldGameTest() throws CantJoin, NoMuchMoney, NotYourTurn, NotLegalAmount {
        p.setNoLimitHoldem(true);
        int game  = man.createGame(roy, p);
        man.JoinGame(game, tamir);
        man.startGame(game);
        man.fold(game, roy);
        assertTrue(tamir.getWallet().getAmountOfMoney() == 100+man.getMinimumBet(game)/2);
    }

    @Test
    public void roytest(){

    }

}
