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

    void check(Player player) throws NoMuchMoney, NotYourTurn;

    void leaveGame(Player player, int userID);

    void allIn(Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

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

    void startGame() throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    boolean inMax();

    String getType();

    Hashtable<String, ArrayList<String>> getAllTurnsByAllPlayers();

    void endTurn(Player player);

    void endRound();

    ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns);

    void spectateGame(User user);

    void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void fold(Player player) throws NotYourTurn;

    void win(Player player);

    void dealCard(Player player);

    void bet(int amount, Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void call(int amount, Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    boolean isLocked();

    Player findPlayer(User usr);

    boolean canJoin(User user) throws NotYourLeague;

    void sendMessage(String from, String to, String data);
}
