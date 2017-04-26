package Users;


import Loggers.IErrorLogger;

public class UserNotInLeague extends Exception {

    public UserNotInLeague(String username, int league) {

        super();
        IErrorLogger.getInstance().writeToFile("username "+username+" is not in league "+ league);
    }
}
