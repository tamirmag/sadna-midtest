package Games;


import Users.NoMuchMany;
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

    public void join(Player player) throws NoMuchMany, CantJoin {
        player.wallet.sub(cost);
        this.policy.join(player);
    }

    @Override
    public boolean canJoin(User user) {
        if(user.getWallet().getAmountOfMoney() > cost)
           return super.canJoin(user);
        else
            return false;
    }
}
