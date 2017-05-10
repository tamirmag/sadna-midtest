package Users;

import Loggers.ActionLogger;
import Loggers.IActionLogger;

import java.util.ArrayList;
import java.util.Hashtable;


public class AccountManager implements IAccountManager {

    private int defaultLeague;
    private ArrayList<User> users;
    private ArrayList<User> loggedInUsers;
    private Hashtable<Integer, ArrayList<User>> leagues;
    private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static IAccountManager instance ;
    private int maximalRank;

    private AccountManager() {

        users = new ArrayList<User>();
        loggedInUsers = new ArrayList<User>();
        leagues = new Hashtable<Integer, ArrayList<User>>();
        maximalRank = 0;
        leagues.put(defaultLeague, new ArrayList<User>());
    }

    public static IAccountManager getInstance()
    {
        if(instance==null)
        {
           instance = new AccountManager();
        }return instance;
    }
    @Override
    public boolean isUserExists(String username) {

        for (User u : users) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public User getLoggedInUser(String username) throws UserNotExists, UserNotLoggedIn {
        if(!isUserExists(username)) throw new UserNotExists(username);
        else if(!isUserLoggedIn(username)) throw new UserNotLoggedIn(username);
        else return getUser(username);

    }


    private User getUser(String username) {

        for (User u : users) {
            if (u.getUsername().equals(username)) return u;

        }
        return null;
    }

    @Override
    public UserManager register(String username, String password, String email, int wallet)
            throws UserAlreadyExists, PasswordNotValid, EmailNotValid, NegativeValue, UsernameNotValid {

        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if (isUserExists(username)) throw new UserAlreadyExists(username);
        else if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null ? "null" : password);
        else if (!isValidEmail(email))
            throw new EmailNotValid(email == null ? "null" : email);
        else if (wallet < 0)
            throw new NegativeValue(wallet);
        else {
            User u = new User(username, password, defaultLeague, email, new Wallet(wallet));
            if (leagues.get(defaultLeague) == null) {
                leagues.put(defaultLeague, new ArrayList<User>());
            }
            leagues.get(defaultLeague).add(u);
            users.add(u);
            loggedInUsers.add(u);
            IActionLogger.getInstance().writeToFile("user " + username + " successfully registered.");
            System.out.println("UserTests successfully registered.");
            return new UserManager(u);
        }

    }

    @Override
    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public void logout(User u) throws AlreadyLoggedOut, UserNotExists {
        if (u == null) throw new UserNotExists("null");
        else if (!users.contains(u)) throw new UserNotExists(u.getUsername());
        else if (!loggedInUsers.contains(u)) throw new AlreadyLoggedOut(u.getUsername());
        else {
            loggedInUsers.remove(u);
            maximalRank = getMaximalRank();
            ActionLogger.getInstance().writeToFile(u.getUsername() + " successfully logged out.");
            System.out.println("you have successfully logged out.");
        }

    }

    @Override
    public void logout(String username) throws UserNotExists, AlreadyLoggedOut {
        if (username == null) throw new UserNotExists("null");
        else if(!isUserExists(username)) throw new UserNotExists(username);
        else if(!isUserLoggedIn(username))throw new AlreadyLoggedOut(username);
        else {
            removeFromLoggedIn(username);
            maximalRank = getMaximalRank();
            ActionLogger.getInstance().writeToFile(username + " successfully logged out.");
        }
    }

    @Override
    public UserManager login(String username, String password)
            throws UsernameNotValid, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists {

        User user = null;
        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if ((user = getUser(username)) == null) throw new UserNotExists(username);
        else if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null ? "null" : password);
        else if (!isPasswordCorrect(username, password))
            throw new UsernameAndPasswordNotMatch(username, password);
        else if (loggedInUsers.contains(getUser(username)))
            throw new AlreadyLoggedIn(username);
        else {
            User u = getUser(username);
            loggedInUsers.add(u);
            if (u.getLeague() > maximalRank) {
                u.setHighestRanking(true);
            }
            ActionLogger.getInstance().writeToFile(u.getUsername() + " successfully logged in.");
            return new UserManager(u);
        }

    }

    @Override
    public void addUser(User u) throws UserAlreadyExists, UsernameNotValid {
        String username = u.getUsername();
        if (username == null || username.equals("") || username.contains(" ")) throw new UsernameNotValid(username);
        else if (users.contains(u)) throw new UserAlreadyExists(u.getUsername());
        else users.add(u);

    }

    @Override
    public void addLoggedInUser(User u) throws AlreadyLoggedIn {
        if (loggedInUsers.contains(u)) throw new AlreadyLoggedIn(u.getUsername());
        else loggedInUsers.add(u);
    }

    @Override
    public void setUsername(User u, String username) {
        users.get(users.indexOf(u)).setUsername(username);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setUsername(username);
        IActionLogger.getInstance().writeToFile(u.getUsername() + " successfully logged out.");
    }

    @Override
    public void setEmail(User u, String email) {
        users.get(users.indexOf(u)).setEmail(email);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setEmail(email);
    }

    @Override
    public void setPassword(User u, String password) {
        users.get(users.indexOf(u)).setPassword(password);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setPassword(password);
    }

  /*  @Override
    public void addUserToLeague(User u) throws UserAlreadyInLeague {
        int league = u.getLeague();
        if (leagues.get(league) != null && leagues.get(league).contains(u))
            throw new UserAlreadyInLeague(u.getUsername(), league);
        else if (!leagues.containsKey(league) || leagues.get(league) == null) {
            leagues.put(league, new ArrayList<User>());
            leagues.get(league).add(u);
        } else leagues.get(league).add(u);
        ActionLogger.getInstance().writeToFile(u.getUsername() + " was moved to league " + league);
    }

    @Override
    public void removeUserFromLeague(User u) throws UserNotInLeague, LeagueNotExists {
        int league = u.getLeague();
        if (!leagues.containsKey(league) || leagues.get(league) == null) throw new LeagueNotExists(league);
        else if (!leagues.get(league).contains(u)) throw new UserNotInLeague(u.getUsername(), league);
        else leagues.get(league).remove(u);
        ActionLogger.getInstance().writeToFile(u.getUsername() + " was removed from league " + league);
    }*/
    @Override
    public void moveUserToLeague(String username, int newLeague) throws UserNotInLeague, LeagueNotExists, UserAlreadyInLeague, UserNotExists {
        if(!isUserExists(username)) throw new UserNotExists(username);
        User u = getUser(username);
        int league = u.getLeague();
        if (newLeague == league) throw new UserAlreadyInLeague(username, newLeague);
        if (!leagues.containsKey(league) || leagues.get(league) == null) throw new LeagueNotExists(league);
        else if (!leagues.get(league).contains(u)) throw new UserNotInLeague(u.getUsername(), league);
        else leagues.get(league).remove(u);

        if (leagues.get(newLeague) != null && leagues.get(newLeague).contains(u))
            throw new UserAlreadyInLeague(username, newLeague);
        else if (!leagues.containsKey(newLeague) || leagues.get(newLeague) == null)
        {
            leagues.put(newLeague, new ArrayList<User>());
            leagues.get(newLeague).add(u);
        }
        else {
            leagues.get(newLeague).add(u);
        }

        ActionLogger.getInstance().writeToFile(username + " was moved to league " + newLeague);
        u.setLeague(newLeague);
    }

    @Override
    public void setDefaultLeague(int league) throws NegativeValue {
        if (league < 0) throw new NegativeValue(league);
        else {
            moveUsersFromDefaultLeague(league);
            defaultLeague = league;
        }
    }

    @Override
    public int getDefaultLeague() {
        return defaultLeague;
    }

    @Override
    public void clearUsers() {
        users.clear();
    }

    @Override
    public void clearLoggedInUsers() {
        loggedInUsers.clear();
    }

    @Override
    public void clearLeagues() {
        leagues.clear();
    }

    @Override
    public int getMaximalRank() {
        if (loggedInUsers.size() == 0) return maximalRank;
        else {
            int ret = loggedInUsers.get(0).getLeague();
            for (User u : loggedInUsers) {
                if (u.getLeague() > ret)
                    ret = u.getLeague();
            }
            return ret;
        }
    }

    private void moveUsersFromDefaultLeague(int newLeague) throws NegativeValue {
        if (newLeague < 0) throw new NegativeValue(newLeague);
        else {
            if (leagues.get(defaultLeague) == null) leagues.put(defaultLeague, new ArrayList<User>());
            ArrayList<User> move = new ArrayList<User>(leagues.get(defaultLeague));
            if (leagues.get(newLeague) == null) {
                leagues.put(newLeague, new ArrayList<User>());
            }
            for (User u : move) {
                leagues.get(newLeague).add(u);
                u.setLeague(newLeague);
            }
            leagues.get(defaultLeague).clear();
        }
    }

    private boolean isPasswordCorrect(String username, String password) {

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(password)) return true;
            }
        }
        return false;
    }

    private boolean isUserLoggedIn(String username)
    {
        for(User u : loggedInUsers)
        {
            if(u.getUsername().equals(username)) return true;
        }
        return false;
    }

    private void removeFromLoggedIn(String username)
    {
        for(User u : loggedInUsers)
        {
            if(u.getUsername().equals(username))
            {
                loggedInUsers.remove(u);
                break;
            }
        }
    }
}


