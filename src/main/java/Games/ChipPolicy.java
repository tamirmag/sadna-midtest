package Games;

import Users.NoMuchMoney;
import Users.Wallet;

public class ChipPolicy extends Policy{

    int chips;

    public ChipPolicy(IGame policy, int chips) {
        this.policy = policy;
        this.chips = chips;
        this.policy.setChipNum(chips);
        this.id = this.policy.getId();
    }

    @Override
    public int getChips() {
        return chips;
    }

    @Override
    public void join(Player player) throws CantJoin, NoMuchMoney {
        player.wallet = new Wallet(chips);
         this.policy.join(player);
    }

}

