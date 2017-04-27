package ServiceLayer;


import Games.IGame;
import Games.Preferences;
import Users.*;

import java.util.ArrayList;

public class ServiceClass
{
    public ServiceUser register(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        IAccountManager.getInstance().register(username,password,email,wallet);
        int league = IAccountManager.getInstance().getDefaultLeague();
        return new ServiceUser(username, password, email, new ServiceWallet(wallet),league);
    }

    public ServiceUser login(String username,String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists {
        IUserManager u = IAccountManager.getInstance().login(username,password);
        int league = u.getUser().getLeague();
        ServiceWallet wallet = new ServiceWallet(u.getUser().getWallet().getAmountOfMoney());
        String email = u.getUser().getEmail();
        return new ServiceUser(username,password,email,wallet,league);
    }

    public void logout(String username) throws UserNotExists, AlreadyLoggedOut {
        IAccountManager.getInstance().logout(username);
    }

    public ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesBySpectatingPolicy(true);
        ArrayList<Integer> ret = new ArrayList<>();
        for(IGame i : games) ret.add(i.getId());
        return ret;
    }

    public int createGame(String username,String gameType,int BuyInPolicy,int ChipPolicy,
                             int minimumBet,int minimalAmountOfPlayers,
                             int maximalAmountOfPlayers,boolean spectatingMode) throws UserNotLoggedIn, UserNotExists
    {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        Preferences p = new Preferences();
        p.setBuyInPolicy(BuyInPolicy);
        p.setChipPolicy(ChipPolicy);
        p.setMinBetPolicy(minimumBet);
        p.setMinAmountPolicy(minimalAmountOfPlayers);
        p.setMaxAmountPolicy(maximalAmountOfPlayers);
        p.setSpectatePolicy(spectatingMode);
        u.CreateGame(gameType ,p);
        return 0;
    }
}
