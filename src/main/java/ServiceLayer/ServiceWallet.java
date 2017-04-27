package ServiceLayer;


public class ServiceWallet {
    private int amountOfMoney=0;

    public ServiceWallet(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public int getAmountOfMoney() { return amountOfMoney; }

    public void setAmountOfMoney(int amountOfMoney) { this.amountOfMoney = amountOfMoney; }

    public void add(int amount){
        this.amountOfMoney += amount;
    }

    public void sub(int amount){
        this.amountOfMoney -= amount;
    }
}
