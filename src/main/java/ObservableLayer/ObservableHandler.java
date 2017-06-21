package ObservableLayer;


import java.util.ArrayList;
import java.util.HashMap;

public class ObservableHandler implements IObservableHandler {


    private HashMap<Integer, ArrayList<ObservableSpectator>> spectators;
    private HashMap<Integer, ArrayList<ObservablePlayer>> players;
    private static final IObservableHandler instance = new ObservableHandler();

    public static IObservableHandler getInstance()
    {
        return instance;
    }
    private ObservableHandler() {
        players = new HashMap<>();
        spectators = new HashMap<>();
    }

    @Override
    public void attachPlayer(String playerName, String ipAddress, int port, int game) {
        ObservablePlayer player = new ObservablePlayer(ipAddress, port, playerName);
        if (!players.containsKey(game)) {
            players.put(game, new ArrayList<ObservablePlayer>());
        }
        players.get(game).add(player);

    }

    @Override
    public void attachSpectator(String playerName, String ipAddress, int port, int game) {
        ObservableSpectator spectator = new ObservableSpectator(ipAddress, port, playerName);
        if (!spectators.containsKey(game)) {
            spectators.put(game, new ArrayList<ObservableSpectator>());
        }
        spectators.get(game).add(spectator);

    }

    @Override
    public void notifySpectators(int game, String content) {
        if (!spectators.containsKey(game)) return;
        for (ObservableSpectator spectator : spectators.get(game)) {
            IObserverClient.getInstance().sendToSpectator(spectator, game, content);
        }
    }

    @Override
    public void sendCardsToPlayer(String playerName, int game, Object cards) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            if (player.getName().equals(playerName)) {
                IObserverClient.getInstance().sendCardsToPlayer(player, game, cards);
                break;
            }
        }
    }

    @Override
    public void sendGameStateToPlayer(String playerName, int game, Object gameState) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            if (player.getName().equals(playerName)) {
                IObserverClient.getInstance().sendGameStateToPlayer(player, game, gameState);
                break;
            }
        }
    }

    @Override
    public void sendSomeoneFold(String foldedPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneFold(player, game, foldedPlayer);
        }
    }

    @Override
    public void sendSomeoneCheck(String checkPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneCheck(player, game, checkPlayer);
        }
    }

    @Override
    public void sendSomeoneAllIn(String allinPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneAllIn(player, game, allinPlayer);
        }
    }

    @Override
    public void sendSomeoneWinner(String winnerPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneWinner(player, game, winnerPlayer);
        }
    }

    @Override
    public void sendSomeoneRaised(String raisedPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneRaised(player, game, raisedPlayer, amount);
        }
    }

    @Override
    public void sendSomeoneBet(String betPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneBet(player, game, betPlayer, amount);
        }
    }

    @Override
    public void sendSomeoneCall(String callPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneCall(player, game, callPlayer, amount);
        }
    }

}



