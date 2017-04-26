package Loggers;

import java.util.ArrayList;


public interface IActionLogger extends IMyLogger {
    static IActionLogger getInstance()
    {
        return ActionLogger.getInstance();
    }
    ArrayList<String> getLinesByUsername(String username);
}
