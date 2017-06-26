package Loggers;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class GameLogger extends MyLogger implements IMyLogger {

    @Id
    private String gameNumber;

    public GameLogger(int gameNumber) {
        super("Game" + gameNumber );
        this.gameNumber = "Game" + gameNumber ;
    }


    public GameLogger(String filename) {
        super(filename);
    }

    public ArrayList<String> getRound(int roundnum) {
        ArrayList<String> lines = getContentOfFile();
        int round1 = getNumOfLineByRound(roundnum);
        int round2 = getNumOfLineByRound(roundnum + 1);
        if (round1 == -1) return new ArrayList<>();
        else if (round2 == -1) return new ArrayList<>(lines.subList(round1 + 1, lines.size()));
        else return new ArrayList<>(lines.subList(round1 + 1, round2));
    }

    private int getNumOfLineByRound(int roundNumber) {
        int counter = 0;
        boolean found = false;
        ArrayList<String> lines = getContentOfFile();
        for (String line : lines) {
            if (line.contains("Round " + roundNumber)) {
                found = true;
                break;
            }
            counter++;
        }
        if (found) return counter;
        else return -1;
    }

    public int getGameNumber() {
        int ret = -1;
        String i = gameNumber.substring(4);
        if(tryParseInt(i))
        {
            ret = Integer.parseInt(i);
        }
        return ret;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}