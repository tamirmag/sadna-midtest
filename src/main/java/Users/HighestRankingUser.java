package Users;


import Loggers.ActionLogger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class HighestRankingUser extends User {
    public HighestRankingUser(String username, String password,int league,String email,Wallet wallet) {
        super(username, password,league,email,wallet);
    }
    public HighestRankingUser(User u) {
        super(u.getUsername(), u.getPassword(),u.getLeague(),u.getEmail(),u.getWallet());
    }

    public void moveUserToLeague(String username,int toLeague )
            throws UserAlreadyInLeague, NegativeValue, UserNotInLeague, LeagueNotExists {
     /*   Scanner scanner = new Scanner(System.in);
        System.out.println("please enter username of user (insert 0 if you want to leave):");
        String username = scanner.next();
        while ((!username.equals("0")) && (!LoginManager.getInstance().isUserExists(username))) {
            System.out.println("username does not exists, please try again or insert 0 to leave:");
            username = scanner.next();
        }

        if (!username.equals("0")) {

            UserTests u = LoginManager.getInstance().getUser(username);
            LoginManager.getInstance().removeUserFromLeague(u);
            System.out.println("UserTests " + u.getUsername() + " was removed from league " + u.getLeague());
            ActionLogger.getInstance().writeToFile("UserTests " + u.getUsername() + " was removed from league " + u.getLeague());

            System.out.println("what league you want to add the user to?");
            String league = scanner.next();

            while (!isNumeric(league)) {
                System.out.println("please insert numeric value. try again: ");
                league = scanner.next();
            }
            */
        if(toLeague <0) throw new NegativeValue(toLeague);
        User u = AccountManager.getInstance().getUser(username);
        int formerLeague = AccountManager.getInstance().getUser(username).getLeague();
        if(formerLeague == toLeague) throw new UserAlreadyInLeague(username,toLeague);
        AccountManager.getInstance().removeUserFromLeague(u);
        AccountManager.getInstance().getUser(username).setLeague(toLeague);
        AccountManager.getInstance().addUserToLeague(u);
        System.out.println("Users " + u.getUsername() + " moved from league " + formerLeague + " to league " + toLeague);
        ActionLogger.getInstance().writeToFile("Users " + u.getUsername() + " moved from league " + formerLeague + " to league " + toLeague);

    }

    public void setDefaultLeague(int defaultLeague) throws NegativeValue {
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("what is the value of the new default league?");
        String league = scanner.next();

        while (!isNumeric(league)) {
            System.out.println("please insert numeric value. try again: ");
            league = scanner.next();
        }
        int toLeague = Integer.parseInt(league);
*/
        int formerDefaultLeague = AccountManager.getInstance().getDefaultLeague();
        AccountManager.getInstance().setDefaultLeague(defaultLeague);
        System.out.println("default league changed to " + defaultLeague + " and all users from " + formerDefaultLeague + " moved to it.");
        ActionLogger.getInstance().writeToFile("default league changed to " + defaultLeague + " by "+getUsername()+" and all users from " + formerDefaultLeague + " moved to it.");


    }

    public void setCriteria() throws NotImplementedException {
        throw new NotImplementedException();
    }

}
