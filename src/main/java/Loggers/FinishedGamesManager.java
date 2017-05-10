package Loggers;

import Games.IGame;
import Users.User;
import Games.Player;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;


public class FinishedGamesManager implements IFinishedGamesManager {
    private static FinishedGamesManager instance = null;
    ArrayList<GameLogger> finishedGames = new ArrayList<>();
    private final String relPathToLogs = "GameLogs";


    public static FinishedGamesManager getInstance() {
       // if (instance == null) {
            instance = new FinishedGamesManager();
        //}
        return instance;
    }

    @Override
    public void clearAllFinishedGames()
    {
        if(finishedGames!=null)
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
            if(content != null) {
                if (content.indexOf("game ended") != -1)//the game ended
                    finishedGames.add(g);
            }
        }
    }

    @Override
    public ArrayList<String> getNamesOfAllEndedGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : finishedGames)
            ans.add(g.getFilename().replace(".txt", ""));
        return ans;
    }

    @Override
    public ArrayList<String> viewReplay(int gameNumber) {
        if (gameNumber <= 0 || !isFinishedGameExists(gameNumber)) return new ArrayList<String>();
        else {
            for (GameLogger g : finishedGames) {
                if (g.getFilename().equals("Game" + gameNumber + ".txt")) return g.getContentOfFile();
            }
            return new ArrayList<String>();
        }
    }

    @Override
    public void saveFavoriteTurn(User user, IGame game, int turn) {
        ArrayList<String> all_turns_of_user = new ArrayList<>();

        Hashtable<String, ArrayList<String>> temp = game.getAllTurnsByAllPlayers();
        for (Player p : user.getExistingPlayers())
            all_turns_of_user.addAll(temp.get(p.getName()));
        if (turn >= 1) {
            String chosenTurn = all_turns_of_user.get(turn - 1);
            user.getFavoriteTurns().add(chosenTurn);
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


    private boolean isFinishedGameExists(int num) {
        for (GameLogger gameLogger : finishedGames) {
            if (gameLogger.getFilename().equals("Game" + num + ".txt")) return true;
        }
        return false;
    }

    @Override
    public void deleteAllFinishedGameLogs()
    {
        if(finishedGames != null)
        {
            for(GameLogger g : finishedGames )
            {
               g.deleteFile();
            }
            finishedGames.clear();
        }

    }

}
