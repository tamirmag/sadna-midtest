package Users;


import Loggers.IErrorLogger;

public class LeagueNotExists extends Exception {

    public LeagueNotExists(int league) {
        super("league " +league+ " does not exist");
        IErrorLogger.getInstance().writeToFile("league " +league+ " does not exist");
    }
}
