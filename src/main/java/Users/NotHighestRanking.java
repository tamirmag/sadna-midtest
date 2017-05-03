package Users;


import Loggers.IErrorLogger;

public class NotHighestRanking extends Exception{

    public NotHighestRanking(String s) {
        super("user "+s+" is not a highest ranking user");
        IErrorLogger.getInstance().writeToFile("user "+s+" is not a highest ranking user");
    }
}
