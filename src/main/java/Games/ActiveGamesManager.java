package Games;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import Users.User;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class ActiveGamesManager
{
    private static ActiveGamesManager instance = null;
    ArrayList<IGame> games = new ArrayList();
    int index = 0;

    private ActiveGamesManager()
    {

    }

    public static ActiveGamesManager getInstance()
    {
        if(instance == null) {
            instance = new ActiveGamesManager();
        }
        return instance;
    }
    public void publishMessage(String msg, int gameNumber, Player player) {
        for (IGame game : games) {
            if (game.getId() == gameNumber) {
                game.publishMessage(msg, player);
            }
        }
    }

    public int createGame(User user, String type, Preferences pref) {
        ArrayList<Player> players = new ArrayList<Player>();
        Player p = new Player(user.getUsername(), user.getWallet());
        p.wallet = user.getWallet();
        p.name = user.getUsername();
        players.add(p);
        IGame game = null;
        switch (type)
        {
            case "NoLimitHoldem":
                game = new NoLimitHoldem(players, ++index, user.getLeague());
                break;

            case "LimitHoldem":
                game = new LimitHoldem(players, ++index, user.getLeague());
                break;

            case "PotLimitHoldem":
                game = new PotLimitHoldem(players, ++index, user.getLeague());
                break;

            default:
                game = new Game(players, ++index, "Normal", user.getLeague());
        }

        game = buildByPref(pref, game);
        games.add(game);
        return  index;
    }

    private IGame buildByPref(Preferences pref, IGame game) {
        if(pref.getBuyInPolicy() > 0)
            game = new BuyInPolicy(game, pref.getBuyInPolicy());
        if(pref.getChipPolicy() > 0)
            game = new ChipPolicy(game, pref.getChipPolicy());
        if(pref.getMaxAmountPolicy() > 0)
            game = new MaxAmountPolicy(game, pref.getMaxAmountPolicy());
        if(pref.getMinAmountPolicy() > 0)
            game = new MinAmountPolicy(game, pref.getMinAmountPolicy());
        if(pref.getMinBetPolicy() > 0)
            game = new MinBetPolicy(game, pref.getMinBetPolicy());
        if(pref.isSpectatePolicy())
            game = new SpectatePolicy(game, pref.isSpectatePolicy());


        return game;
    }

    private IGame find(int id){
        IGame myGame=null;
        for (IGame game:games ) {
            if(game.getId() == id)
                myGame = game;
        }
        return myGame;
    }

    public void startGame(int id){
        IGame myGame = find(id);
        myGame.startGame();
    }

    public void raise(int id, int amount, User usr)
    {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.raise(amount,p);
    }

    public int getMinimumBet(int id){
        IGame myGame = find(id);
        return myGame.getMinimumBet();
    }

    public void fold(int id, User usr){
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.fold(p);
    }

    public boolean isPlayerInGame(int id, String name){
        IGame myGame = find(id);
        return myGame.isPlayerInGame(name);
    }

    public void allIn(int id,User usr){
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.allIn(p);
    }

    public int getPlayersNum(int id)
    {
        IGame myGame = find(id);
        return myGame.getPlayersNum();
    }

    public void check(int id,User usr){
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.check(p);
    }

    public void bet(int id,int amount, User usr){
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.bet(amount, p);
    }

    public int getTurn(int id){
        IGame myGame = find(id);
        return myGame.getTurn();
    }


    public void JoinGame(int id, User user) {
        IGame myGame=null;
        for (IGame game:games ) {
            if(game.getId() == id)
                myGame = game;
        }
        Player p = new Player(user.getUsername(), user.getWallet());
        myGame.join(p);
    }

    public void spectateGame(IGame game, User user) {
        game.spectateGame(user);
    }

    public List<IGame> findAllActiveGames(User user) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.canJoin(user))
                ourGames.add(game);
        }
        return ourGames;
    }

    public List<IGame> findActiveGamesByPlayer(String name) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.isPlayerInGame(name))
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {

        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getPot() == potSize)
                ourGames.add(game);
        }
        return ourGames;
    }


    public List<IGame> findSpectatableGames(User user) {

        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.spectaAble())
                ourGames.add(game);
        }
        return ourGames;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<IGame> findActiveGamesByPlayersMinimumPolicy(int minimal) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getMinPlayers() == minimal)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByPlayersMaximumPolicy(int maximal) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getMaxPlayers() == maximal)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getMinimumBet() == minimumBet)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getChips() == numOfChips)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getBuyIn() == costOfJoin)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getType() == gameTypePolicy)
                ourGames.add(game);
        }
        return ourGames;
    }


    ///////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////



    public void setMinimumBet(int id,int bet) {
        IGame myGame = find(id);
        myGame.setMinimumBet(bet);
    }




    public void setMinimumPlayers(int id, int num) {
        IGame myGame = find(id);
        myGame.setMinimumPlayers(num);
    }




    public void leaveGame(int id, User usr, int userID) {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.leaveGame(p, userID);
    }


    public void setMaximumPlayers(int id, int num) {
        IGame myGame = find(id);
        myGame.setMaximumPlayers(num);
    }

    public void setChipNum(int id, int num) {
        IGame myGame = find(id);
        myGame.setChipNum(num);
    }


    public int getMaxPlayers(int id) {
        IGame myGame = find(id);
        return myGame.getMaxPlayers();
    }


    public int getMinPlayers(int id) {
        IGame myGame = find(id);
        return myGame.getMinPlayers();
    }


    public int getChips(int id) {
        IGame myGame = find(id);
        return myGame.getChips();
    }


    public int getBuyIn(int id) {
        IGame myGame = find(id);
        return myGame.getBuyIn();
    }


    public int getId(int id) {
        IGame myGame = find(id);
        return myGame.getId();
    }


    public void publishMessage(int id, String msg, Player player) {
        IGame myGame = find(id);
        myGame.publishMessage(msg, player);
    }




    public int getPot(int id) {
        IGame myGame = find(id);
        return myGame.getPot();
    }


    public boolean spectaAble(int id) {
        IGame myGame = find(id);
        return myGame.spectaAble();
    }



    public void terminateGame(int id) {
        IGame myGame = find(id);
        myGame.terminateGame();
    }



    public boolean inMax(int id) {
        IGame myGame = find(id);
        return myGame.inMax();
    }


    public String getType(int id) {
        IGame myGame = find(id);
        return myGame.getType();
    }


    public Hashtable<String, ArrayList<String>> getAllTurnsByAllPlayers(int id) {
        IGame myGame = find(id);
        return myGame.getAllTurnsByAllPlayers();
    }


    public void endTurn(int id, Player player) {
        IGame myGame = find(id);
        myGame.endTurn(player);
    }


    public void endRound(int id) {
        IGame myGame = find(id);
        myGame.endRound();
    }


    public ArrayList<String> getAllTurnsOfPlayer(int id, Player p, ArrayList<String> allTurns) {
        IGame myGame = find(id);
        return myGame.getAllTurnsOfPlayer(p, allTurns);
    }


    public void spectateGame(int id, User user) {
        IGame myGame = find(id);
        myGame.spectateGame(user);
    }




    public void win(int id,User user) {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(user);
        myGame.win(p);
    }


    public void dealCard(int id, User usr) {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.dealCard(p);
    }



    public void call(int id, int amount, User usr) {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.call(amount,p);
    }


    public boolean isLocked(int id) {
        IGame myGame = find(id);
        return myGame.isLocked();
    }


    public Player findPlayer(int id, User usr) {
        IGame myGame = find(id);
        return myGame.findPlayer(usr);
    }



}

