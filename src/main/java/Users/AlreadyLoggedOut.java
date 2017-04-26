package Users;



import Loggers.ErrorLogger;

public class AlreadyLoggedOut extends Exception {
    public AlreadyLoggedOut(String s) {
        super();
        ErrorLogger.getInstance().writeToFile("user " +s+ " already logged out");
    }
}