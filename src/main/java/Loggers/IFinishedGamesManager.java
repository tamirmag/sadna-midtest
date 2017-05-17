package Loggers;


import java.util.ArrayList;

public interface IFinishedGamesManager {
    //  void saveFavoriteTurn(User user, IGame game, int turn);

    ArrayList<String> viewReplay(int gameNumber);

    ArrayList<String> getNamesOfAllEndedGames();

    void clearAllFinishedGames();

    void deleteAllFinishedGameLogs();

    void addFinishedGameLog(GameLogger g);

    static IFinishedGamesManager getInstance() {
        return FinishedGamesManager.getInstance();
    }
}
