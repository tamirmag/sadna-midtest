package Loggers;

import DB.LoggerDB;

import java.util.ArrayList;
import java.util.Iterator;


public class FinishedGamesManager implements IFinishedGamesManager {
    private static FinishedGamesManager instance;
    ArrayList<GameLogger> finishedGames = new ArrayList<>();

    public static FinishedGamesManager getInstance() {
        if (instance == null) {
            instance = new FinishedGamesManager();
        }
        return instance;
    }

    @Override
    public void clearAllFinishedGames() {
        if (finishedGames != null)
            for (GameLogger g : finishedGames) g.clearLog();
    }

    private FinishedGamesManager() {
        ArrayList<GameLogger> loggers = LoggerDB.getInstance().getAllGameLoggers();
        for (GameLogger g : loggers) {
            ArrayList<String> content = g.getContentOfFile();
            if (content.indexOf("game ended") != -1)//the game is active
                finishedGames.add(g);
        }
    }

    @Override
    public ArrayList<String> getNamesOfAllEndedGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : finishedGames)
            ans.add(g.getFilename());//ans.add(g.getFilename().replace(".txt", ""));
        return ans;
    }

    @Override
    public ArrayList<String> viewReplay(int gameNumber) {
        if (gameNumber <= 0 || !isFinishedGameExists(gameNumber)) return new ArrayList<String>();
        else {
            for (GameLogger g : finishedGames) {
                if (g.getFilename().equals("Game" + gameNumber)) return g.getContentOfFile();
            }
            return new ArrayList<String>();
        }
    }


    private boolean isFinishedGameExists(int num) {
        for (GameLogger gameLogger : finishedGames) {
            if (gameLogger.getFilename().equals("Game" + num)) return true;
        }
        return false;
    }

    @Override
    public void addFinishedGameLog(GameLogger g) {
        finishedGames.add(g);
    }

    @Override
    public void deleteAllFinishedGameLogs() {
        if (finishedGames != null) {
            Iterator<GameLogger> iter = finishedGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
                g.deleteFile();
                iter.remove();
            }
        }

    }


}



  /*      filesWrite.lock();
        if (finishedGames != null) {
            Iterator<GameLogger> iter = finishedGames.iterator();
            while (iter.hasNext()) {
                GameLogger g = iter.next();
                iter.remove();
                g.deleteFile();
            }
        }
        filesWrite.unlock();
    }*/

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
