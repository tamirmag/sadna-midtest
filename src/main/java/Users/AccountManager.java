package Users;

import Loggers.ActionLogger;
import java.util.ArrayList;
import java.util.Hashtable;



public class AccountManager {

    private int defaultLeague;
    private ArrayList<User> users;
    private ArrayList<User> loggedInUsers;
    private Hashtable<Integer, ArrayList<User>> leagues;
    private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static AccountManager instance;
    private int maximalRank;

    private AccountManager() {

        users = new ArrayList<User>();
        loggedInUsers = new ArrayList<User>();
        leagues = new Hashtable<Integer, ArrayList<User>>();
        maximalRank = 0;
        leagues.put(defaultLeague,new ArrayList<User>());
    }

    public static AccountManager getInstance() {
        if (instance == null) instance = new AccountManager();
        return instance;
    }

    public boolean isUserExists(String username) {
      /*  try(Reader reader = new InputStreamReader(LoginManager.class.getResourceAsStream("sample2.json"), "UTF-8")) {
            Gson g = new GsonBuilder().create();
            LoginManager u = g.fromJson(reader, LoginManager.class);
            System.out.println( Arrays.toString(u.users.toArray()));

        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //return !filterUsers(usernameFilter(username)).isEmpty();

        for(User u : users)
        {
            if(u.getUsername().equals(username)) return true;
        }
        return false;
    }

    public User getUser(String username) {
      /*  User u = null;
        try
        {
            u= filterUsers(usernameFilter(username)).get(0);
        }
        catch (IndexOutOfBoundsException ex)
        {
            return null;
        }
        return u;
        */

      for(User u : users)
      {
          if(u.getUsername().equals(username)) return u;

      }
      return null;
    }

    public UserManager register(String username, String password, String email, int wallet)
            throws UserAlreadyExists, PasswordNotValid, EmailNotValid, NegativeValue, UsernameNotValid {

       /* Scanner s = new Scanner(System.in);
        System.out.println("please insert your username:");
        String username = s.next();
        while (isUserExists(username)) {
            System.out.println("this username already exists. please insert another username and try again.");
            System.out.println("please insert your username:");
            username = s.next();
        }
        System.out.println("please insert your password :");
        String password = s.next();

        while (password.equals("") || password == null) {
            System.out.println("you are required to insert a new password. an empty password is not valid.");
            System.out.println("please insert your password:");
            password = s.next();
        }*/

        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if (isUserExists(username)) throw new UserAlreadyExists(username);
        else if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null? "null" : password);
        else if (!isValidEmail(email))
            throw new EmailNotValid(email == null? "null" : email);
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
            ActionLogger.getInstance().writeToFile("user "+username+" successfully registered.");
            System.out.println("UserTests successfully registered.");
            return new UserManager(u);
        }

    }

    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public void logout(User u) throws AlreadyLoggedOut, UserNotExists {
        if(u == null) throw new UserNotExists("null");
        else if (!users.contains(u)) throw new UserNotExists(u.getUsername());
        else if (!loggedInUsers.contains(u)) throw new AlreadyLoggedOut(u.getUsername());
        else {
            loggedInUsers.remove(u);
            maximalRank = getMaximalRank();
            ActionLogger.getInstance().writeToFile(u.getUsername()+" successfully logged out.");
            System.out.println("you have successfully logged out.");
        }
        //System.out.println("you have successfully logged out.");
        //else System.out.println("you are already logged out");
    }

    public UserManager login(String username, String password)
            throws UsernameNotValid, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists {
       /* Scanner c = new Scanner(System.in);
        System.out.println("welcome!");
        System.out.println("please insert your username:");
        String username = c.next();
        System.out.println("please insert your password:");
        String password = c.next();

        while (!isUserExists(username) || !isPasswordCorrect(username, password)) {
            System.out.println("username or password are not correct. please insert them again:");
            System.out.println("please insert your username:");
            username = c.next();
            System.out.println("please insert your password:");
            password = c.next();
        }

        UserTests u = getUser(username);
        System.out.println("welcome " + username);
        ActionLogger.getInstance().writeToFile("UserTests " + username + " successfully logged in.");*/
       User user = null;
        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if(( user = getUser(username)) == null) throw new UserNotExists(username);
        else if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null? "null" : password);
        else if (!isPasswordCorrect(username, password))
            throw new UsernameAndPasswordNotMatch(username,password);
        else if (loggedInUsers.contains(getUser(username)))
            throw new AlreadyLoggedIn(username);
        else {
            User u = getUser(username);
            loggedInUsers.add(u);
            if (u.getLeague() > maximalRank) {
                HighestRankingUser u1 = new HighestRankingUser(u);
                return new UserManager(u1);
            }
            ActionLogger.getInstance().writeToFile(u.getUsername()+" successfully logged in.");
            return new UserManager(getUser(username));
        }

    }

    public void addUser(User u) throws UserAlreadyExists, UsernameNotValid {
        String username= u.getUsername();
        if(username == null || username.equals("") || username.contains(" ")) throw new UsernameNotValid(username);
        else if (users.contains(u)) throw new UserAlreadyExists(u.getUsername());
        else users.add(u);

    }

    public void addLoggedInUser(User u) throws AlreadyLoggedIn {
        if (loggedInUsers.contains(u)) throw new AlreadyLoggedIn(u.getUsername());
        else loggedInUsers.add(u);
    }

    public void setUsername(User u, String username) {
        users.get(users.indexOf(u)).setUsername(username);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setUsername(username);
        ActionLogger.getInstance().writeToFile(u.getUsername()+" successfully logged out.");
    }

    public void setEmail(User u, String email) {
        users.get(users.indexOf(u)).setEmail(email);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setEmail(email);
    }

    public void setPassword(User u, String password) {
        users.get(users.indexOf(u)).setPassword(password);
        loggedInUsers.get(loggedInUsers.indexOf(u)).setPassword(password);
    }

    public void addUserToLeague(User u) throws UserAlreadyInLeague {
        int league = u.getLeague();
        if (leagues.get(league) != null && leagues.get(league).contains(u)) throw new UserAlreadyInLeague(u.getUsername(),league);
        else if (!leagues.containsKey(league) || leagues.get(league) == null) {
            leagues.put(league, new ArrayList<User>());
            leagues.get(league).add(u);
        } else leagues.get(league).add(u);
        ActionLogger.getInstance().writeToFile(u.getUsername()+" was moved to league "+league);
    }

    public void removeUserFromLeague(User u) throws UserNotInLeague, LeagueNotExists {
        //throws UserNotInLeague, LeagueNotExists {
        int league = u.getLeague();
        if (!leagues.containsKey(league) || leagues.get(league) == null) throw new LeagueNotExists(league);
        else if (!leagues.get(league).contains(u)) throw new UserNotInLeague(u.getUsername(),league);
        else leagues.get(league).remove(u);
        ActionLogger.getInstance().writeToFile(u.getUsername()+" was removed from league "+league);
    }

    public void setDefaultLeague(int league) throws NegativeValue {
        if (league < 0) throw new NegativeValue(league);
        else {
            moveUsersFromDefaultLeague(league);
            defaultLeague = league;
        }
    }

    public int getDefaultLeague() {
        return defaultLeague;
    }

    private void moveUsersFromDefaultLeague(int newLeague) throws NegativeValue {
        if (newLeague < 0) throw new NegativeValue(newLeague);
        else {
            if(leagues.get(defaultLeague)==null) leagues.put(defaultLeague,new ArrayList<User>());
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

        for(User u :users)
        {
            if(u.getUsername().equals(username))
            {
                if(u.getPassword().equals(password)) return true;
            }
        }
        return false;
    }



    public void clearUsers() {
        users.clear();
    }

    public void clearLoggedInUsers() {
        loggedInUsers.clear();
    }

    public void clearLeagues(){leagues.clear();}

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


}


