package Users;

import Games.IGame;
import Games.Player;
import Games.Preferences;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public interface IUserManager {
    void logout() throws UserNotExists, AlreadyLoggedOut;

    void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists;

    void addPlayer(Player p);

    void addFavoriteTurn(String turn);

    void viewReplay(int gameNumber);

    void spectateGame(int gameNumber);

    void CreateGame(String type, Preferences pref);

    ArrayList<IGame> findActiveGamesByPlayerName(String playerName);

    ArrayList<IGame> findActiveGamesByPotSize(int potSize);

    ArrayList<IGame> findActiveGamesBySpectatingPolicy(boolean spectatingAllowed);

    ArrayList<IGame> findActiveGamesByMinPlayersPolicy(int minimal);

    ArrayList<IGame> findActiveGamesByMaxPlayersPolicy(int maximal);

    ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet);

    ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips);

    ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin);

    ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy);

    User getUser();

    void JoinGame(int gameNumber);

    void moveUserToLeague(String username, int toLeague)
            throws UserAlreadyInLeague, NegativeValue, UserNotInLeague, LeagueNotExists, UserNotExists;

    void setCriteria() throws NotImplementedException;

    void setDefaultLeague(int defaultLeague) throws NegativeValue;

    //Game actions
    void check(int gameID);

    void bet(int gameID, int amount);

    void allIn(int gameID);

    void fold(int gameID);

    void raise(int gameID, int amount);
}
