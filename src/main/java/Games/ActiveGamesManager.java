package Games;

import java.util.ArrayList;
import java.util.List;
import Users.User;

public class ActiveGamesManager
{
    private static ActiveGamesManager instance = null;
    ArrayList<IGame> games = new ArrayList<IGame>();
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

    public void createGame(User user, String type, Preferences pref) {
        ArrayList<Player> players = new ArrayList<Player>();
        Player p = new Player(user.getUsername(), user.getWallet());
        p.wallet = user.getWallet();
        p.name = user.getUsername();
        players.add(p);
        IGame game = null;
        switch (type)
        {
            case "NoLimitHoldem":
                    game = new NoLimitHoldem(players, ++index);
                break;

            case "LimitHoldem":
                game = new LimitHoldem(players, ++index);
                break;

            case "PotLimitHoldem":
                game = new PotLimitHoldem(players, ++index);
                break;

            default:
                game = new Game(players, ++index, "Normal");
        }

        game = buildByPref(pref, game);
        games.add(game);
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

    public List<IGame> findActiveGamesByPlayer(String name) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.isPlayerInGame(name))
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {

        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getPot() == potSize)
                ourGames.add(game);
        }
        return ourGames;
    }


    public List<IGame> findSpectatableGames(User user) {

        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.spectaAble())
                ourGames.add(game);
        }
        return ourGames;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<IGame> findActiveGamesByPlayersMinimumPolicy(int minimal) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getMinPlayers() == minimal)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByPlayersMaximumPolicy(int maximal) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getMaxPlayers() == maximal)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getMinimumBet() == minimumBet)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getChips() == numOfChips)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getBuyIn() == costOfJoin)
                ourGames.add(game);
        }
        return ourGames;
    }

    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {
        ArrayList<IGame> ourGames = new ArrayList<IGame>();
        for (IGame game : games) {
            if (game.getType() == gameTypePolicy)
                ourGames.add(game);
        }
        return ourGames;
    }

}

