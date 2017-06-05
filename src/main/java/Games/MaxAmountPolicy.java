package Games;

import Users.NoMuchMoney;
import Users.User;

/**
 * Created by tamir on 16/04/2017.
 */
public class MaxAmountPolicy extends Policy{

    int maxAmount;

    public MaxAmountPolicy(IGame policy, int maxAmount) {

        this.policy = policy;
        this.maxAmount = maxAmount;
    }


    public void join(Player player) throws CantJoin, NoMuchMoney {
        if(this.policy.getPlayersNum() >= maxAmount) throw new CantJoin(this.policy.getId() ,player.getName());
        this.policy.join(player);
    }

    public boolean inMax(){
        return this.policy.getPlayersNum()<maxAmount;
    }

    public int getMaxPlayers() {
        return maxAmount;
    }

    @Override
    public boolean canJoin(User user) throws NotYourLeague {
        if(this.policy.getPlayersNum() < maxAmount)
            return super.canJoin(user);
        else
            return false;
    }
}
