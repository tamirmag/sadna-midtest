package Users;


import Loggers.IErrorLogger;

public class UserAlreadyInLeague extends Exception {

    public UserAlreadyInLeague(String username , int league) {
        super("user "+username+" is already in league "+league);
        IErrorLogger.getInstance().writeToFile("user "+username+" is already in league "+league);
    }
}
