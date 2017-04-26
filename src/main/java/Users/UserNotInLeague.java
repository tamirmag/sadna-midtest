package Users;


import Loggers.ErrorLogger;

public class UserNotInLeague extends Exception {

    public UserNotInLeague(String username, int league) {

        super();
        ErrorLogger.getInstance().writeToFile("username "+username+" is not in league "+ league);
    }
}
