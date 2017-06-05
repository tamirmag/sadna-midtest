package Games;

import Users.NoMuchMoney;

/**
 * Created by tamir on 16/04/2017.
 */
public class MinAmountPolicy extends Policy{

    int minAmount;


    public MinAmountPolicy(IGame policy, int minAmount) {

        this.policy = policy;
        this.minAmount = minAmount;
    }


    @Override
    public int getMinPlayers() {
        return minAmount;
    }

    public void startGame() throws NoMuchMoney, NotYourTurn {
        if(this.policy.getPlayersNum() >= minAmount)
            this.policy.startGame();
    }



}
