package Users;


import Loggers.IErrorLogger;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(String s) {
        super();
        IErrorLogger.getInstance().writeToFile("user "+s+" already exists");
    }
}
