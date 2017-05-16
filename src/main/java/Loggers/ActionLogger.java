package Loggers;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class ActionLogger extends MyLogger implements IActionLogger {
    private static final ActionLogger instance = new ActionLogger();
    private Semaphore semaphore = new Semaphore(1, true);

    private ActionLogger() {
        super("ActionLog.txt", "SystemLogs");
    }

    public static IActionLogger getInstance() {
       /* semaphore.acquire();
        if(instance == null) {
            instance = new ActionLogger();
        }*/
        return instance;
    }

    @Override
    public ArrayList<String> getLinesByUsername(String username) {
        ArrayList<String> ret = getContentOfFile();

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String s : ret) {
            if (!s.contains(username)) ret.remove(s);
        }
        semaphore.release();
        return ret;
    }


}
