package Users;


import Loggers.IErrorLogger;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(String s) {
        super("user "+s+" already exists");
        IErrorLogger.getInstance().writeToFile("user "+s+" already exists");
    }
}
