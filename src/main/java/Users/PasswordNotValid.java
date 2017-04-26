package Users;

import Loggers.IErrorLogger;

public class PasswordNotValid extends Exception {
    public PasswordNotValid(String s) {
        super();
        IErrorLogger.getInstance().writeToFile("password "+s+" is not valid");
    }
}
