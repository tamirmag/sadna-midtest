package Users;


import Loggers.ErrorLogger;

public class NegativeValue extends Exception {
    public NegativeValue(int val) {
        super();
        ErrorLogger.getInstance().writeToFile( val+ " is not a positive value");
    }
}
