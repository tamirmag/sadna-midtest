package Games;

import java.util.ArrayList;

/**
 * Created by tamir on 16/04/2017.
 */
public class PotLimitHoldem extends Game{

    public PotLimitHoldem(ArrayList<Player> players, int id)
    {

        super(players, id, "PotLimitHoldem");
    }

    @Override
    public void raise(int amount, Player player) {
        if(amount<= super.getPot())
            super.raise(amount, player);
    }
}