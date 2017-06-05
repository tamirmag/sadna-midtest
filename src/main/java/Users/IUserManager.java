package Users;

import Games.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public interface IUserManager {
    void logout() throws UserNotExists, AlreadyLoggedOut;

    void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists;

    void addPlayer(Player p);

    //void addFavoriteTurn(String turn);

    ArrayList<String> viewReplay(int gameNumber);

    void spectateGame(int gameNumber);

    int CreateGame( Preferences pref);

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

    void JoinGame(int gameNumber) throws  CantJoin, NoMuchMoney;

   // void moveUserToLeague(String username, int toLeague)
     //       throws UserAlreadyInLeague, NegativeValue, UserNotInLeague, LeagueNotExists, UserNotExists, NotHighestRanking;

    //void setCriteria() throws NotImplementedException, NotHighestRanking;

   // void setDefaultLeague(int defaultLeague) throws NegativeValue, NotHighestRanking;

    //Game actions
    void check(int gameID) throws NoMuchMoney, NotYourTurn;

    void bet(int gameID, int amount) throws NoMuchMoney, NotYourTurn;

    void allIn(int gameID) throws NoMuchMoney, NotYourTurn;

    void fold(int gameID) throws NotYourTurn;

    void raise(int gameID, int amount) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn;

    void startGame(int gameID) throws NotYourTurn, NoMuchMoney;

}
