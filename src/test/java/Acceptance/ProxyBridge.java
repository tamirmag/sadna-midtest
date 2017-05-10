package Acceptance;

import java.util.ArrayList;

import Games.CantJoin;
import Games.NotAllowedNumHigh;
import ServiceLayer.IServiceClass;
import ServiceLayer.ServiceUser;
import Users.*;

public class ProxyBridge implements Bridge{

	private IServiceClass real;
	
	public ProxyBridge() {
		real = null;
	}

	public void setRealBridge(IServiceClass implementation) {
		if (real == null)
			real = implementation;
	}

	@Override
	public ServiceUser register(String username, String password, String email, int wallet)
			throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
		return real.register(username, password, email, wallet);
	}

	@Override
	public ServiceUser login(String username, String password)
			throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, 
			UsernameNotValid, PasswordNotValid, UserNotExists {
		return real.login(username, password);
	}

	@Override
	public void logout(String username) throws UserNotExists, AlreadyLoggedOut {
		real.logout(username);
	}

	@Override
	public ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
		return real.findSpectatableGames(username);
	}

	@Override
	public int createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy, int minimumBet,
			int minimalAmountOfPlayers, int maximalAmountOfPlayers, boolean spectatingMode)
			throws UserNotLoggedIn, UserNotExists {
		return real.createGame(username, gameType, BuyInPolicy, ChipPolicy,
				minimumBet, minimalAmountOfPlayers, maximalAmountOfPlayers, spectatingMode);
	}

	@Override
	public void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, CantJoin, NoMuchMoney {
		real.joinGame(gamenum, username);
		
	}

	@Override
	public void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
		real.spectateGame(gamenum, username);
	}

	@Override
	public ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
		return real.viewReplay(gamenum, username);
	}

	@Override
	public void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists {
		real.saveFavoriteTurn(gamenum, username, turn);
	}

	@Override
	public ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username)
			throws UserNotLoggedIn, UserNotExists {
		return real.findActiveGamesByPotSize(potSize, username);
	}

	@Override
	public void setDefaultLeague(String username, int defaultLeague)
			throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
		real.setDefaultLeague(username, defaultLeague);
		
	}

	@Override
	public void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
		real.setCriteria(username, criteria);
	}

	@Override
	public void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists,
			LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
		real.moveToLeague(username, userToMove, league);
		
	}

	@Override
	public void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney {
		real.check(username, gameID);
	}

	@Override
	public void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney {
		real.bet(username, gameID, amount);
	}

	@Override
	public void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney {
		real.raise(username, gameID, amount);
	}

	@Override
	public void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney {
		real.allIn(username, gameID);
	}

	@Override
	public void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists {
		real.fold(username, gameID);
	}

	@Override
	public void terminateGame(int gameID) {
		real.terminateGame(gameID);
	}

	@Override
	public void clearLoggedInUsers() {
		real.clearLoggedInUsers();
	}

	@Override
	public void clearUsers() {
		real.clearUsers();
	}

	@Override
	public void clearAllFinishedGameLogs() {
		real.clearAllFinishedGameLogs();
	}


}
