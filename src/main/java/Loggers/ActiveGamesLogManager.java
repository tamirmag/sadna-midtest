package Loggers;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import DB.LoggerDB;
import Users.IAccountManager;
import Users.IUserManager;
import Users.User;

import static java.lang.Thread.sleep;

public class ActiveGamesLogManager implements IActiveGamesLogManager {
    private static final ActiveGamesLogManager instance = new ActiveGamesLogManager();
    ArrayList<GameLogger> ActiveGames = new ArrayList<GameLogger>();


    private ActiveGamesLogManager() {
        ArrayList<GameLogger> loggers = LoggerDB.getInstance().getAllGameLoggers();
        for (GameLogger g : loggers) {
            ArrayList<String> content = g.getContentOfFile();
            if (content.indexOf("game ended") == -1)//the game is active
                ActiveGames.add(g);
        }
    }


    public static IActiveGamesLogManager getInstance() {
        return instance;
    }

    @Override
    public ArrayList<String> getNamesOfAllActiveGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : ActiveGames)
            ans.add(g.getFilename().replace(".txt", ""));//ans.add(g.getFilename().replace(".txt", ""));
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
                    running = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void spectateGame1(int gameNumber, User user) {
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
        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    @Override
    public String getFileNameByGameNum(int num) {
        StringBuilder s = new StringBuilder("");
        for (GameLogger g : ActiveGames) {
            if (g.getFilename().equals("Game" + num)) {
                s.append(g.getFilename());
                break;
            }
        }
        return s.toString();
    }


    @Override
    public void AddGameLogger(int g) {
        GameLogger g1 = new GameLogger(g);
        ActiveGames.add(g1);
    }

    @Override
    public void AddGameLogger(GameLogger g) {
        ActiveGames.add(g);
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
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == ganeNum) {
                log = logger;
                break;
            }
        }
        return log;
    }

    @Override
    public void RemoveGameLogger(int gameNum) {
        for (GameLogger logger : ActiveGames) {
            if (logger.getGameNumber() == gameNum) {
                logger.deleteFile();
                ActiveGames.remove(logger);
                break;
            }
        }
    }

    @Override
    public void RemoveGameLogger(GameLogger g) {
        ActiveGames.remove(g);
    }

    @Override
    public void RemoveAllGameLoggers() {
        if (ActiveGames != null) {
            Iterator<GameLogger> iter = ActiveGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
                g.deleteFile();
                iter.remove();
            }
        }
    }
}

/*    public void RemoveAllGameLoggers1() {
        filesWrite.lock();
        if (ActiveGames != null) {
            Iterator<GameLogger> iter = ActiveGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
                File f = g.getFile();
                try {
                   Files.delete(f.toPath());
                } catch (IOException e) {
               }
                iter.remove();
            }
        }
        filesWrite.unlock();
    }
    */
