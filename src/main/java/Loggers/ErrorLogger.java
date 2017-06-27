package Loggers;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class ErrorLogger extends MyLogger implements IErrorLogger {
    @Id
    private String filename = "ErrorLog";
    private static ErrorLogger instance;

    private ErrorLogger() {
        super("ErrorLog");
        filename = "ErrorLog";

    }

    public static IErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }


}




