package Users;



import Loggers.IErrorLogger;

public class AlreadyLoggedOut extends Exception {
    public AlreadyLoggedOut(String s) {
        super();
        IErrorLogger.getInstance().writeToFile("user " +s+ " already logged out");
    }
}