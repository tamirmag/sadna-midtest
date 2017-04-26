package Users;


import Loggers.ErrorLogger;

public class UserNotExists extends Exception {
    public UserNotExists(String username) {
        super();
        ErrorLogger.getInstance().writeToFile("username "+username+" does not exist");
    }
}

