package Loggers;

import java.util.ArrayList;


public class ActionLogger extends MyLogger implements IActionLogger {
    private static ActionLogger instance = null;

    private ActionLogger(){
        super( "ActionLog.txt" , "SystemLogs");

    }

    public static IActionLogger getInstance()
    {
        if(instance == null) {
            instance = new ActionLogger();
        }
        return instance;
    }
    @Override
    public ArrayList<String> getLinesByUsername(String username)
    {
        ArrayList<String> ret = getContentOfFile();
        for(String s : ret)
        {
            if(!s.contains(username)) ret.remove(s);
        }

        return ret;
    }




}
