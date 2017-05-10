package Games;

import Users.NoMuchMoney;

import java.util.ArrayList;

/**
 * Created by tamir on 16/04/2017.
 */
public class PotLimitHoldem extends Game{

    public PotLimitHoldem(ArrayList<Player> players, int id, int legue)
    {

        super(players, id, "PotLimitHoldem", legue);
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney {
        if(amount<= super.getPot())
            super.raise(amount, player);
        else
            throw new NotAllowedNumHigh(super.getPot());
    }
}