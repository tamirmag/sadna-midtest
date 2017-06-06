package Acceptance;

import java.util.ArrayList;

import Games.CantJoin;
import Games.NotAllowedNumHigh;
import Games.NotYourTurn;
import Games.SpectatingNotAllowed;
import ServiceLayer.IServiceClass;
import ServiceLayer.ServiceUser;
import Users.*;

public interface Bridge extends IServiceClass {

    ServiceUser register(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid;

    ServiceUser login(String username, String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists;

    void logout(String username) throws UserNotExists, AlreadyLoggedOut;

    ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists;

    int createGame(String username,String gameType,  int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists;

    void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, CantJoin, NoMuchMoney;

    void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed;

    ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists;

    void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn;

    void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn;

    void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, NotYourTurn;

    void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn;

    void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn;

    ArrayList<Integer> findActiveGamesByGameTypePolicy(String username,String gameTypePolicy) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByMinimumBetPolicy(String username,int minimumBet) throws UserNotLoggedIn, UserNotExists ;

    ArrayList<Integer> findActiveGamesByMinPlayersPolicy(String username,int minPlayers) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByMaxPlayersPolicy(String username,int maxPlayers) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByChipPolicy(String username,int numOfChips) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByBuyInPolicy(String username,int costOfJoin) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByPlayerName(String username,String playerName) throws UserNotLoggedIn, UserNotExists;
}

//void setDefaultLeague(String username, int defaultLeague) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking;

//void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking;

//void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists, LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking;
//void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists;