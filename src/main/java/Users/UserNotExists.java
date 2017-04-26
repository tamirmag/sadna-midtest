package Users;


import Loggers.IErrorLogger;

public class UserNotExists extends Exception {
    public UserNotExists(String username) {
        super("username "+username+" does not exist");
        IErrorLogger.getInstance().writeToFile("username "+username+" does not exist");
    }
}

