package Users;


import Loggers.ErrorLogger;

public class UsernameNotValid extends Exception {
    public UsernameNotValid(String username) {
        super();
        ErrorLogger.getInstance().writeToFile("username "+username+" is not valid");
    }
}
