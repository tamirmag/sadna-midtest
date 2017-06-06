package Loggers;

import Users.User;

import java.util.ArrayList;

public interface IActiveGamesLogManager {
    static IActiveGamesLogManager getInstance() {
        return ActiveGamesLogManager.getInstance();
    }

    ArrayList<String> getNamesOfAllActiveGames();

    void spectateGame(int gameNumber, User user);

    boolean isActiveGameExists(int num);

    String getFileNameByGameNum(int num);

    void AddGameLogger(int g);

    void AddGameLogger(GameLogger g);

    void WriteToGameLogger(int gameNum, String message);

    void RemoveGameLogger(int gameNum);

    void RemoveAllGameLoggers();

    void RemoveGameLogger(GameLogger g);
}
