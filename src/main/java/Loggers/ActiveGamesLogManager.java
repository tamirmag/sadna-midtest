package Loggers;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;


import Users.IAccountManager;
import Users.IUserManager;
import Users.User;

import static java.lang.Thread.sleep;

public class ActiveGamesLogManager implements IActiveGamesLogManager {
    private static ActiveGamesLogManager instance = null;
    ArrayList<GameLogger> ActiveGames = new ArrayList<GameLogger>();
    private final String relPathToLogs = "GameLogs";

    private ActiveGamesLogManager() {
        String filepath = "GameLogs";
        String s = filepath + "\\";
        Path inputPath = Paths.get(s);
        Path fullPath = inputPath.toAbsolutePath();

        File[] files = new File(fullPath.toString()).listFiles();

        for (File f : files) {
            GameLogger g = new GameLogger(f.getName());
            ArrayList<String> content = g.getContentOfFile();
            if (content.indexOf("game ended") == -1)//the game is active
                ActiveGames.add(g);
        }
    }


    public static IActiveGamesLogManager getInstance()
    {
        if(instance == null)
        {
            instance = new ActiveGamesLogManager();
        }
        return instance;
    }

    @Override
    public ArrayList<String> getNamesOfAllActiveGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : ActiveGames)
            ans.add(g.getFilename().replace(".txt", ""));
        return ans;
    }

    @Override
    public void spectateGame(int gameNumber, User user) {

        String filename = getFileNameByGameNum(gameNumber);
        boolean running = true;
        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename))) {
            while (running) {
                if (reader.available() > 0) {
                    user.getCharToPrint((char) reader.read());
                } else {

                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        running = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isActiveGameExists(int num) {

        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num + ".txt")) return true;
        }
        return false;
    }

    @Override
    public String getFileNameByGameNum(int num) {
        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num + ".txt")) return g.getFilename();
        }
        return "";
    }


    @Override
    public void AddGameLogger(int g) {
        GameLogger g1 = new GameLogger(g);
        ActiveGames.add(g1);

    }

    @Override
    public void WriteToGameLogger(int gameNum, String message) {
        IMyLogger g = getGameLogger(gameNum);
        if (g != null) {
            g.writeToFile(message);
        }
    }


    private IMyLogger getGameLogger(int ganeNum) {
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == ganeNum) return logger;
        }
        return null;
    }

    @Override
    public void RemoveGameLogger(int gameNum)  {
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == gameNum)
            {
               logger.deleteFile();
                ActiveGames.remove(logger);
            }
        }
    }
    @Override
    public void RemoveAllGameLoggers()  {
        if(ActiveGames !=null)
        {
            Iterator<GameLogger> iter = ActiveGames.iterator();
            while(iter.hasNext())
            {
                GameLogger g = iter.next();
                g.deleteFile();
                iter.remove();
            }
           // for (GameLogger logger : ActiveGames) {
           //    logger.deleteFile();
            //    ActiveGames.remove(logger);
            //}
           // ActiveGames.clear();
        }
    }


}