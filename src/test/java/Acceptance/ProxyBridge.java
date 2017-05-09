package Acceptance;

import java.util.ArrayList;

import ServiceLayer.ServiceUser;
import Users.AlreadyLoggedIn;
import Users.AlreadyLoggedOut;
import Users.EmailNotValid;
import Users.LeagueNotExists;
import Users.NegativeValue;
import Users.PasswordNotValid;
import Users.UserAlreadyExists;
import Users.UserAlreadyInLeague;
import Users.UserNotExists;
import Users.UserNotInLeague;
import Users.UserNotLoggedIn;
import Users.UsernameAndPasswordNotMatch;
import Users.UsernameNotValid;

public class ProxyBridge implements Bridge{

	private Bridge real;
	
	public ProxyBridge() {
		real = null;
	}

	public void setRealBridge(Bridge implementation) {
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
	public void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
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
			throws UserNotLoggedIn, UserNotExists, NegativeValue {
		real.setDefaultLeague(username, defaultLeague);
		
	}

	@Override
	public void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue {
		real.setCriteria(username, criteria);
	}

	@Override
	public void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists,
			LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague {
		real.moveToLeague(username, userToMove, league);
		
	}

	@Override
	public void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists {
		real.check(username, gameID);
	}

	@Override
	public void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists {
		real.bet(username, gameID, amount);
	}

	@Override
	public void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists {
		real.raise(username, gameID, amount);
	}

	@Override
	public void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists {
		real.allIn(username, gameID);
	}

	@Override
	public void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists {
		real.fold(username, gameID);
	}
	


}
