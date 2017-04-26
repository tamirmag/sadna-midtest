package Users;

import Loggers.IErrorLogger;

public class PasswordNotValid extends Exception {
    public PasswordNotValid(String s) {
        super("password "+s+" is not valid");
        IErrorLogger.getInstance().writeToFile("password "+s+" is not valid");
    }
}
