package Users;

import Games.ActiveGamesManager;
import Games.IGame;
import Games.Player;
import Games.Preferences;
import Loggers.ActionLogger;
import Loggers.ActiveGamesLogManager;
import Loggers.FinishedGamesManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;


public class UserManager {


    private User user;

    public UserManager(User one) {
        user = one;
    }

    public void logout() throws UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().logout(user);
    }

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
            System.out.println("password successfully changed.");
            ActionLogger.getInstance().writeToFile(prior + " successfully changed his username to " + password);
        }

    }

    public void addPlayer(Player p) {
        user.getExistingPlayers().add(p);
    }

    public void addFavoriteTurn(String turn) {
        user.getFavoriteTurns().add(turn);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " added a new favorite turn");
    }

    public void viewReplay(int gameNumber) {

        ArrayList<String> replay = FinishedGamesManager.getInstance().viewReplay(gameNumber);
        for (String s : replay)
            for (char c : s.toCharArray()) {
                user.getCharToPrint(c);
            }
    }

    public void spectateGame(int gameNumber) {
        ActiveGamesLogManager.getInstance().spectateGame(gameNumber, user);
    }

    public void CreateGame(String type, Preferences pref) {
        ActiveGamesManager.getInstance().createGame(this.user, type, pref);
        Player p = new Player(this.user.getUsername(), this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " created a new game");
    }


    public ArrayList<IGame> findActiveGamesByPlayerName(String playerName) {

        return new ArrayList<>(ActiveGamesManager.getInstance().findActiveGamesByPlayer(playerName));

    }


    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {

        return ActiveGamesManager.getInstance().findActiveGamesByPotSize(potSize);

    }


    public ArrayList<IGame> findActiveGamesBySpectatingPolicy(boolean spectatingAllowed) {

        return new ArrayList<>(ActiveGamesManager.getInstance().findSpectatableGames(user));
    }


    public ArrayList<IGame> findActiveGamesByMinPlayersPolicy(int minimal) {

        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimal);
    }

    public ArrayList<IGame> findActiveGamesByMaxPlayersPolicy(int maximal) {
        return ActiveGamesManager.getInstance().findActiveGamesByPlayersMaximumPolicy(maximal);
    }


    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {

        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimumBet);
    }


    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {

        return ActiveGamesManager.getInstance().findActiveGamesByChipPolicy(numOfChips);

    }


    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {

        return ActiveGamesManager.getInstance().findActiveGamesByBuyInPolicy(costOfJoin);

    }


    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {

        return ActiveGamesManager.getInstance().findActiveGamesByGameTypePolicy(gameTypePolicy);
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public void JoinGame(int gameNumber) {
        ActiveGamesManager.getInstance().JoinGame(gameNumber, this.user);
        Player p = new Player(this.user.getUsername(), this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername() + " joined to game " + gameNumber);
    }

    public void moveUserToLeague(String username, int toLeague)
            throws UserAlreadyInLeague, NegativeValue, UserNotInLeague, LeagueNotExists {

        if (toLeague < 0) throw new NegativeValue(toLeague);
        User u = AccountManager.getInstance().getUser(username);
        int formerLeague = AccountManager.getInstance().getUser(username).getLeague();
        if (formerLeague == toLeague) throw new UserAlreadyInLeague(username, toLeague);
        AccountManager.getInstance().removeUserFromLeague(u);
        AccountManager.getInstance().getUser(username).setLeague(toLeague);
        AccountManager.getInstance().addUserToLeague(u);
        System.out.println("Users " + u.getUsername() + " moved from league " + formerLeague + " to league " + toLeague);
        ActionLogger.getInstance().writeToFile("Users " + u.getUsername() + " moved from league " + formerLeague + " to league " + toLeague);
    }

    public void setCriteria() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void setDefaultLeague(int defaultLeague) throws NegativeValue {
        int formerDefaultLeague = AccountManager.getInstance().getDefaultLeague();
        AccountManager.getInstance().setDefaultLeague(defaultLeague);
        System.out.println("default league changed to " + defaultLeague + " and all users from " + formerDefaultLeague + " moved to it.");
        ActionLogger.getInstance().writeToFile("default league changed to " + defaultLeague + " by " + user.getUsername() + " and all users from " + formerDefaultLeague + " moved to it.");

    }
}


