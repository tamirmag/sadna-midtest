package LoggersTests;

import Loggers.ActionLogger;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class ActionLoggerTests {
    @Test//(expected = FileNotFoundException.class)
    public void checkWriteToLogger() {
        ActionLogger.getInstance().writeToFile("let's test this");
        ArrayList<String> content = ActionLogger.getInstance().getContentOfFile();
        assertTrue(content.get(0).equals("let's test this"));

    }

    @Test
    public void checkIfLogIsClear() {
        ActionLogger.getInstance().clearLog();
        ArrayList<String> content = ActionLogger.getInstance().getContentOfFile();
        assertTrue(content.size() == 0);
    }

    @After
    public void tearDown() {
        ActionLogger.getInstance().clearLog();
    }


}