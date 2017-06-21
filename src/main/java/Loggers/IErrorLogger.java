package Loggers;


public interface IErrorLogger extends IMyLogger {
    static IErrorLogger getInstance()
    {
        return ErrorLogger.getInstance();
    };

}
