package Games;
import Users.NoMuchMoney;

import java.util.ArrayList;

/**
 * Created by tamir on 16/04/2017.
 */
public class LimitHoldem extends Game{

    public LimitHoldem(ArrayList<Player> players, int id, int legue)
    {
        super(players, id, "LimitHoldem", legue);
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney {
        if(super.getTurn() < 2)
            super.raise(super.getMinimumBet(), player);
        else
            super.raise(amount, player);
    }

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney {
        if(super.getTurn() < 2)
            super.bet(super.getMinimumBet(), player);
        else
            super.bet(amount, player);
    }

}
