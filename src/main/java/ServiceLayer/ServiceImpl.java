package ServiceLayer;


import Games.ActiveGamesManager;
import Games.IGame;
import Games.Preferences;
import Loggers.ActiveGamesLogManager;
import Users.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class ServiceImpl implements ServiceInterface{
    public String register(String name,String password,String email,int wallet){
        try {
            AccountManager.getInstance().register(name,password,email,0);
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

    public String login(String name,String password){

        try {
            AccountManager.getInstance().login(name,password);
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

    public String logout(String name){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(name));
        if(u.getUser() == null) return "user not exists";
        try {
            AccountManager.getInstance().logout(u.getUser());
        } catch (AlreadyLoggedOut alreadyLoggedOut) {
            return "user already logged out";
        } catch (UserNotExists userNotExists) {
            return "user not exists";
        }
        return "Logout Succeeded";
    }


    public String createGame(String username,String gameType,int BuyInPolicy,int ChipPolicy,
                             int minimumBet,int minimalAmountOfPlayers,
                             int maximalAmountOfPlayers,boolean spectatingMode){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if (u.getUser() == null) return "user not exists";
        Preferences p = new Preferences();
        if(gameType.equals("")) return "No game type was selected";
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
        u.CreateGame(gameType ,p);
        return "Game Creation Succeeded";
    }

    public String joinGame(int gamenum,String username){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        if(gamenum < 0 ) return "negative value of game number";
        u.JoinGame(gamenum);
        return "Joining Game Succeeded";
        //return "";
    }

    public String spectateGame(int gamenum,String username){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        ActiveGamesLogManager.getInstance().spectateGame(gamenum,u.getUser());
        return "spectate succeeded";
    }

    public String viewReplay(int gamenum,String username){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        u.viewReplay(gamenum);
        return "game replay viewed successfully";
    }

    public String saveFavoriteTurn(int gamenum,String username,String turn){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        u.addFavoriteTurn(turn);
        return "turn was successfully added";

    }

    public String findActiveGamesByPotSize(int potSize,String username){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        ArrayList<IGame> games = u.findActiveGamesByPotSize(potSize);
        if(games.size() >0) return "games were found";
        else return "no games were found";
    }

    public String setDefaultLeague(int league ,HighestRankingUser u){
        try {
            u.setDefaultLeague(league);
        } catch (NegativeValue negativeValue) {
            return "negative value was inserted";
        }
        return "default league was changed";
    }

    public String setCriteria(int criteria, HighestRankingUser u){
        try
        {
            u.setCriteria();
        }
        catch (NotImplementedException ex)
        {
            return "criteria was set";
        }
        return "criteria was set";
    }

    public String moveToLeague(String username , HighestRankingUser hu ,int league){

        try {
            hu.moveUserToLeague(username ,league);
        } catch (UserAlreadyInLeague userAlreadyInLeague) {
            return "user already in league";
        } catch (NegativeValue negativeValue) {
            return "league number is negative";
        } catch (UserNotInLeague userNotInLeague) {
            return "user not in league";
        } catch (LeagueNotExists leagueNotExists) {
            return "league not exists";
        }
        return "user moved successfully";
    }

    public String findSpectatableGames(String username){
        UserManager u = new UserManager(AccountManager.getInstance().getUser(username));
        if(u.getUser() == null) return "user not exists";
        ArrayList<IGame> games = u.findActiveGamesBySpectatingPolicy(true);
        return "found some games to spectate";
    }
}
