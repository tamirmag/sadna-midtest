package Games;

import Users.NoMuchMoney;

import java.util.ArrayList;

/**
 * Created by tamir on 16/04/2017.
 */
public class PotLimitHoldem extends Policy {

    public PotLimitHoldem(IGame policy) {
        this.policy = policy;
        this.id = this.policy.getId();
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn {
        if (amount <= super.getPot())
            policy.raise(amount, player);
        else
            throw new NotAllowedNumHigh(super.getPot());
    }
}