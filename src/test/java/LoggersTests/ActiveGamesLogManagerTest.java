package LoggersTests;

import Loggers.ActiveGamesLogManager;
import Loggers.GameLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class ActiveGamesLogManagerTest {
    @Before
    public void atStart()
    {
        File dir = new File("GameLogs");
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    @Test
    public void test1()
    {

        ActiveGamesLogManager.getInstance().AddGameLogger(3);
        ActiveGamesLogManager.getInstance().AddGameLogger(4);
        ActiveGamesLogManager.getInstance().AddGameLogger(5);
        ActiveGamesLogManager.getInstance().AddGameLogger(6);
        String [] activegames = new String[]{"Game3" ,"Game4","Game5","Game6"} ;
        ArrayList<String> names= ActiveGamesLogManager.getInstance().getNamesOfAllActiveGames();
        assertArrayEquals(activegames,names.toArray());
        assertTrue(ActiveGamesLogManager.getInstance().isActiveGameExists(5) ==true);
    }

}