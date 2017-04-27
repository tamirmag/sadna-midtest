package Users;

import Loggers.IErrorLogger;


public class UserNotLoggedIn extends Exception {
    public UserNotLoggedIn(String username) {
        super();
        IErrorLogger.getInstance().writeToFile("username "+username+" is not logged in");
    }
}
