package Acceptance;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

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


public class acctests {
	
	Bridge bridge = Driver.getBridge();
    
	@Test
    public void testRegister() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
		ServiceUser u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
		assertEquals(u.getUsername(),"moshe");
		assertEquals(u.getPassword(),"1111");
		assertEquals(u.getEmail(),"noname@gmail.com");
		assertFalse(u.getUsername()=="");
		assertFalse(u.getPassword()=="");
		assertFalse(u.getEmail()=="");
		assertFalse(u.getWallet().getAmountOfMoney()<0);
    }
	
	@Test
    public void testLoginWithIncorrectPassword() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists {
		ServiceUser u = bridge.register("moshe", "1111", "noname@gmail.com", 100);
		String password="1111";
		 bridge.login("moshe", password); 
		 assertEquals(u.getPassword(),password);
    }
	
	@Test
    public void testLogin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists {
		ServiceUser u2 = bridge.register("moshe", "1111", "noname@gmail.com", 100);
		ServiceUser u1 = bridge.login("moshe", "1111"); 
		assertEquals(u1.getUsername(),"moshe");
		assertEquals(u1.getPassword(),"1111");
		assertFalse(u1.getUsername()=="");
		assertFalse(u1.getPassword()=="");
		assertTrue((u1.getUsername()==u2.getUsername()&&u1.getPassword()==u2.getPassword())||u1.getUsername()==u2.getUsername());
    }
	
	 @Test(expected = AlreadyLoggedIn.class)
	    public void AlreadyLoggedInLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue {
		 bridge.register("moshe", "1111", "noname@gmail.com", 100);
		 bridge.login("moshe", "1111");   
		 bridge.login("moshe", "1111");
	    }

	    @Test(expected = UsernameNotValid.class)
	    public void UsernameNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue {
	    	bridge.register("moshe", "1111", "noname@gmail.com", 100); 
	    	bridge.login("", "1111");   
	    }

	    @Test(expected = PasswordNotValid.class)
	    public void PasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue {
	    	bridge.register("moshe", "1111", "noname@gmail.com", 100); 
	    	bridge.login("moshe", "");
	    }

	    @Test(expected = UsernameAndPasswordNotMatch.class)
	    public void UsernamePasswordNotValidLogin() throws UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, UserAlreadyExists, AlreadyLoggedIn, UserNotExists, EmailNotValid, NegativeValue {
	    	bridge.register("moshe", "1111", "noname@gmail.com", 100); 
	    	bridge.login("moshe", "2222");
	    }

	   @Test(expected = UserAlreadyExists.class)
	    public void UserAlreadyExistsLogin() throws UserAlreadyExists, UsernameNotValid, EmailNotValid, NegativeValue, PasswordNotValid {
		   bridge.register("moshe", "1111", "noname@gmail.com", 100);
		   bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    }

		@Test
	    public void testLogOut() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, AlreadyLoggedOut {
			String uname="moshe";
			bridge.logout(uname);
			assertFalse(uname=="");
	    }

	    @Test(expected = AlreadyLoggedOut.class)
	    public void AlreadyLoggedOutLogout() throws UserAlreadyExists, UserNotExists, AlreadyLoggedOut, AlreadyLoggedIn, UsernameNotValid, UsernameAndPasswordNotMatch, PasswordNotValid, EmailNotValid, NegativeValue {
			   bridge.register("moshe", "1111", "noname@gmail.com", 100);
		    	bridge.login("moshe", "1111");
		    	bridge.logout("moshe");
		    	bridge.logout("moshe");
	    }

	   @Test(expected = UserNotExists.class)
	    public void UserNotExistsLogout() throws UserNotExists, AlreadyLoggedOut {
	    	bridge.logout("moshe");
	    }
	   
	   @Test(expected = UserNotExists.class)
	    public void UserNotExistsLogin() throws UserNotExists, AlreadyLoggedOut, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid {
	    	bridge.login("moshe", "1111");
	    }

	    @Test(expected = EmailNotValid.class)
	    public void EmailNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
	    	 bridge.register("moshe", "1111", "12323123123@!2", 100);
	    }

	    @Test(expected = NegativeValue.class)
	    public void NegativeValueRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", -100);	  
	    	 }

	    @Test(expected = UsernameNotValid.class)
	    public void UsernameNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
	        bridge.register("", "1111", "noname@gmail.com", 100);	
	        }

	    @Test(expected = PasswordNotValid.class)
	    public void PasswordNotValidRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
	    	 bridge.register("moshe", "", "noname@gmail.com", 100);	    }

	    @Test(expected = UserAlreadyExists.class)
	    public void UserAlreadyExistsRegistration() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    }
	    
		@Test
	    public void testFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn  {
			ServiceUser u2 = bridge.register("moshe", "1111", "noname@gmail.com", 100);
			ServiceUser u1 = bridge.login("moshe", "1111"); 
			assertEquals(u1.getUsername(),"moshe");
			assertEquals(u1.getPassword(),"1111");
			assertFalse(u1.getUsername()=="");
			assertFalse(u1.getPassword()=="");
			assertTrue((u1.getUsername()==u2.getUsername()&&u1.getPassword()==u2.getPassword())||u1.getUsername()==u2.getUsername());
			bridge.joinGame(1, "moshe");
			bridge.joinGame(2, "moshe");
			bridge.joinGame(3, "moshe");
			ArrayList<Integer> sGames = bridge.findSpectatableGames("moshe");
			assertTrue(sGames.size()==3);
		}
		
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInFindSpectatableGames() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.findSpectatableGames("moshe");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsFindSpectatableGames() throws UserNotLoggedIn, UserNotExists {
	    	 bridge.findSpectatableGames("moshe");
	    }
	    
		@Test
	    public void testCreateGameAndJoin() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn  {
			ServiceUser u2 = bridge.register("moshe", "1111", "noname@gmail.com", 100);
			ServiceUser u1 = bridge.login("moshe", "1111");
			ServiceUser u3 = bridge.register("stav", "2222", "yoyo@gmail.com", 100);
			ServiceUser u4 = bridge.login("stav", "2222");
			assertEquals(u1.getUsername(),"moshe");
			assertEquals(u1.getPassword(),"1111");
			assertFalse(u1.getUsername()=="");
			assertFalse(u1.getPassword()=="");
			assertFalse(u2.getUsername()=="");
			assertFalse(u2.getPassword()=="");
			assertEquals(u3.getUsername(),"stav");
			assertEquals(u3.getPassword(),"2222");
			assertFalse(u4.getUsername()=="");
			assertFalse(u4.getPassword()=="");
			String name= "moshe";
			int num = bridge.createGame(name, "NoLimitHoldem", 2, 2, 10, 2, 5, true);
			bridge.joinGame(num, "stav");
			ArrayList<Integer> sGames = bridge.findSpectatableGames("stav");
			assertTrue(sGames.size()==1);
		}	 
		
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedIncreateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.createGame("moshe", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistscreateGame() throws UserNotLoggedIn, UserNotExists {
	    	 bridge.createGame("moshe", "NoLimitHoldem", 2, 2, 10, 2, 5, true);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInJoinGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.joinGame(22,"moshe");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsJoinGame() throws UserNotLoggedIn, UserNotExists  {
	    	 bridge.joinGame(22,"moshe");
	    }

	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInSpectateGame() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.spectateGame(22,"moshe");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsSpectateGame() throws UserNotLoggedIn, UserNotExists  {
	    	 bridge.spectateGame(22,"moshe");
	    }
	    
		@Test
	    public void testViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, UserNotExists, UserNotLoggedIn  {
			ServiceUser u2 = bridge.register("moshe", "1111", "noname@gmail.com", 100);
			ServiceUser u1 = bridge.login("moshe", "1111");
			assertFalse(u1.getUsername()=="");
			assertFalse(u1.getPassword()=="");
			assertFalse(u2.getUsername()=="");
			assertFalse(u2.getPassword()=="");
			String name= "moshe";
			int num = bridge.createGame(name, "NoLimitHoldem", 2, 2, 10, 2, 5, true);
		    ArrayList<String> sGames= bridge.viewReplay(num,"moshe");
			assertTrue(sGames.size()==1);

		}	 
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInViewReplay() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.viewReplay(22,"moshe");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsViewReplay() throws UserNotLoggedIn, UserNotExists  {
	    	 bridge.viewReplay(22,"moshe");
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInSaveFavoriteTurn() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.saveFavoriteTurn(22,"moshe","favoriteTurn1");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsSaveFavoriteTurn() throws UserNotLoggedIn, UserNotExists  {
	    	 bridge.saveFavoriteTurn(22,"moshe","favoriteTurn1");
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInFindActiveGamesByPotSize() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.findActiveGamesByPotSize(100,"moshe");
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsFindActiveGamesByPotSize() throws UserNotLoggedIn, UserNotExists  {
	    	 bridge.findActiveGamesByPotSize(100,"moshe");
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInSetDefaultLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.setDefaultLeague("moshe",1);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsSetDefaultLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.setDefaultLeague("moshe",1);
	    }
	    
	    @Test(expected = NegativeValue.class)
	    public void NegativeValueSetDefaultLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn  {
	    	bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	bridge.login("moshe", "1111");
	    	bridge.setDefaultLeague("moshe",-12);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInSetCriteria() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.setCriteria("moshe",1);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsSetCriteria() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.setCriteria("moshe",1);
	    }
	    
	    @Test(expected = NegativeValue.class)
	    public void NegativeValueSetCriteria() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn  {
	    	bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	bridge.login("moshe", "1111");
	    	bridge.setCriteria("moshe",-12);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInMoveToLeague() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague, UsernameAndPasswordNotMatch, AlreadyLoggedIn, AlreadyLoggedOut  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.login("moshe", "1111");
	    	 bridge.setDefaultLeague("moshe",1);
	    	 bridge.logout("moshe");
	    	 bridge.moveToLeague("moshe","moshe",2);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague  {
	    	 bridge.moveToLeague("moshe","moshe",2);
	    }
	    
	    @Test(expected = NegativeValue.class)
	    public void NegativeValueMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.login("moshe", "1111");
	    	 bridge.setDefaultLeague("moshe",1);
	    	 bridge.moveToLeague("moshe","moshe",-2);
	    }
	    
	    @Test(expected = NegativeValue.class)
	    public void UserAlreadyInLeagueMoveToLeague() throws UserNotLoggedIn, UserNotExists, NegativeValue, EmailNotValid, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UsernameAndPasswordNotMatch, AlreadyLoggedIn, LeagueNotExists, UserAlreadyInLeague, UserNotInLeague  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.login("moshe", "1111");
	    	 bridge.setDefaultLeague("moshe",1);
	    	 bridge.moveToLeague("moshe","moshe",1);
	    }
	    
///	    void moveToLeague(String username, String userToMove, int league)  LeagueNotExists , UserNotInLeague;

	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInCheck() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.check("moshe",2122);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsCheck() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.check("moshe",2122);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInBet() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.bet("moshe",2122,20);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsBet() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.bet("moshe",2122,20);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInRaise() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.raise("moshe",2122,5);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsRaise() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.raise("moshe",2122,5);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInAllIn() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.allIn("moshe",2122);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsAllIn() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.allIn("moshe",2122);
	    }
	    
	    @Test(expected = UserNotLoggedIn.class)
	    public void UserNotLoggedInFold() throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid, UserNotLoggedIn, UserNotExists  {
	    	 bridge.register("moshe", "1111", "noname@gmail.com", 100);
	    	 bridge.fold("moshe",2122);
	    }
	    
	    @Test(expected = UserNotExists.class)
	    public void UserNotExistsFold() throws UserNotLoggedIn, UserNotExists, NegativeValue  {
	    	 bridge.fold("moshe",2122);
	    }
	    

	
}
