package Users;


import Loggers.ErrorLogger;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(String s) {
        super();
        ErrorLogger.getInstance().writeToFile("user "+s+" already exists");
    }
}
