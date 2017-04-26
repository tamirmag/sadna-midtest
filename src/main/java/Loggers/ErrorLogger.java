package Loggers;


public class ErrorLogger extends MyLogger {
    private static ErrorLogger instance = null;
    private ErrorLogger(){
        super( "ErrorLog.txt","SystemLogs");

    }

    public static ErrorLogger getInstance()
    {
        if(instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }



}

