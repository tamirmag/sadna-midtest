package Games;

import Loggers.ActiveGamesLogManager;
import Users.User;

/**
 * Created by tamir on 16/04/2017.
 */
public class SpectatePolicy extends Policy{

    boolean spectate;

    public SpectatePolicy(IGame policy, boolean spectate) {
        this.policy = policy;
        this.spectate = spectate;
    }

    @Override
    public boolean spectaAble(){
        return spectate;
    }

    @Override
    public void spectateGame(User user) {
        ActiveGamesLogManager.getInstance().spectateGame(this.policy.getId(), user);
    }
}
