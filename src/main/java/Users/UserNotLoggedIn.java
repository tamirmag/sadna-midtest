package Users;

import Loggers.IErrorLogger;


public class UserNotLoggedIn extends Exception {
    public UserNotLoggedIn(String username) {
        super("username "+username+" is not logged in");
        IErrorLogger.getInstance().writeToFile("username "+username+" is not logged in");
    }
}
