package ServiceLayer;

import Games.IGame;
import Games.Preferences;
import Loggers.ActiveGamesLogManager;
import Users.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class ServiceImpl implements ServiceInterface {

    //done
    public String register(String name, String password, String email, int wallet) {
        try {
            IAccountManager.getInstance().register(name, password, email, 0);
        } catch (UserAlreadyExists userAlreadyExists) {
            return "UserTests already exists";
        } catch (PasswordNotValid passwordNotValid) {
            return "Password not valid";
        } catch (EmailNotValid emailNotValid) {
            return "email not valid";
        } catch (NegativeValue negativeValue) {
            return "Negative value";
        } catch (UsernameNotValid usernameNotValid) {
            return "username not valid";
        }
        return "Register Succeeded";
    }

    //done
    public String login(String name, String password) {

        try {
            IAccountManager.getInstance().login(name, password);
        } catch (UsernameNotValid usernameNotValid) {
            return "username not valid";
        } catch (PasswordNotValid passwordNotValid) {
            return "password not valid";
        } catch (UsernameAndPasswordNotMatch usernameAndPasswordNotMatch) {
            return "username and password does not match";
        } catch (AlreadyLoggedIn alreadyLoggedIn) {
            return "user already logged in";
        } catch (UserNotExists userNotExists) {
            return "Name doesn't exist";
        }
        return "login succeeded";
    }

    //done
    public String logout(String name) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(name));
        if (u.getUser() == null) return "user not exists";
        try {
            IAccountManager.getInstance().logout(u.getUser());
        } catch (AlreadyLoggedOut alreadyLoggedOut) {
            return "user already logged out";
        } catch (UserNotExists userNotExists) {
            return "user not exists";
        }
        return "Logout Succeeded";
    }

    //done
    public String createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy,
                             int minimumBet, int minimalAmountOfPlayers,
                             int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        Preferences p = new Preferences();
        if (gameType.equals("")) return "No game type was selected";
        if (BuyInPolicy < 0) return "negative value of buy-in policy";
        else {
            p.setBuyInPolicy(BuyInPolicy);
        }
        if (ChipPolicy < 0) return "negative value of chip policy";
        else {
            p.setChipPolicy(ChipPolicy);
        }
        if (minimumBet < 0) return "negative value of minimum bet";
        else {
            p.setMinBetPolicy(minimumBet);
        }
        if (minimalAmountOfPlayers < 0)
            return "negative value of minimal amount of players";
        else if (maximalAmountOfPlayers < 0)
            return "negative value of maximal amount of players";
        else if (maximalAmountOfPlayers < minimalAmountOfPlayers)
            return "maximal amount id bigger than minimal amount";
        else {
            p.setMinAmountPolicy(minimalAmountOfPlayers);
            p.setMaxAmountPolicy(maximalAmountOfPlayers);
        }
        p.setSpectatePolicy(spectatingMode);
        u.CreateGame(gameType, p);
        return "Game Creation Succeeded";
    }

    //done
    public String joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        if (gamenum < 0) return "negative value of game number";
        u.JoinGame(gamenum);
        return "Joining Game Succeeded";
        //return "";
    }

    //done
    public String spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        ActiveGamesLogManager.getInstance().spectateGame(gamenum, u.getUser());
        return "spectate succeeded";
    }

    //done
    public String viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        u.viewReplay(gamenum);
        return "game replay viewed successfully";
    }

    //done
    public String saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        u.addFavoriteTurn(turn);
        return "turn was successfully added";

    }

    //done
    public String findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        ArrayList<IGame> games = u.findActiveGamesByPotSize(potSize);
        if (games.size() > 0) return "games were found";
        else return "no games were found";
    }

    //done
    public String setDefaultLeague(int league, User u) {
        try {
            IUserManager u1 = new UserManager(u);
            u1.setDefaultLeague(league);
        } catch (NegativeValue negativeValue) {
            return "negative value was inserted";
        }
        return "default league was changed";
    }

    //done
    public String setCriteria(int criteria, User u) {
        try {
            IUserManager u1 = new UserManager(u);
            u1.setCriteria();
        } catch (NotImplementedException ex) {
            return "criteria was set";
        }
        return "criteria was set";
    }

    //done
    public String moveToLeague(String username, User hu, int league) {

        try {
            IUserManager u1 = new UserManager(hu);
            u1.moveUserToLeague(username, league);
        } catch (UserAlreadyInLeague userAlreadyInLeague) {
            return "user already in league";
        } catch (NegativeValue negativeValue) {
            return "league number is negative";
        } catch (UserNotInLeague userNotInLeague) {
            return "user not in league";
        } catch (LeagueNotExists leagueNotExists) {
            return "league not exists";
        } catch (UserNotExists userNotExists) {
            userNotExists.printStackTrace();
        }
        return "user moved successfully";
    }

    //done
    public String findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        if (u.getUser() == null) return "user not exists";
        ArrayList<IGame> games = u.findActiveGamesBySpectatingPolicy(true);
        return "found some games to spectate";
    }
}
