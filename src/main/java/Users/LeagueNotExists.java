package Users;


import Loggers.IErrorLogger;

public class LeagueNotExists extends Exception {

    public LeagueNotExists(int league) {
        super();
        IErrorLogger.getInstance().writeToFile("league " +league+ " does not exist");
    }
}
