package Users;


import Loggers.ErrorLogger;

public class PasswordNotValid extends Exception {
    public PasswordNotValid(String s) {
        super();
        ErrorLogger.getInstance().writeToFile("password "+s+" is not valid");
    }
}
