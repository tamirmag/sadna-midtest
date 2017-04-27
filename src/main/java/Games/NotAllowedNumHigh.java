package Games;

/**
 * Created by tamir on 26/04/2017.
 */
public class NotAllowedNumHigh extends Exception{
    public NotAllowedNumHigh(int num) {
        super("amount not alowd, the max amount is " + num);
    }
}
