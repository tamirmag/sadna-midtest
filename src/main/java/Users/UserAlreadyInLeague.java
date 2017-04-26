package Users;


import Loggers.ErrorLogger;

public class UserAlreadyInLeague extends Exception {

    public UserAlreadyInLeague(String username , int league) {
        super();
        ErrorLogger.getInstance().writeToFile("user "+username+" is already in league "+league);
    }
}
