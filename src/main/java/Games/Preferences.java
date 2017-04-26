package Games;

/**
 * Created by tamir on 21/04/2017.
 */
public class Preferences {
    private int buyInPolicy = 0;
    private int chipPolicy = 0;
    private int maxAmountPolicy = 0;
    private int minAmountPolicy = 0;
    private int minBetPolicy = 0;
    private boolean SpectatePolicy = false;

    public int getBuyInPolicy() {
        return buyInPolicy;
    }

    public int getChipPolicy() {
        return chipPolicy;
    }

    public int getMaxAmountPolicy() {
        return maxAmountPolicy;
    }

    public int getMinAmountPolicy() {
        return minAmountPolicy;
    }

    public int getMinBetPolicy() {
        return minBetPolicy;
    }

    public boolean isSpectatePolicy() {
        return SpectatePolicy;
    }

    public void setBuyInPolicy(int buyInPolicy) {
        this.buyInPolicy = buyInPolicy;
    }

    public void setChipPolicy(int chipPolicy) {
        this.chipPolicy = chipPolicy;
    }

    public void setMaxAmountPolicy(int maxAmountPolicy) {
        this.maxAmountPolicy = maxAmountPolicy;
    }

    public void setMinAmountPolicy(int minAmountPolicy) {
        this.minAmountPolicy = minAmountPolicy;
    }

    public void setMinBetPolicy(int minBetPolicy) {
        this.minBetPolicy = minBetPolicy;
    }

    public void setSpectatePolicy(boolean spectatePolicy) {
        SpectatePolicy = spectatePolicy;
    }
}
