package Games;

import Users.NoMuchMoney;
import Users.User;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
public abstract class Policy implements IGame {

    IGame policy;

    @Override
    public void setMinimumBet(int bet) {
        policy.setMinimumBet(bet);
    }

    @Override
    public void setMinimumPlayers(int min) {
        policy.setMinimumPlayers(min);
    }

    @Override
    public void setMaximumPlayers(int max) {
        policy.setMaximumPlayers(max);
    }

    @Override
    public void setChipNum(int num) {
        policy.setChipNum(num);
    }

    @Override
    public int getId() {
        return policy.getId();
    }

    @Override
    public void publishMessage(String msg, Player player) { policy.publishMessage(msg, player); }

    @Override
    public boolean isPlayerInGame(String name) {
        return policy.isPlayerInGame(name);
    }

    @Override
    public void join(Player player) throws NoMuchMoney, CantJoin {
        policy.join(player);
    }

    @Override
    public int getPot() {
        return policy.getPot();
    }

    @Override
    public boolean spectaAble() {
        return policy.spectaAble();
    }

    @Override
    public int getMinimumBet() {
        return this.policy.getMinimumBet();
    }

    @Override
    public String getType() {
        return this.policy.getType();
    }

    @Override
    public ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns) { return this.policy.getAllTurnsOfPlayer(p, allTurns); }

    @Override
    public Hashtable<String, ArrayList<String>> getAllTurnsByAllPlayers() { return this.policy.getAllTurnsByAllPlayers(); }

    @Override
    public void spectateGame(User user) {
        this.policy.spectateGame(user);
    }

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney {
        this.policy.bet(amount, player);
    }

    @Override
    public void call(int amount, Player player) throws NoMuchMoney {
        this.policy.call(amount, player);
    }

    @Override
    public boolean isLocked() {
        return this.policy.isLocked();
    }

    @Override
    public int getPlayersNum() {
        return this.policy.getPlayersNum();
    }

    @Override
    public void startGame() {
        this.policy.startGame();
    }

    @Override
    public int getMaxPlayers() {
        return this.policy.getMaxPlayers();
    }

    @Override
    public int getMinPlayers() {
        return this.policy.getMinPlayers();
    }

    @Override
    public int getChips() {
        return this.policy.getChips();
    }

    @Override
    public int getBuyIn() {
        return this.policy.getBuyIn();
    }

    @Override
    public void allIn(Player player) throws NoMuchMoney { this.policy.allIn(player); }

    @Override
    public int getTurn() {
        return this.policy.getTurn();
    }

    @Override
    public void check(Player player) throws NoMuchMoney {
        this.policy.check(player);
    }

    @Override
    public void leaveGame(Player player, int userID) {
        this.policy.leaveGame(player, userID);
    }

    @Override
    public void terminateGame() {
        this.policy.terminateGame();
    }

    @Override
    public boolean inMax() {
        return this.policy.inMax();
    }

    @Override
    public void endTurn(Player player) {
        this.policy.endTurn(player);
    }

    @Override
    public void endRound() {
        this.policy.endRound();
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney {
        this.policy.raise(amount, player);
    }

    @Override
    public void fold(Player player) {
        this.policy.fold(player);
    }

    @Override
    public void win(Player player) {
        this.policy.win(player);
    }

    @Override
    public void dealCard(Player player) {
        this.policy.dealCard(player);
    }

    @Override
    public Player findPlayer(User usr) {
        return this.policy.findPlayer(usr);
    }

    @Override
    public boolean canJoin(User user) { return this.policy.canJoin(user); }
}
