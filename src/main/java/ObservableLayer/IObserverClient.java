package ObservableLayer;

public interface IObserverClient {

    static IObserverClient getInstance() {
        return ObserverClient.getInstance();
    }

    void sendToSpectator(ObservableSpectator spectator, int game, String content);

    void sendCardsToPlayer(ObservablePlayer player, int game, Object cards);

    void sendGameStateToPlayer(ObservablePlayer player, int game, Object gameState);

    void sendSomeoneFold(ObservablePlayer player, int game, String foldedPlayer);

    void sendSomeoneRaised(ObservablePlayer player, int game, String raisedPlayer, int amount);

    void sendSomeoneBet(ObservablePlayer player,int game,String betPlayer ,int amount);

    void sendSomeoneCall(ObservablePlayer player,int game,String callPlayer ,int amount);

    void sendSomeoneCheck(ObservablePlayer player, int game, String checkPlayer);

    void sendSomeoneAllIn(ObservablePlayer player, int game, String allinPlayer);

    void sendSomeoneWinner(ObservablePlayer player, int game, String winnerPlayer);
}
