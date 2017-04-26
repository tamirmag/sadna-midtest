package Users;



import Loggers.IErrorLogger;

public class AlreadyLoggedOut extends Exception {
    public AlreadyLoggedOut(String s) {
        super("user " +s+ " already logged out");
        IErrorLogger.getInstance().writeToFile("user " +s+ " already logged out");
    }
}