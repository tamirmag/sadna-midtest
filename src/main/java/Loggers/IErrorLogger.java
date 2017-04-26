package Loggers;

/**
 * Created by רועי on 4/26/2017.
 */
public interface IErrorLogger extends IMyLogger {
    static IErrorLogger getInstance()
    {
        return ErrorLogger.getInstance();
    };

}
