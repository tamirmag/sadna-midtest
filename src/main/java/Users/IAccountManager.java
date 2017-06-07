package Users;


import Games.Player;

import java.util.ArrayList;

public interface IAccountManager {
    static IAccountManager getInstance() {
        return AccountManager.getInstance();
    }

    boolean isUserExists(String username);

    User getLoggedInUser(String username) throws UserNotExists, UserNotLoggedIn;

    UserManager register(String username, String password, String email, int wallet)
            throws UserAlreadyExists, PasswordNotValid, EmailNotValid, NegativeValue, UsernameNotValid;

    boolean isValidEmail(String email);

    void logout(User u) throws AlreadyLoggedOut, UserNotExists;

    void logout(String username) throws AlreadyLoggedOut, UserNotExists;

    UserManager login(String username, String password)
            throws UsernameNotValid, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists;

    void addUser(User u) throws UserAlreadyExists, UsernameNotValid;

    void addLoggedInUser(User u) throws AlreadyLoggedIn;

    void setUsername(User u, String username) throws UsernameNotValid, UserNotExists, UserAlreadyExists;

    void setEmail(User u, String email) throws EmailNotValid;

    void setPassword(User u, String password) throws PasswordNotValid;

    void clearUsers();

    void clearLoggedInUsers();

    void clearLeagues();

    int getUnknownLeague();

    void updateNumOfGames(ArrayList<Player> players);

    ArrayList<User> getTop20NumOfGames();

    ArrayList<User> getTop20highestCashGained();

    ArrayList<User> getTop20GrossProfit();

    double getUserAverageCashGain(String username) throws UserNotExists;

    double getUserAverageGrossProfit(String username) throws UserNotExists;

    void setTotalCashGain(String username, int totalCashGain) throws UserNotExists;

    void setGrossProfit(String username, int grossProfit) throws UserNotExists;

    void setHighestCashGained(String username, int highestCashGained) throws UserNotExists;

    int getTotalCashGain(String username) throws UserNotExists;

    int getGrossProfit(String username) throws UserNotExists;

    int getHighestCashGained(String username) throws UserNotExists;


}
//int getMaximalRank();

// void moveUserToLeague(String username, int newLeague) throws UserNotInLeague, LeagueNotExists, UserAlreadyInLeague, UserNotExists;

