package Loggers;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;


@Entity
public class ActionLogger extends MyLogger implements IActionLogger {
    @Id
    private String filename = "ActionLog";
    private static final ActionLogger instance = new ActionLogger();

    private ActionLogger() {
        super("ActionLog");
        filename = "ActionLog";
    }

    public static IActionLogger getInstance() {
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