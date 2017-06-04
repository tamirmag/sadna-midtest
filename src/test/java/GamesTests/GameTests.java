package GamesTests;

import Games.ActiveGamesManager;
import Games.CantJoin;
import Games.IGame;
import Games.Preferences;
import Loggers.ActionLogger;
import Users.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tamir2 on 26/04/2017.
 */
public class GameTests {

    ActiveGamesManager man = ActiveGamesManager.getInstance();
    static User roy = new User("roy", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User tamir = new User("tamir", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User nofar = new User("nofar", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User mor = new User("mor", "1235", 1, "rzarviv@gmail.com", new Wallet(100));
    static User yoni = new User("yoni", "1235", 1, "rzarviv@gmail.com", new Wallet(100));


    @Before
    public void inTheBeginningOfEveryTest() throws NegativeValue, UserAlreadyExists, UsernameNotValid, PasswordNotValid, EmailNotValid, AlreadyLoggedOut, UserNotExists, UsernameAndPasswordNotMatch, AlreadyLoggedIn {
    /*    AccountManager.getInstance().register(roy2.getUsername(), roy2.getPassword(), roy2.getEmail(), roy2.getWallet().getAmountOfMoney());
        AccountManager.getInstance().register(tamir2.getUsername(), tamir2.getPassword(), tamir2.getEmail(), tamir2.getWallet().getAmountOfMoney());
        AccountManager.getInstance().register(nofar2.getUsername(), nofar2.getPassword(), nofar2.getEmail(), nofar2.getWallet().getAmountOfMoney());
        AccountManager.getInstance().register(mor2.getUsername(), mor2.getPassword(), mor2.getEmail(), mor2.getWallet().getAmountOfMoney());
        AccountManager.getInstance().register(yoni2.getUsername(), yoni2.getPassword(), yoni2.getEmail(), yoni2.getWallet().getAmountOfMoney());
        roy = AccountManager.getInstance().login(roy2.getUsername(), roy2.getPassword());
        tamir = AccountManager.getInstance().login(tamir2.getUsername(), tamir2.getPassword());
        nofar = AccountManager.getInstance().login(nofar2.getUsername(), nofar2.getPassword());
        mor = AccountManager.getInstance().login(mor2.getUsername(), mor2.getPassword());
        yoni = AccountManager.getInstance().login(yoni2.getUsername(), yoni2.getPassword());*/

    }

    @After
    public void inTheEndOfEveryTest() throws NegativeValue {
        AccountManager.getInstance().clearLoggedInUsers();
        AccountManager.getInstance().clearUsers();
        AccountManager.getInstance().clearLeagues();
        ActionLogger.getInstance().clearLog();
        //AccountManager.getInstance().setDefaultLeague(0);
    }

    @Test
    public void createGameTest() {
        int game  = man.createGame(roy,"NoLimitHoldem", new Preferences());
        assertTrue(game > 0);
        int game2  = man.createGame(roy,"NoLimitHoldem", new Preferences());
        assertTrue(game2 > game);

    }

    @Test
    public void joinGameTest() throws CantJoin, NoMuchMoney {
        int game  = man.createGame(roy,"NoLimitHoldem", new Preferences());
        man.JoinGame(game, tamir);
        List<IGame> games = man.findActiveGamesByPlayer(tamir.getUsername());
        List<Integer> ourGames = new ArrayList<Integer>();
        for (IGame g : games) {
            if (g.isPlayerInGame(tamir.getUsername()))
                ourGames.add(game);
        }
        assertTrue(ourGames.contains(game));
    }

}
