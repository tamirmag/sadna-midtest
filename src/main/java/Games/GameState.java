package Games;


import java.util.ArrayList;

public class GameState {

    private ArrayList<Player> allPlayers;
    private ArrayList<Player> notFoldPlayers;
    private ArrayList<Card> tableCards;
    private ArrayList<Card> specificPlayerCards;
    private int currentMinimumBet;
    private int pot;


    public GameState(ArrayList<Player> allPlayers,
                     ArrayList<Player> notFoldPlayers,
                     ArrayList<Card> tableCards,
                     ArrayList<Card> specificPlayerCards,
                     int currentMinimumBet,
                     int pot) {

        this.allPlayers = allPlayers;
        this.notFoldPlayers = notFoldPlayers;
        this.tableCards = tableCards;
        this.specificPlayerCards = specificPlayerCards;
        this.currentMinimumBet = currentMinimumBet;
        this.pot = pot;
    }
}
