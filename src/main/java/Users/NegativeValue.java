package Users;


import Loggers.IErrorLogger;

public class NegativeValue extends Exception {
    public NegativeValue(int val) {
        super();
        IErrorLogger.getInstance().writeToFile( val+ " is not a positive value");
    }
}
