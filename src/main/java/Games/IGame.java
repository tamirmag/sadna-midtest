package Games;

import Users.NoMuchMoney;
import Users.User;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
public interface IGame {
    int getTurn();

    void setMinimumBet(int bet);

    int getMinimumBet();

    void setMinimumPlayers(int num);

    void check(Player player) throws NoMuchMoney;

    void leaveGame(Player player, int userID);

    void allIn(Player player) throws NoMuchMoney;

    void setMaximumPlayers(int num);

    void setChipNum(int num);

    int getMaxPlayers();

    int getMinPlayers();

    int getChips();

    int getBuyIn();

    int getId();

    void publishMessage(String msg, Player player);

    boolean isPlayerInGame(String name);

    void join(Player player) throws NoMuchMoney, CantJoin;

    int getPot();

    boolean spectaAble();

    int getPlayersNum();

    void terminateGame();

    void startGame();

    boolean inMax();

    String getType();

    Hashtable<String, ArrayList<String>> getAllTurnsByAllPlayers();

    void endTurn(Player player);

    void endRound();

    ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns);

    void spectateGame(User user);

    void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney;

    void fold(Player player);

    void win(Player player);

    void dealCard(Player player);

    void bet(int amount, Player player) throws NoMuchMoney;

    void call(int amount, Player player) throws NoMuchMoney;

    boolean isLocked();

    Player findPlayer(User usr);

    boolean canJoin(User user);

}
