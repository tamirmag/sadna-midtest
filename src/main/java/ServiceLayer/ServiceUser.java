package ServiceLayer;


public class ServiceUser {
    private String username;
    private String password;
    private String email;
    private ServiceWallet wallet;
    private int league;
    private int numOfGames;
    private int grossProfit; //The total gross profit
    private int highestCashGained; //The highest cash gain in a game.
    private int totalCashGain;

    public ServiceUser(String username, String password, String email, ServiceWallet wallet, int league, int numOfGames, int grossProfit, int highestCashGained, int totalCashGain) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.wallet = wallet;
        this.league = league;
        this.numOfGames = numOfGames;
        this.grossProfit = grossProfit;
        this.highestCashGained = highestCashGained;
        this.totalCashGain = totalCashGain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ServiceWallet getWallet() {
        return wallet;
    }

    public void setWallet(ServiceWallet wallet) {
        this.wallet = wallet;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    public int getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(int grossProfit) {
        this.grossProfit = grossProfit;
    }

    public int getHighestCashGained() {
        return highestCashGained;
    }

    public void setHighestCashGained(int highestCashGained) {
        this.highestCashGained = highestCashGained;
    }

    public int getTotalCashGain() { return totalCashGain; }

    public void setTotalCashGain(int totalCashGain) { this.totalCashGain = totalCashGain; }
}
