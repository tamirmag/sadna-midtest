package Games;


import Users.NoMutchMany;
import Users.User;

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

    public boolean join(Player player) throws NoMutchMany {
        if(this.policy.isLocked())
            return false;
        player.wallet.sub(cost);
        return this.policy.join(player);
    }

    @Override
    public boolean canJoin(User user) {
        if(user.getWallet().getAmountOfMoney() > cost)
           return super.canJoin(user);
        else
            return false;
    }
}
