package Loggers;

import Games.IGame;
import Users.User;
import Games.Player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class FinishedGamesManager implements IFinishedGamesManager {
    private final static FinishedGamesManager instance = new FinishedGamesManager();
    ArrayList<GameLogger> finishedGames = new ArrayList<>();
    private final String relPathToLogs = "GameLogs";

    ReentrantReadWriteLock filesLock = new ReentrantReadWriteLock(true);
    final Lock filesRead = filesLock.readLock();
    final Lock filesWrite = filesLock.readLock();

    ReentrantReadWriteLock usersLock = new ReentrantReadWriteLock(true);
    final Lock usersRead = usersLock.readLock();

    public static FinishedGamesManager getInstance() {
        // if (instance == null) {
        //    instance = new FinishedGamesManager();

        return instance;
    }

    @Override
    public void clearAllFinishedGames() {
        if (finishedGames != null)
            for (GameLogger g : finishedGames) g.clearLog();
    }

    private FinishedGamesManager() {
        String s = relPathToLogs + "\\";
        Path inputPath = Paths.get(s);
        Path fullPath = inputPath.toAbsolutePath();
        File[] files = new File(fullPath.toString()).listFiles();
        for (File f : files) {
            GameLogger g = new GameLogger(f.getName());
            ArrayList<String> content = g.getContentOfFile();
            if (content != null) {
                if (content.indexOf("game ended") != -1)//the game ended
                    finishedGames.add(g);
            }
        }
    }

    @Override
    public ArrayList<String> getNamesOfAllEndedGames() {
        ArrayList<String> ans = new ArrayList<>();
        filesRead.lock();
        for (GameLogger g : finishedGames)
            ans.add(g.getFilename().replace(".txt", ""));
        filesRead.unlock();
        return ans;
    }

    @Override
    public ArrayList<String> viewReplay(int gameNumber) {
        if (gameNumber <= 0 || !isFinishedGameExists(gameNumber)) return new ArrayList<String>();
        else {
            filesRead.lock();
            for (GameLogger g : finishedGames) {
                if (g.getFilename().equals("Game" + gameNumber + ".txt")) return g.getContentOfFile();
            }
            filesRead.unlock();
            return new ArrayList<String>();
        }
    }

   /* @Override
    public void saveFavoriteTurn(User user, IGame game, int turn) {
        ArrayList<String> all_turns_of_user = new ArrayList<>();
        Hashtable<String, ArrayList<String>> temp = game.getAllTurnsByAllPlayers();

        usersRead.lock();
        for (Player p : user.getExistingPlayers())
            all_turns_of_user.addAll(temp.get(p.getName()));
        if (turn >= 1) {
            String chosenTurn = all_turns_of_user.get(turn - 1);
            user.getFavoriteTurns().add(chosenTurn);
        }
        usersRead.unlock();
    }*/

    private boolean isFinishedGameExists(int num) {
        filesRead.lock();
        for (GameLogger gameLogger : finishedGames) {
            if (gameLogger.getFilename().equals("Game" + num + ".txt")) return true;
        }
        filesRead.unlock();
        return false;
    }

    @Override
    public void addFinishedGameLog(GameLogger g) {
        filesWrite.lock();
        finishedGames.add(g);
        filesWrite.unlock();

    }
    @Override
    public void deleteAllFinishedGameLogs() {
     /*   if (finishedGames != null) {
            for (GameLogger g : finishedGames) {
                g.deleteFile();
            }
            finishedGames.clear();
        }*/

        filesWrite.lock();
        if (finishedGames != null) {
            Iterator<GameLogger> iter = finishedGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
                System.out.println("deleting finished game " + g.getFilename());
                iter.remove();
                g.deleteFile();
            }
        }
        filesWrite.unlock();
    }
}
