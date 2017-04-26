package Users;


import Loggers.ErrorLogger;

public class EmailNotValid extends Exception {
    public EmailNotValid(String s)
    {
        super();
        ErrorLogger.getInstance().writeToFile("email " +s+ "is not valid");
    }
}
