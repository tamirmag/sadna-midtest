package Users;


public class Wallet implements IWallet {
    private int amountOfMoney=0;

    public Wallet(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    @Override
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
    @Override
    public void add(int amount){
        this.amountOfMoney += amount;
    }

    @Override
    public void sub(int amount){
        this.amountOfMoney -= amount;
    }
}
