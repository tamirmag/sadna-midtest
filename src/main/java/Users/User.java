package Users;

import Games.Player;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private int league;
    private Wallet wallet;
    private int numOfGames;
    // private boolean isHighestRanking;
    private ArrayList<Player> existingPlayers = new ArrayList<>();
    // private ArrayList<String> favoriteTurns = new ArrayList<>();

    public User(String username, String password, int league, String email, Wallet wallet) {
        this.username = username;
        this.password = password;
        this.league = league;
        this.email = email;
        this.wallet = wallet;
        this.numOfGames = 0;
    }

    @Override
    public String toString() {
        return "username: " + username + " ,password:" + password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public void getCharToPrint(char c) {
        System.out.print(c);
    }

    public ArrayList<Player> getExistingPlayers() {
        return existingPlayers;
    }

    public void setExistingPlayers(ArrayList<Player> existingPlayers) {
        this.existingPlayers = existingPlayers;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object other) {
        return username.equals(((User) other).username) && password.equals(((User) other).password);
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    //public boolean isHighestRanking() {return isHighestRanking;}

    // public void setHighestRanking(boolean highestRanking) {isHighestRanking = highestRanking;}

       /* public ArrayList<String> getFavoriteTurns() {
        return favoriteTurns;
    }

    public void setFavoriteTurns(ArrayList<String> favoriteTurns) {
        this.favoriteTurns = favoriteTurns;
    }
    */
}