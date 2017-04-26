package Games;

import Users.User;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
public interface IGame {
    void setMinimumBet(int bet);
    int getMinimumBet();

    void setMinimumPlayers(int num);

    void setMaximumPlayers(int num);

    void setChipNum(int num);

    int getMaxPlayers();

    int getMinPlayers();

    int getChips();

    int getBuyIn();

    int getId();

    void publishMessage(String msg, Player player);

    boolean isPlayerInGame(String name);

    boolean join(Player player);

    int getPot();

    boolean spectaAble();



    int getPlayersNum();

    void startGame();


    String getType();

    Hashtable<String ,ArrayList<String>> getAllTurnsByAllPlayers();

    ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns);

    void spectateGame(User user);

    void bet(int amount, Player player);

    void call(int amount, Player player);

    boolean isLocked();
}
