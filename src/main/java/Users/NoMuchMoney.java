package Users;

/**
 * Created by tamir on 26/04/2017.
 */
public class NoMuchMoney extends Exception{

    public NoMuchMoney(int total, int num) {
        super("have only " + total + " can't sub " + num);
    }
}
