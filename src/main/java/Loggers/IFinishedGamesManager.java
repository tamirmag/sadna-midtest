package Loggers;


import Games.IGame;
import Users.User;

import java.util.ArrayList;

public interface IFinishedGamesManager {
    void saveFavoriteTurn(User user, IGame game, int turn);

    ArrayList<String> viewReplay(int gameNumber);

    ArrayList<String> getNamesOfAllEndedGames();

    void clearAllFinishedGames();

    void deleteAllFinishedGameLogs();

    static IFinishedGamesManager getInstance() {
        return FinishedGamesManager.getInstance();
    }
}
