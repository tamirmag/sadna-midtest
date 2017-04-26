package ServiceLayer;

import Users.*;

public interface ServiceInterface {
    String register(String name, String password, String email, int wallet);
    String login(String name, String password);
    String logout(String name);
    String createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy,
                      int minimumBet, int minimalAmountOfPlayers,
                      int maximalAmountOfPlayers, boolean spectatingMode);
    String joinGame(int gamenunber, String username);
    String spectateGame(int gamename, String username);
    String viewReplay(int gamename, String username);
    String saveFavoriteTurn(int gamename, String username, String turn);
    String findActiveGamesByPotSize(int potSize, String username);
    String setDefaultLeague(int league, User u);
    String setCriteria(int criteria, User u);
    String moveToLeague(String username, User u, int league);
    String findSpectatableGames(String username);
}
