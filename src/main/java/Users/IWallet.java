package Users;

/**
 * Created by רועי on 4/26/2017.
 */
public interface IWallet {
    int getAmountOfMoney();

    void setAmountOfMoney(int amountOfMoney);

    void add(int amount);

    void sub(int amount) throws NoMuchMany;
}
