package Loggers;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Users.*;
import Users.User;

import static java.lang.Thread.sleep;

public class ActiveGamesLogManager {
    private static ActiveGamesLogManager instance = null;
    ArrayList<GameLogger> ActiveGames = new ArrayList<>();
    private final String relPathToLogs = "GameLogs";

    public static ActiveGamesLogManager getInstance() {
        if (instance == null) {
            instance = new ActiveGamesLogManager();
        }
        return instance;
    }

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

    public ArrayList<String> getNamesOfAllActiveGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : ActiveGames)
            ans.add(g.getFilename().replace(".txt", ""));
        return ans;
    }

    public void spectateGame(int gameNumber,User user) {
      /*  Scanner scanner = new Scanner(System.in);
        System.out.println("please insert number of game:");
        String s = scanner.next();
        while (!isInteger(s)) {
            System.out.println("your input is not representing a game number.please try again.");
            System.out.println("please insert number of game:");
            s = scanner.next();
        }
        int game = Integer.parseInt(s);
        while (!isActiveGameExists(game)) {
            System.out.println("there is no finished game with this number. try again:");
            System.out.println("please insert number of game:");
            s = scanner.next();
            if (!isInteger(s)) continue;
            else game = Integer.parseInt(s);
        }*/
        String filename = filterActiveGames(isActiveGameExistsfilter(gameNumber)).get(0).getFilename();
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

    public boolean isActiveGameExists(int num) {
        return !filterActiveGames(isActiveGameExistsfilter(num)).isEmpty();
    }


    private Predicate<GameLogger> isActiveGameExistsfilter(int num) {
        return p -> p.getFilename().equals("Game" + num + ".txt");
    }

    public void AddGameLogger(int g)
    {
        GameLogger g1 = new GameLogger(g);
        ActiveGames.add(g1);

    }

    public void WriteToGameLogger(int gameNum,String message)
    {
        GameLogger g = getGameLogger(gameNum);
        if(g!=null)
        {
            g.writeToFile(message);
        }
    }


    private GameLogger getGameLogger(int ganeNum)
    {
        for(GameLogger logger : ActiveGames)
        {
            if(logger.getGameNumber() == ganeNum) return logger;
        }
        return null;
    }

    public void RemoveGameLogger(int gameNum)
    {
        for(GameLogger logger : ActiveGames)
        {
            if(logger.getGameNumber() == gameNum) ActiveGames.remove(logger);
        }
    }

    private ArrayList<GameLogger> filterActiveGames(Predicate<GameLogger> predicate) {
        return new ArrayList<>(ActiveGames.stream().filter(predicate).collect(Collectors.toList()));
    }
}