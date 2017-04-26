package Users;


import Loggers.IErrorLogger;

public class UsernameNotValid extends Exception {
    public UsernameNotValid(String username) {
        super();
        IErrorLogger.getInstance().writeToFile("username "+username+" is not valid");
    }
}
