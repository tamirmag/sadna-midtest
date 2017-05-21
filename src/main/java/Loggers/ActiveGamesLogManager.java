package Loggers;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import Users.IAccountManager;
import Users.IUserManager;
import Users.User;

import static java.lang.Thread.sleep;

public class ActiveGamesLogManager implements IActiveGamesLogManager {
    private static final ActiveGamesLogManager instance = new ActiveGamesLogManager();
    ArrayList<GameLogger> ActiveGames = new ArrayList<GameLogger>();
    private final String relPathToLogs = "GameLogs";

    ReentrantReadWriteLock filesLock = new ReentrantReadWriteLock(true);
    final Lock filesRead = filesLock.readLock();
    final Lock filesWrite = filesLock.writeLock();

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


    public static IActiveGamesLogManager getInstance() {
       /* if(instance == null)
        {
            instance = new ActiveGamesLogManager();
        }*/

        return instance;
    }

    @Override
    public ArrayList<String> getNamesOfAllActiveGames() {
        ArrayList<String> ans = new ArrayList<>();
        filesRead.lock();
        for (GameLogger g : ActiveGames)
            ans.add(g.getFilename().replace(".txt", ""));
        filesRead.unlock();
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

    @Override
    public boolean isActiveGameExists(int num) {
        boolean ans = false;
        filesRead.lock();
        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num + ".txt")) {
                ans = true;
                break;
            }
        }
        filesRead.unlock();
        return ans;
    }

    @Override
    public String getFileNameByGameNum(int num) {
        StringBuilder s = new StringBuilder("");
        filesRead.lock();
        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num + ".txt")) {
                s.append(g.getFilename());
                break;
            }
        }
        filesRead.unlock();
        return s.toString();
    }


    @Override
    public void AddGameLogger(int g) {
        filesWrite.lock();
        GameLogger g1 = new GameLogger(g);
        ActiveGames.add(g1);
        filesWrite.lock();

    }

    @Override
    public void WriteToGameLogger(int gameNum, String message) {
        IMyLogger g = getGameLogger(gameNum);
        if (g != null) {
            g.writeToFile(message);
        }
    }


    private IMyLogger getGameLogger(int ganeNum) {
        IMyLogger log = null;
        filesRead.lock();
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == ganeNum) {
                log = logger;
                break;
            }
        }
        filesRead.unlock();
        return log;
    }

    @Override
    public void RemoveGameLogger(int gameNum) {
        filesWrite.lock();
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == gameNum) {
                logger.deleteFile();
                ActiveGames.remove(logger);
                break;
            }
        }
        filesWrite.unlock();
    }

    @Override
    public void RemoveAllGameLoggers() {
        filesWrite.lock();
        if (ActiveGames != null) {
            Iterator<GameLogger> iter = ActiveGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
               File f = g.getFile();
                try {
                    Files.delete(f.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
               }
                iter.remove();
                //g.deleteFile();
            }
        }
        filesWrite.unlock();
    }


}