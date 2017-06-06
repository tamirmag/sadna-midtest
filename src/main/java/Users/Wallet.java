package Users;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Wallet implements IWallet {
    private int amountOfMoney=0;
    @Id
    private String id = new ObjectId().toString();

    public Wallet()
    {

    }
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
    public void sub(int amount) throws NoMuchMoney {
        if(amount > getAmountOfMoney())
            throw new NoMuchMoney(getAmountOfMoney(), amount);
        this.amountOfMoney -= amount;
    }
}
