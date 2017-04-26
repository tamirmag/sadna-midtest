package Users;


import Loggers.IErrorLogger;

public class UsernameNotValid extends Exception {
    public UsernameNotValid(String username) {
        super("username "+username+" is not valid");
        IErrorLogger.getInstance().writeToFile("username "+username+" is not valid");
    }
}
