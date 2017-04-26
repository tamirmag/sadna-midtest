package Loggers;


public class ErrorLogger extends MyLogger implements IErrorLogger {
    private static ErrorLogger instance = null;
    private ErrorLogger(){
        super( "ErrorLog.txt","SystemLogs");

    }

    public static IErrorLogger getInstance()
    {
        if(instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }



}

