package ServiceLayer;

import Users.*;

public interface ServiceInterface {
    String register(String name, String password, String email, int wallet);
    String login(String name, String password);
    String logout(String name) throws UserNotLoggedIn, UserNotExists;
    String createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy,
                      int minimumBet, int minimalAmountOfPlayers,
                      int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists;
    String joinGame(int gamenunber, String username) throws UserNotLoggedIn, UserNotExists;
    String spectateGame(int gamename, String username) throws UserNotLoggedIn, UserNotExists;
    String viewReplay(int gamename, String username) throws UserNotLoggedIn, UserNotExists;
    String saveFavoriteTurn(int gamename, String username, String turn) throws UserNotLoggedIn, UserNotExists;
    String findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists;
    String setDefaultLeague(int league, User u);
    String setCriteria(int criteria, User u);
    String moveToLeague(String username, User u, int league);
    String findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists;
}
