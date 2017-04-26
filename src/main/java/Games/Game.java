package Games;
import Users.Wallet;
import Games.Card;
import Games.DeckManager;
import Loggers.GameLogger;
import Users.User;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
public class Game implements IGame {
    public int getId() {
        return id;
    }

    @Override
    public void publishMessage(String msg, Player player) {
        // TODO implement
        //log.addToLog(msg);
    }

    private int id;
    private DeckManager deck = new DeckManager();
    private ArrayList<Player> desk = new ArrayList<Player>(); // players that not yet fold
    private ArrayList<Card> flop = new ArrayList<Card>(); // cards on desk
    private ArrayList<Player> players; //all players in the room
    private int dealerId = 0;
    private int turnId = 0; //number of current playing player
    private int turn = 0; // number of round
    private boolean up = false; // if somones raised in this round
//    private int minimumBet;
    private int currentMinimumBet;
 //   private int maxPlayers;
 //   private int minPlayers;
    private int pot;
    private ArrayList<Integer> playerDesk; //the amount of many that every player put
    private boolean locked = false;
    private String type = "nurmal";
    private GameLogger logger;

    public Game(ArrayList<Player> players, int id, String type) {
        this.players = players;
        currentMinimumBet = 0;
  //      maxPlayers = 0;
        pot = 0;
        this.id = id;
        this.type = type;
        logger = new GameLogger(id);
    }


    public int getPlayersNum() {
        return players.size();
    }

    public boolean inMax(){
        return true;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public boolean join(Player player)
    {
        if(locked)
            return false;
        players.add(player);
        return true;
    }

    public int getMaxPlayers()
    {
        return 0;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    public boolean isPlayerInGame(String name){
        for (Player player: players) {
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }



    public int getTurn(){
        return turn;
    }

    @Override
    public void setMinimumBet(int bet) {
        currentMinimumBet = bet;
  //      minimumBet = bet;
    }


    @Override
    public void setMinimumPlayers(int num) {
        //minPlayers = num;
    }

    @Override
    public void setMaximumPlayers(int num) {
        //maxPlayers = num;
    }

    @Override
    public void setChipNum(int num) {

        for (Player player:players) {
            player.wallet = new Wallet(num);
        }
    }



    public int getMinimumBet(){
        return currentMinimumBet;
    }

    public int getPot(){
        return pot;
    }

    @Override
    public boolean spectaAble() {
        return false;
    }

    public void raise(int amount, Player player) {
        this.currentMinimumBet += amount;
        player.wallet.sub(currentMinimumBet);;
        playerDesk.set(turnId, this.currentMinimumBet);
        endTurn(player);
    }

    public void fold(Player player) {
        if (desk.get(turnId).equals(player)) {
            desk.remove(turnId);
            if(desk.size() == 1){
                win(desk.get(0));
            }
            if (this.turnId == desk.size())
                endRound();

        }
    }

    private void win(Player player) {
        /*
        if (player != null) {
        } else {
        }
        */
    }

    public void dealCard(Player player) {
        Card card = deck.dealCard();
        player.hand.add(card);
    }

    public void bet(int amount, Player player) {
        call(amount,player);
    }

    public void call(int amount, Player player) {
        if (desk.get(turnId).equals(player)) {
            if(amount >= currentMinimumBet) {
                playerDesk.set(turnId, amount);
                player.wallet.setAmountOfMoney(amount);
                currentMinimumBet = amount;
                endTurn(player);
            }
        }
    }

    public void check(Player player) {
        if (desk.get(turnId).equals(player)) {
            playerDesk.set(turnId, currentMinimumBet);
            player.wallet.setAmountOfMoney(currentMinimumBet);
            endTurn(player);
        }
    }

    public void leaveGame(Player player, int userID) {
        desk.remove(player);
        if(desk.size() == 1){
            win(desk.get(0));
        }
        if (this.turnId == desk.size())
            endRound();
        players.remove(player);
    }

    public void allIn(Player player) {
        call(player.wallet.getAmountOfMoney(),player);
    }

    public void terminateGame() {
    }

    public void startGame() {
        locked = true;

    }

    private void endTurn(Player player) {
        if (desk.get(turnId).equals(player)) {
            this.turnId = this.turnId + 1;
            if (this.turnId == desk.size())
                endRound();
        }
    }

    private void endRound() {
        if (up) {
            this.up = false;
            this.turnId = 0;
            for (int i = 0; i < playerDesk.size(); i++) {
                pot += playerDesk.get(i);
                playerDesk.set(i, 0);
            }
        } else {
            turnId = 0;
            switch(turn)
            {
                case 0:
                    turn++;
                    break;
                case 1:
                    flop.add(deck.dealCard());
                    flop.add(deck.dealCard());
                    flop.add(deck.dealCard());
                    turn++;
                    break;
                case 2:
                    flop.add(deck.dealCard());
                    turn++;
                    break;
                case 3:
                    flop.add(deck.dealCard());
                    turn++;
                    win(null);
                    break;
            }
        }
    }

    public ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns)
    {
        for (String s: allTurns) {
            if(!s.contains(p.getName())) allTurns.remove(s);
        }
        return allTurns;
    }

    @Override
    public void spectateGame(User user) {

    }

    public Hashtable<String ,ArrayList<String>> getAllTurnsByAllPlayers()
    {
        Hashtable<String ,ArrayList<String>> ans = new Hashtable<>();
        ArrayList<String> allTurns = logger.getContentOfFile();

        for(Player p:players)
        {
            ArrayList<String> temp = new ArrayList<>();
            for(String a : allTurns) temp.add(a);
            ans.put(p.getName(), getAllTurnsOfPlayer(p, temp));
        }
        return ans;

    }


    @Override
    public int getMinPlayers(){
        return 0;
    }

    @Override
    public int getChips() {
        return 0;
    }

    @Override
    public int getBuyIn() {
        return 0;
    }

}
