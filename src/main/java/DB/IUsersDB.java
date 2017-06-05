package DB;

import Users.User;

import java.util.ArrayList;

public interface IUsersDB {
    static UsersDB getInstance()
    {
        return UsersDB.getInstance();
    }

    boolean isExistUser(String username);

    User getUser(String username);

    void register(User u);

    User login(String username);

    void logout(String username);

    boolean isUsernameAndPasswordMatch(String username, String password);

    boolean isLoggedIn(String username);

    void setEmail(String username, String email);

    void setPassword(String username, String password);

    void setUsername(String oldUsername, String newUsername);

    void moveUserToLeague(String username, int newLeague);

    ArrayList<Integer> allLeagues();

    ArrayList<User> allUsers();

    void organizeLeagues();

    void deleteAllUsers();

    void changeDataStore(String newDataStore);
}
