package Games;

/**
 * Created by tamir on 28/04/2017.
 */
public class CantJoin extends Exception {

    public CantJoin(int id, String userName) {
        super(userName + " cant join game " + id);
    }
}
