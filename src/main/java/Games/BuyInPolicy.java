package Games;


public class BuyInPolicy extends Policy{

    int cost;

    public BuyInPolicy(IGame policy, int cost) {
        this.policy = policy;
        this.cost = cost;
    }


    @Override
    public int getBuyIn() {
        return cost;
    }

    public boolean join(Player player)
    {
        if(this.policy.isLocked())
            return false;
        player.wallet.sub(cost);
        return this.policy.join(player);
    }
}
