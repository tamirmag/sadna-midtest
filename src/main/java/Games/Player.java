package Games;

import Users.Wallet;

import java.util.ArrayList;


public class Player {
    String name;
    ArrayList<Card> hand = new ArrayList<Card>();
    public Wallet wallet;

    public Player(String name, Wallet wallet)
    {
        this.name = name;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}