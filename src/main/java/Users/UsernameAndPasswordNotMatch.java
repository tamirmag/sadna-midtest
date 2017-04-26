package Users;


import Loggers.ErrorLogger;

public class UsernameAndPasswordNotMatch extends Exception{

    public UsernameAndPasswordNotMatch(String username , String password ) {
        super();
        ErrorLogger.getInstance().writeToFile("username " + username+" and passwrod "+password+" does not match");

    }
}
