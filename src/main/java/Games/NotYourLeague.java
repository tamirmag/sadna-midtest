package Games;

/**
 * Created by tamir on 04/06/2017.
 */
public class NotYourLeague extends Exception{

    public NotYourLeague(int game, int user){
        super("the game league is "+ game + " and it's not match your league "+ user);
    }
}
