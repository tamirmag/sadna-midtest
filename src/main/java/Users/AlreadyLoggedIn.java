package Users;

import Loggers.IErrorLogger;

public class AlreadyLoggedIn extends Exception {
    public AlreadyLoggedIn(String s) {
        super();
        IErrorLogger.getInstance().writeToFile("user "+s+" already logged in");
    }
}