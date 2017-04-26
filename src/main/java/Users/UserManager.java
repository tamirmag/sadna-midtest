package Users;

import Games.ActiveGamesManager;
import Games.IGame;
import Games.Player;
import Games.Preferences;
import Loggers.ActionLogger;
import Loggers.ActiveGamesLogManager;
import Loggers.FinishedGamesManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
//import java.util.Scanner;


public class UserManager {

    // private static Scanner scanner = new Scanner(System.in);
    private User user;

    public UserManager(User one) {
        user = one;
    }

    public void logout() throws UserNotExists, AlreadyLoggedOut {
        AccountManager.getInstance().logout(user);
    }

    public void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists {
      /*  Scanner c = new Scanner(System.in);
        while (true) {
            System.out.println("what would you like to edit? (press 0 if nothing):");
            System.out.println("1) your username");
            System.out.println("2) your email");
            System.out.println("3) your password");
            String ans = c.next();

            if (ans.compareTo("0") == 0) break;
            else {
                switch (ans) {
                    case "1":
                        editUsername();
                        break;
                    case "2":
                        editEmail();
                        break;
                    case "3":
                        editPassword();
                        break;
                    default:
                        System.out.println("your choice is not valid. please try again:");
                        break;
                }
            }
        }*/

        editUsername(username);
        editEmail(email);
        editPassword(password);

    }

    private void editUsername(String username) throws UsernameNotValid, UserAlreadyExists {
     /*   Scanner c = new Scanner(System.in);
        System.out.println("please insert your username:");
        String username = c.next();
        while (LoginManager.getInstance().isUserExists(username) || username.equals("")) {
            System.out.println("this username already exists. please insert another username and try again.");
            System.out.println("please insert your username:");
            username = c.next();
        }*/
        if (username == null || username.equals("") || username.contains(" "))
            throw new UsernameNotValid(username);
        else if ((!user.getUsername().equals(username)) &&
                AccountManager.getInstance().isUserExists(username)) throw new UserAlreadyExists(username);
        else {
            String prior = user.getUsername();
            AccountManager.getInstance().setUsername(user, username);
            user.setUsername(username);
            ActionLogger.getInstance().writeToFile(prior+" successfully changed his username to "+username);
            System.out.println("username successfully changed.");
        }
    }

    private void editEmail(String email) throws EmailNotValid {
        /*Scanner c = new Scanner(System.in);
        System.out.println("please insert your email:");
        String email = c.next();
        while (!LoginManager.getInstance().isValidEmail(email)) {
            System.out.println("this email is not valid. please try again.");
            System.out.println("please insert your email:");
            email = c.next();
        }
        */
        if (AccountManager.getInstance().isValidEmail(email)) {
            String prior= user.getUsername();
            AccountManager.getInstance().setEmail(user, email);
            user.setEmail(email);
            ActionLogger.getInstance().writeToFile(prior+" successfully changed his email to "+email);
            System.out.println("email successfully changed.");
        } else throw new EmailNotValid(email == null? "null" :email);
    }

    private void editPassword(String password) throws PasswordNotValid {
        /*Scanner c = new Scanner(System.in);
        System.out.println("please insert your password:");
        String password = c.next();
        while (password.equals("")) {
            System.out.println("this password is not valid. please try again.");
            System.out.println("please insert your password:");
            password = c.next();
        }
        */
        if (password == null || password.equals("") || password.contains(" "))
            throw new PasswordNotValid(password == null? "null" : password);
        else {
            String prior = user.getUsername();
            AccountManager.getInstance().setPassword(user, password);
            user.setPassword(password);
            System.out.println("password successfully changed.");
            ActionLogger.getInstance().writeToFile(prior+" successfully changed his username to "+password);
        }

    }

    public void addPlayer(Player p) {
        user.getExistingPlayers().add(p);
    }

    public void addFavoriteTurn(String turn) {
        user.getFavoriteTurns().add(turn);
        ActionLogger.getInstance().writeToFile(user.getUsername()+" added a new favorite turn");
    }

    public void viewReplay(int gameNumber) {

        ArrayList<String>replay =  FinishedGamesManager.getInstance().viewReplay(gameNumber);
        for(String s : replay)
            for( char c : s.toCharArray())
            {
                user.getCharToPrint(c);
            }
    }

    public void spectateGame(int gameNumber) {
        ActiveGamesLogManager.getInstance().spectateGame(gameNumber, user);
    }

    public void CreateGame(String type, Preferences pref) {
        ActiveGamesManager.getInstance().createGame(this.user,type,pref);
        Player p = new Player(this.user.getUsername() , this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername()+" created a new game");
    }


    public ArrayList<IGame> findActiveGamesByPlayerName(String playerName) {

      /*  int counter = 1;
        System.out.println("please choose player number form the list below : ");

        for (Player p : user.getExistingPlayers()) {
            System.out.println(counter + ") " + p.getName());
            counter++;
        }
        String ans = scanner.next();
        while (!isInteger(ans) || Integer.parseInt(ans) > counter - 1) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/
        return new ArrayList<>(ActiveGamesManager.getInstance().findActiveGamesByPlayer(playerName));

    }


    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {

        /*int counter = 1;
        System.out.println("please insert pot size : ");

        String ans = scanner.next();
        while (!isInteger(ans) || Integer.parseInt(ans) < 0) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/

        return ActiveGamesManager.getInstance().findActiveGamesByPotSize(potSize);
        //return  new ArrayList<Game> ();
    }


    public ArrayList<IGame> findActiveGamesBySpectatingPolicy(boolean spectatingAllowed) {
        /*System.out.println("viewing the game without playing is allowed or not? (insert y/n) :");
        String ans = scanner.next();
        while (!(ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n"))) {
            System.out.println("your choose is not valid. please try again:");
            ans = scanner.next();
        }*/
        throw new NotImplementedException();
    }


    public ArrayList<IGame> findActiveGamesByMinPlayersPolicy(int minimal) {
      /*  System.out.println("please insert minimal and maximal amount of players:(minimal first, separated by comma):");
        String ans = scanner.next();
        String[] choose = ans.split(",");
        int min, max;
        while (choose.length < 2
                || !isInteger(choose[0])
                || !isInteger(choose[1])
                || ((max = Integer.parseInt(choose[1])) < (min = Integer.parseInt(choose[0])))) {
            System.out.println("your choice is not valid. please try again: ");
            System.out.println("please insert minimal and maximal amount of players:(minimal first, separated by comma):");
            ans = scanner.next();
            choose = ans.split(",");
        }*/

        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimal);
    }

    public ArrayList<IGame> findActiveGamesByMaxPlayersPolicy(int maximal){
        return ActiveGamesManager.getInstance().findActiveGamesByPlayersMaximumPolicy(maximal);
    }


    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {
        /*System.out.println("please insert minimum bet : ");
        String ans = scanner.next();
        int choose;
        while (!isInteger(ans) || ((choose = Integer.parseInt(ans)) < 0)) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/

        return ActiveGamesManager.getInstance().findActiveGamesByMinimumBetPolicy(minimumBet);
    }


    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {
      /*  System.out.println("please choose the amount of chips each player was given(insert 0 for real currency) ");
        String ans = scanner.next();
        int choose;

        while (!isInteger(ans) || ((choose = Integer.parseInt(ans)) < 0)) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/
        return ActiveGamesManager.getInstance().findActiveGamesByChipPolicy(numOfChips);

    }


    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {
        /*System.out.println("please choose the cost of joining the game");
        String ans = scanner.next();
        int choose;

        while (!isInteger(ans) || ((choose = Integer.parseInt(ans)) < 0)) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/
        return ActiveGamesManager.getInstance().findActiveGamesByBuyInPolicy(costOfJoin);

    }



    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {
      /*  int counter = 1;
        String[] opts = new String[]{"limit", "no-limit", "pot limit"};

        System.out.println("please insert game preference number from the list below: ");
        for (String s : opts) {
            System.out.println(counter + ") " + s);
            counter++;
        }

        String ans = scanner.next();
        while (!isInteger(ans) || Integer.parseInt(ans) > counter - 1) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }*/
        return ActiveGamesManager.getInstance().findActiveGamesByGameTypePolicy(gameTypePolicy);

    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public void JoinGame(int gameNumber)
    {
        ActiveGamesManager.getInstance().JoinGame(gameNumber, this.user);
        Player p = new Player(this.user.getUsername() , this.user.getWallet());
        addPlayer(p);
        ActionLogger.getInstance().writeToFile(user.getUsername()+" joined to game " +gameNumber);
    }
}


    /*public ArrayList<Game> findActiveGamesByGamePref() {
        int counter = 1;
        String[] opts = new String[]{
                "game type policy",
                "buy-in policy",
                "chip policy",
                "minimum bet",
                "amount of players in table",
                "spectating game allowed"
        };

        System.out.println("please insert game preference number from the list below: ");
        for (String s : opts) {
            System.out.println(counter + ") " + s);
            counter++;
        }

        String ans = scanner.next();
        while (!isInteger(ans) || Integer.parseInt(ans) > counter - 1) {
            System.out.println("your choice is not valid. please try again:");
            ans = scanner.next();
        }

        int choose = Integer.parseInt(ans);
        switch (choose) {
            case 1:
                return findActiveGamesByGameTypePolicy();

            case 2:
                return findActiveGamesByBuyInPolicy();

            case 3:
                return findActiveGamesByChipPolicy();

            case 4:
                return findActiveGamesByMinimumBetPolicy();

            case 5:
                return findActiveGamesByPlayersPolicy();

            case 6:
                return findActiveGamesBySpectatingPolicy();

        }

        throw new NotImplementedException();
        //return  new ArrayList<Game> ();
    }*/

        /*public void addEmail(String email) throws EmailNotValid {
        Scanner s = new Scanner(System.in);
        System.out.println("please insert your email:");
        String email = s.next();
        while (!LoginManager.getInstance().isValidEmail(email)) {
            System.out.println("this email is not valid, please insert it again");
            System.out.println("please insert your email:");
            email = s.next();
        }
        if (!LoginManager.getInstance().isValidEmail(email))
            user.setEmail(email);
        else throw new EmailNotValid();

    }*/

