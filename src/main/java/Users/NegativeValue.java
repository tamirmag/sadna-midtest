package Users;


import Loggers.IErrorLogger;

public class NegativeValue extends Exception {
    public NegativeValue(int val) {
        super(val+ " is not a positive value");
        IErrorLogger.getInstance().writeToFile( val+ " is not a positive value");
    }
}
