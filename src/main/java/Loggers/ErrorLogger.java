package Loggers;


public class ErrorLogger extends MyLogger implements IErrorLogger {
    private static final ErrorLogger instance = new ErrorLogger();
    private ErrorLogger(){
        super( "ErrorLog.txt","SystemLogs");

    }

    public static IErrorLogger getInstance()
    {
      /*  if(instance == null) {
            instance = new ErrorLogger();
        }*/
        return instance;
    }



}

