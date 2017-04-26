package Users;

import Loggers.IErrorLogger;

public class EmailNotValid extends Exception {
    public EmailNotValid(String s)
    {
        super();
        IErrorLogger.getInstance().writeToFile("email " +s+ "is not valid");
    }
}
