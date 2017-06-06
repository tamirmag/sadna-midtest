package ServerClient;
//package ServiceLayer;

import java.util.ArrayList;

import Games.*;
import ServiceLayer.IServiceClass;
import ServiceLayer.ServiceClass;
import ServiceLayer.ServiceUser;
import Users.*;

public class Handler {

	ServiceClass s = new ServiceClass(); 
	
	public ServiceUser handleRegister(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
		return s.register(username, password, email, wallet);		
	}

	public ServiceUser handleLogin(String username, String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists {
		return s.login(username, password);
	}

	public void handleLogout(String username) throws UserNotExists, AlreadyLoggedOut {
		s.logout(username);
	}

	public ArrayList<Integer> handleFindSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
		return s.findSpectatableGames(username);
	}

	public int handleCreateGame(String username, String gameType, int buyInPolicy, int chipPolicy, int minimumBet,
			int minimalAmountOfPlayers, int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists {
		return s.createGame(username,gameType, buyInPolicy, chipPolicy,
				minimumBet, minimalAmountOfPlayers, maximalAmountOfPlayers, spectatingMode);
	}

	public void handleJoinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, CantJoin, NoMuchMoney {
		s.joinGame(gamenum, username);
	}

	public void handleSpectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed {
		s.spectateGame(gamenum, username);
	}

	public ArrayList<String> handleViewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
		return s.viewReplay(gamenum, username);
	}

	public ArrayList<Integer> handleFindActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists {
		return s.findActiveGamesByPotSize(potSize, username);
	}

	public void handleCheck(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney {
		s.check(username, gameID);
	}

	public void handleBet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, NotLegalAmount {
		s.bet(username, gameID, amount);
	}

	public void handleRaise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotAllowedNumHigh, NotYourTurn, NotLegalAmount {
		s.raise(username, gameID, amount);
	}

	public void handleAllIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, NotLegalAmount {
		s.allIn(username, gameID);
	}

	public void handleFold(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn {
		s.fold(username, gameID);
	}

	public void handleTerminateGame(int gameID) {
		s.terminateGame(gameID);
	}
	
	public void handleClearLoggedInUsers() {
		s.clearLoggedInUsers();
	}

	public void handleClearUsers() {
		s.clearUsers();
	}

	public void handleClearAllFinishedGameLogs() {
		s.clearAllFinishedGameLogs();
	}
	
	
}
