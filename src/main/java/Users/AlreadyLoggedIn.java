package Users;

import Loggers.IErrorLogger;

public class AlreadyLoggedIn extends Exception {
    public AlreadyLoggedIn(String s) {
        super("user "+s+" already logged in");
        IErrorLogger.getInstance().writeToFile("user "+s+" already logged in");
    }
}