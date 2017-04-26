package Users;


import Loggers.IErrorLogger;

public class UsernameAndPasswordNotMatch extends Exception{

    public UsernameAndPasswordNotMatch(String username , String password ) {

        super("username " + username+" and passwrod "+password+" does not match");
        IErrorLogger.getInstance().writeToFile("username " + username+" and passwrod "+password+" does not match");

    }
}
