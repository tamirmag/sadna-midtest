package Games;

/**
 * Created by tamir on 06/06/2017.
 */
public class NotLegalAmount extends Exception {

    public NotLegalAmount(int amount, int minimum) {
        super("the minimum amount to bet is " + minimum + " your " + amount + "bet is denaied");
    }
}
