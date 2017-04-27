package Users;

/**
 * Created by tamir on 26/04/2017.
 */
public class NoMutchMany extends Exception{

    public NoMutchMany(int total, int num) {
        super("have only " + total + " can't sub " + num);
    }
}
