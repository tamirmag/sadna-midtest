package Games;
/**
 * Created by tamir on 16/04/2017.
 */
public class MaxAmountPolicy extends Policy{

    int maxAmount;

    public MaxAmountPolicy(IGame policy, int maxAmount) {
        this.policy = policy;
        this.maxAmount = maxAmount;
    }


    public boolean join(Player player)
    {
        if(this.policy.getPlayersNum() >= maxAmount)
            return false;
        return this.policy.join(player);
    }

    public boolean inMax(){
        return this.policy.getPlayersNum()<maxAmount;
    }

    public int getMaxPlayers() {
        return maxAmount;
    }
}
