package Loggers;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class ErrorLogger extends MyLogger implements IErrorLogger {
    @Id
    private String filename = "ErrorLog";
    private static final ErrorLogger instance = new ErrorLogger();

    private ErrorLogger() {
        super("ErrorLog");
        filename = "ErrorLog";

    }

    public static IErrorLogger getInstance() {
        return instance;
    }


}




