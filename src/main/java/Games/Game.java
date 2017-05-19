package Games;

import Users.NoMuchMoney;
import Users.Wallet;
import Loggers.GameLogger;
import Users.User;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
public class Game implements IGame {

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void publishMessage(String msg, Player player) {
        // TODO implement
        //log.addToLog(msg);
    }

    private int id;
    private int legue;
    private DeckManager deck = new DeckManager();
    private ArrayList<Player> desk = new ArrayList<Player>(); // players that not yet fold
    private ArrayList<Card> flop = new ArrayList<Card>(); // cards on desk
    private ArrayList<Player> players; //all players in the room
    private int dealerId = 0;
    private int turnId = 0; //number of current playing player
    private int turn = 0; // number of round
    private boolean up = true; // if somones raised in this round
    //    private int minimumBet;
    private int currentMinimumBet;
    //   private int maxPlayers;
    //   private int minPlayers;
    private int pot;
    private ArrayList<Integer> playerDesk; //the amount of many that every player put
    private boolean locked = false;
    private String type = "nurmal";
    private GameLogger logger;

    public Game(ArrayList<Player> players, int id, String type, int legue) {
        this.players = players;
        currentMinimumBet = 0;
        //      maxPlayers = 0;
        pot = 0;
        this.id = id;
        this.type = type;
        logger = new GameLogger(id);
        this.legue = legue;
    }


    @Override
    public int getPlayersNum() {
        return players.size();
    }

    @Override
    public boolean inMax(){
        return true;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void join(Player player) throws CantJoin {
        if(locked)
            throw new CantJoin(getId(), player.getName());
        players.add(player);

    }

    @Override
    public int getMaxPlayers()
    {
        return 0;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean isPlayerInGame(String name){
        for (Player player: players) {
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }


    @Override
    public int getTurn(){
        return turnId;
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


    @Override
    public int getMinimumBet(){
        return 5;
    }

    @Override
    public int getPot(){
        return pot;
    }

    @Override
    public boolean spectaAble() {
        return false;
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney {
        this.currentMinimumBet += amount;
        player.wallet.sub(currentMinimumBet);;
        playerDesk.set(turnId, this.currentMinimumBet);
        endTurn(player);
    }

    @Override
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

    @Override
    public void win(Player player) {

        if (player != null) {
            player.getWallet().add(pot);
        } else {
            for (Player p :desk) {
                p.hand.addAll(flop);
            }
        }

        pot = 0;

    }

    @Override
    public void dealCard(Player player) {
        Card card = deck.dealCard();
        player.hand.add(card);
    }

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney {
        call(amount,player);
    }

    @Override
    public void call(int amount, Player player) throws NoMuchMoney {
        if (desk.get(turnId).equals(player)) {
            if(amount >= currentMinimumBet) {
                playerDesk.set(turnId, amount);
                player.wallet.sub(amount);
                currentMinimumBet = amount;
                endTurn(player);
            }
        }
    }

    @Override
    public void check(Player player) throws NoMuchMoney {
        if (desk.get(turnId).equals(player)) {
            playerDesk.set(turnId, currentMinimumBet);
            player.wallet.sub(currentMinimumBet);
            endTurn(player);
        }
    }

    @Override
    public void leaveGame(Player player, int userID) {
        desk.remove(player);
        if(desk.size() == 1){
            win(desk.get(0));
        }
        if (this.turnId == desk.size())
            endRound();
        players.remove(player);
    }

    @Override
    public void allIn(Player player) throws NoMuchMoney {
        call(player.wallet.getAmountOfMoney(),player);
    }

    @Override
    public void terminateGame() {
        logger.writeToFile("game ended");
    }

    @Override
    public void startGame() throws NoMuchMoney {
        locked = true;
        playerDesk = new ArrayList<Integer>();
        for (Player p:players) {
            desk.add(p);
            playerDesk.add(0);
        }
        blinedBet();
    }

    private void blinedBet() throws NoMuchMoney {
        Player smallBlind = desk.get(dealerId);
        Player bigBlind = desk.get(dealerId+1);
        call(getMinimumBet()/2, smallBlind);
        call(getMinimumBet(), bigBlind);
    }

    @Override
    public void endTurn(Player player) {
        if (desk.get(turnId).equals(player)) {
            this.turnId = this.turnId + 1;

            if (this.turnId == desk.size()) {
                endRound();
            }
        }
    }

    @Override
    public void endRound() {
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

    @Override
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

    @Override
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

    @Override
    public Player findPlayer(User usr)
    {
        for (Player p:players) {
            if(p.getName().equals(usr.getUsername()))
                return p;
        }
        return null;
    }


    @Override
    public boolean canJoin(User user) {
        return user.getLeague() == legue;
    }

}
