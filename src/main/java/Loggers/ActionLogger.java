package Loggers;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;


@Entity
public class ActionLogger extends MyLogger implements IActionLogger {
    @Id
    private String filename = "ActionLog";
    private static ActionLogger instance;

    private ActionLogger() {
        super("ActionLog");
        filename = "ActionLog";
    }

    public static IActionLogger getInstance() {
        if (instance == null) {
            instance = new ActionLogger();
        }
        return instance;
    }

    @Override
    public ArrayList<String> getLinesByUsername(String username) {
        ArrayList<String> ret = getContentOfFile();
        for (String s : ret) {
            if (!s.contains(username)) ret.remove(s);
        }
        return ret;
    }


}