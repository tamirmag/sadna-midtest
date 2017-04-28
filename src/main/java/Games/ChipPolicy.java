package Games;

import Users.Wallet;

public class ChipPolicy extends Policy{

    int chips;

    public ChipPolicy(IGame policy, int chips) {
        this.policy = policy;
        this.chips = chips;
        this.policy.setChipNum(chips);
    }

    @Override
    public int getChips() {
        return chips;
    }

    @Override
    public void join(Player player) {
        player.wallet = new Wallet(chips);
        return this.policy.join(player);
    }

}

