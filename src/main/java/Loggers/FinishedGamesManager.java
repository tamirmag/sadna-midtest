package Loggers;

import Games.IGame;
import Users.User;
import Games.Player;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinishedGamesManager {
    private static FinishedGamesManager instance= null;
    ArrayList<GameLogger> finishedGames = new ArrayList<>();
    private final String relPathToLogs = "GameLogs";

    public static FinishedGamesManager getInstance()
    {
        if(instance==null)
        {
            instance = new FinishedGamesManager();
        }
        return instance;
    }
    private FinishedGamesManager() {
        String filepath = "Logs";
        String s = filepath + "\\";
        Path inputPath = Paths.get(s);
        Path fullPath = inputPath.toAbsolutePath();

        File[] files = new File(fullPath.toString()).listFiles();

        for (File f : files) {

            GameLogger g = new GameLogger(f.getName());
            ArrayList<String> content = g.getContentOfFile();
            if (content.indexOf("game ended") != -1)//the game ended
                finishedGames.add(g);
        }

    }

    public ArrayList<String> getNamesOfAllEndedGames() {
        ArrayList<String> ans = new ArrayList<>();
        for (GameLogger g : finishedGames)
            ans.add(g.getFilename().replace(".txt" ,""));
        return ans;
    }

    public ArrayList<String> viewReplay(int gameNumber) {

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("please insert number of game:");
        String s = scanner.next();
        while (!isInteger(s)) {
            System.out.println("your input is not representing a game number.please try again.");
            System.out.println("please insert number of game:");
            s = scanner.next();
        }

        int game = Integer.parseInt(s);
        while (!isFinishedGameExists(game)) {
            System.out.println("there is no finished game with this number. try again:");
            System.out.println("please insert number of game:");
            s = scanner.next();
            if (!isInteger(s)) continue;
            else game = Integer.parseInt(s);
        }*/
        if(gameNumber <=0 || !isFinishedGameExists(gameNumber)) return new ArrayList<String>();

        else return filterFinishedGames(isFinishedGameExistsfilter(gameNumber)).get(0).getContentOfFile();

    }

    public void saveFavoriteTurn(User user , IGame game , int turn)
    {
        ArrayList<String> all_turns_of_user = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Hashtable<String,ArrayList<String>> temp = game.getAllTurnsByAllPlayers();
        for(Player p : user.getExistingPlayers())
            all_turns_of_user.addAll(temp.get(p.getName()));

       /* int counter = 1;
        System.out.println("please choose a turn from the turns below:");
        for(String s :all_turns_of_user)
        {
            System.out.println(counter +") "+s);
            counter++;
        }*/



       /* String s = scanner.next();
        while(!isInteger(s))
        {
            System.out.println("your input does not represent a number. please try again:");
            System.out.println("please choose a turn from the turns above:");
            s = scanner.next();
        }

        int choose = Integer.parseInt(s);
        while(choose > counter || choose < 1)
        {
            System.out.println("your input does not represent a number of valid turn. please try again:");
            System.out.println("please choose a turn from the turns above:");
            s = scanner.next();
            while(!isInteger(s))
            {
                System.out.println("your input does not represent a number. please try again:");
                System.out.println("please choose a turn from the turns above:");
                s = scanner.next();
            }
            choose = Integer.parseInt(s);

        }
        */
        if(turn >=1) {
            String chosenTurn = all_turns_of_user.get(turn-1);
            user.getFavoriteTurns().add(chosenTurn);
        }
        // System.out.println("turn number "+choose +"was added successfully to your favorite turns list.");


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
        return !filterFinishedGames(isFinishedGameExistsfilter(num)).isEmpty();
    }


    private Predicate<GameLogger> isFinishedGameExistsfilter(int num) {
        return p -> p.getFilename().equals("Game" + num + ".txt");
    }


    private ArrayList<GameLogger> filterFinishedGames(Predicate<GameLogger> predicate) {
        return new ArrayList<>(finishedGames.stream().filter(predicate).collect(Collectors.toList()));
    }
}
