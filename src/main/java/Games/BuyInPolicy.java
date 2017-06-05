package Games;

import Users.NoMuchMoney;
import Users.User;

public class BuyInPolicy extends Policy{

    int cost;

    public BuyInPolicy(IGame policy, int cost) {
        this.policy = policy;
        this.cost = cost;
        this.id = this.policy.getId();
    }


    @Override
    public int getBuyIn() {
        return cost;
    }

    public void join(Player player) throws NoMuchMoney, CantJoin {
        player.wallet.sub(cost);
        this.policy.join(player);
    }

    @Override
    public boolean canJoin(User user) throws NotYourLeague {
        if(user.getWallet().getAmountOfMoney() > cost)
           return super.canJoin(user);
        else
            return false;
    }
}
