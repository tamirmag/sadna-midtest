package Games;

import Users.NoMuchMoney;

/**
 * Created by tamir on 16/04/2017.
 */
public class MinBetPolicy extends Policy{

    int minimumBet;

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney, NotYourTurn {
        if(amount >= minimumBet)
            this.policy.call(amount,player);
    }

    public MinBetPolicy(IGame policy, int minimumBet) {
        this.policy = policy;
        this.minimumBet = minimumBet;
        setMinimumBet(minimumBet);
        this.id = this.policy.getId();


    }


}
