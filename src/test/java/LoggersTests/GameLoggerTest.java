package LoggersTests;

import Loggers.GameLogger;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameLoggerTest {
    static GameLogger gameLogger;
    @BeforeClass
    public static void setUp() throws Exception {
        gameLogger = new GameLogger(2);
        gameLogger.writeToFile("Round 1");
        gameLogger.writeToFile("he gpkjnfgds");
        gameLogger.writeToFile("jsdbvksvkdfv");
        gameLogger.writeToFile("kncx jvk");
        gameLogger.writeToFile("osjfidn");
        gameLogger.writeToFile("Round 2");
    }

    @Test
    public void check1()//defined round
    {
        ArrayList<String> out=  gameLogger.getRound(1);
        assertEquals(out.size() ,4);
    }
    @Test
    public void check2() //UndefinedRound
    {
        ArrayList<String> out=  gameLogger.getRound(2);
        assertTrue(out.size() ==0);
    }

    @Test
    public void check3()//empty file
    {
        //gameLogger.clearLog();
        ArrayList<String> out=  gameLogger.getRound(1);
        assertFalse(out.size() ==0);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        gameLogger.clearLog();
    }

}