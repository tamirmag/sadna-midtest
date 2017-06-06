package Games;

/**
 * Created by tamir on 26/04/2017.
 */
public class NotYourTurn extends Exception{
    public NotYourTurn() {
        super("this is not your turn");
    }
}
