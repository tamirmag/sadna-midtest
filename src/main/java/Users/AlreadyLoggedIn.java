package Users;

import Loggers.ErrorLogger;

public class AlreadyLoggedIn extends Exception {
    public AlreadyLoggedIn(String s) {
        super();
        ErrorLogger.getInstance().writeToFile("user "+s+" already logged in");
    }
}