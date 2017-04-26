package Users;


import Loggers.ErrorLogger;

public class LeagueNotExists extends Exception {

    public LeagueNotExists(int league) {
        super();
        ErrorLogger.getInstance().writeToFile("league " +league+ " does not exist");
    }
}
