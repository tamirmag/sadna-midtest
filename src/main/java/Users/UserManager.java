package Users;

import Games.*;
import Loggers.ActionLogger;
import Loggers.ActiveGamesLogManager;
import Loggers.FinishedGamesManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;


public class UserManager implements IUserManager {


    private User user;

    public UserManager(User one) {
        user = one;
    }

   /* public UserManager(String username, String password, int league, String email, Wallet wallet) {
        user = new User(username, password, league, email, wallet);
    }*/

    @Override
    public void logout() throws UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().logout(user);
    }

    @Override
    public void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists {
        editUsername(username);
        editEmail(email);
        editPassword(password);

    }

    private void editUsername(String username) throws UsernameNotValid, UserAlreadyExists {
        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if ((!user.getUsername().equals(username)) &&
                AccountManager.getInstance().isUserExists(username)) throw new UserAlreadyExists(username);
        else {
            String prior = user.getUsername();
            AccountManager.getInstance().setUsername(user, username);
            user.setUsername(username);
            ActionLogger.getInstance().writeToFile(prior + " successfully changed his username to " + username);
            System.out.println("username successfully changed.");
        }
    }

    private void editEmail(String email) throws EmailNotValid {
        if (AccountManager.getInstance().isValidEmail(email)) {
            String prior = user.getUsername();
            AccountManager.getInstance().setEmail(user, email);
            user.setEmail(email);
            ActionLogger.getInstance().writeToFile(prior + " successfully changed his email to " + email);
            System.out.println("email successfully changed.");
        } else throw new EmailNotValid(email == null ? "null" : email);
    }

    private void editPassword(String password) throws PasswordNotValid {
        if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null ? "null" : password);
        else {
            String prior = user.getUsername();
            AccountManager.getInstance().setPassword(user, password);
            user.setPassword(password);
            ActionLogger.getInstance().writeToFile(prior + " successfully changed his username to " + password);
        }
    }

    @Override
    public void addPlayer(Player p) {
        user.getExistingPlayers().add(p);
    }

  /*  @Override
    public void addFavoriteTurn(String turn) {
        user.getFavoriteTurns().add(turn);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " added a new favorite turn");
    }*/

    @Override
    public ArrayList<String> viewReplay(int gameNumber) {
        ArrayList<String> replay = FinishedGamesManager.getInstance().viewReplay(gameNumber);
        return replay;
        /*for (String s : replay)
            for (char c : s.toCharArray()) {
                user.getCharToPrint(c);
            }*/
    }

    @Override
    public void spectateGame(int gameNumber) {
        /*TODO :finish implementing this function*/
        ActiveGamesLogManager.getInstance().spectateGame(gameNumber, user);
    }

    @Override
    public int CreateGame( Preferences pref) {
        int i = ActiveGamesManager.getInstance().createGame(this.user, pref);
        Player p = new Player(this.user.getUsername(), this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " created a new game");
        return i;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByPlayerName(String playerName) {
        return new ArrayList<>(ActiveGamesManager.getInstance().findActiveGamesByPlayer(playerName));
    }

    @Override
    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {
        return ActiveGamesManager.getInstance().findActiveGamesByPotSize(potSize);
    }

    @Override
    public ArrayList<IGame> findActiveGamesBySpectatingPolicy(boolean spectatingAllowed) {
        return new ArrayList<>(ActiveGamesManager.getInstance().findSpectatableGames(user));
    }

    @Override
    public ArrayList<IGame> findActiveGamesByMinPlayersPolicy(int minimal) {
        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimal);
    }

    @Override
    public ArrayList<IGame> findActiveGamesByMaxPlayersPolicy(int maximal) {
        return ActiveGamesManager.getInstance().findActiveGamesByPlayersMaximumPolicy(maximal);
    }

    @Override
    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {
        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimumBet);
    }

    @Override
    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {
        return ActiveGamesManager.getInstance().findActiveGamesByChipPolicy(numOfChips);
    }

    @Override
    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {
        return ActiveGamesManager.getInstance().findActiveGamesByBuyInPolicy(costOfJoin);
    }

    @Override
    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {
        return ActiveGamesManager.getInstance().findActiveGamesByGameTypePolicy(gameTypePolicy);
    }

    @Override
    public User getUser() {
        return user;
    }
    @Override
    public void JoinGame(int gameNumber) throws NoMuchMoney, CantJoin {
        ActiveGamesManager.getInstance().JoinGame(gameNumber, this.user);
        Player p = new Player(this.user.getUsername(), this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " joined to game " + gameNumber);
    }

    //Game actions
    @Override
    public void check(int gameID) throws NoMuchMoney, NotYourTurn {
        ActiveGamesManager.getInstance().check(gameID, user);
    }

    @Override
    public void bet(int gameID, int amount) throws NoMuchMoney, NotYourTurn {
        ActiveGamesManager.getInstance().bet(gameID, amount, user);
    }

    @Override
    public void allIn(int gameID) throws NoMuchMoney, NotYourTurn {
        ActiveGamesManager.getInstance().allIn(gameID, user);
    }

    @Override
    public void fold(int gameID) throws NotYourTurn {
        ActiveGamesManager.getInstance().fold(gameID, user);
    }

    @Override
    public void raise(int gameID, int amount) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn {
        ActiveGamesManager.getInstance().raise(gameID, amount, user);
    }

    @Override
    public void startGame(int gameID) throws NotYourTurn, NoMuchMoney {
        IActiveGamesManager.getInstance().startGame(gameID);
    }


    //highest ranking users operations

 /*   @Override
    public void moveUserToLeague(String username, int toLeague) throws UserAlreadyInLeague, NegativeValue, UserNotInLeague, LeagueNotExists, UserNotExists, NotHighestRanking {
        if(!user.isHighestRanking()) throw new NotHighestRanking(user.getUsername());
        if (toLeague < 0) throw new NegativeValue(toLeague);
        AccountManager.getInstance().moveUserToLeague(username,toLeague);

    }

    @Override
    public void setCriteria() throws NotImplementedException, NotHighestRanking {
        if(!user.isHighestRanking()) throw new NotHighestRanking(user.getUsername());
        throw new NotImplementedException();
    }

    @Override
    public void setDefaultLeague(int defaultLeague) throws NegativeValue, NotHighestRanking {
        if(!user.isHighestRanking()) throw new NotHighestRanking(user.getUsername());
        int formerDefaultLeague = AccountManager.getInstance().getDefaultLeague();
        AccountManager.getInstance().setDefaultLeague(defaultLeague);
        System.out.println("default league changed to " + defaultLeague + " and all users from " + formerDefaultLeague + " moved to it.");
        ActionLogger.getInstance().writeToFile("default league changed to " + defaultLeague + " by " + user.getUsername() + " and all users from " + formerDefaultLeague + " moved to it.");
    }*/
}


