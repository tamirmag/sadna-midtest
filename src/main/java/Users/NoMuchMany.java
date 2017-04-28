package Users;

/**
 * Created by tamir on 26/04/2017.
 */
public class NoMuchMany extends Exception{

    public NoMuchMany(int total, int num) {
        super("have only " + total + " can't sub " + num);
    }
}
