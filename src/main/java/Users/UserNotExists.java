package Users;


import Loggers.IErrorLogger;

public class UserNotExists extends Exception {
    public UserNotExists(String username) {
        super();
        IErrorLogger.getInstance().writeToFile("username "+username+" does not exist");
    }
}

