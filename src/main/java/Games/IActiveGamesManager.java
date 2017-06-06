package Games;


import Users.NoMuchMoney;
import Users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tamir on 28/04/2017.
 */
public interface IActiveGamesManager {
    static ActiveGamesManager getInstance(){ return ActiveGamesManager.getInstance(); }

    void publishMessage(String msg, int gameNumber, Player player);

    int createGame(User user, Preferences pref);

    void startGame(int id) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void raise(int id, int amount, User usr) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void fold(int id, User usr) throws NotYourTurn;

    void allIn(int id, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void check(int id, User usr) throws NoMuchMoney, NotYourTurn;

    void bet(int id, int amount, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void JoinGame(int id, User user) throws NoMuchMoney, CantJoin;

    void spectateGame(int id, User user) throws SpectatingNotAllowed;

    List<IGame> findAllActiveGames(User user) throws NotYourLeague;

    List<IGame> findActiveGamesByPlayer(String name);

    void logout(String name);

    ArrayList<IGame> findActiveGamesByPotSize(int potSize);

    List<IGame> findSpectatableGames(User user);

    ArrayList<IGame> findActiveGamesByPlayersMinimumPolicy(int minimal);

    ArrayList<IGame> findActiveGamesByPlayersMaximumPolicy(int maximal);

    ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet);

    ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips);

    ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin);

    ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy);

    void leaveGame(int id, User usr, int userID);

    void publishMessage(int id, String msg, Player player);

    void terminateGame(int id);

    void call(int id, int amount, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void sendMessage(int id, String from, String to, String data);
}
