package DB;


import Loggers.GameLogger;
import Loggers.MyLogger;
import Users.User;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

public class LoggerDB {
    private static final LoggerDB instance = new LoggerDB();

    Morphia morphia;
    MongoClient m;
    Datastore datastore;

    private LoggerDB() {
        morphia = new Morphia();
        morphia.mapPackage("Loggers");
        m = new MongoClient("localhost", 27017);
        datastore = morphia.createDatastore(m, "systemDatabase");
    }

    public static LoggerDB getInstance() {
        return instance;
    }

    public void changeDataStore(String newDataStore) {
        datastore = morphia.createDatastore(m, newDataStore);
    }

    public void addNewLogger(MyLogger logger)
    {
        datastore.save(logger);
    }

    public ArrayList<String> getContentOfFile(String filename)
    {
        final Query<MyLogger> query = datastore.createQuery(MyLogger.class).filter("filename =", filename);
        List<MyLogger> loggers = query.asList();
        if (loggers.size() == 0) return null;
        return loggers.get(0).getContentOfFile();
    }

    public void saveLogger(MyLogger logger)
    {
        datastore.save(logger);
    }

    public void deleteLogger(String filename)
    {
        final Query<MyLogger> query = datastore.createQuery(MyLogger.class).filter("filename =", filename);
        datastore.delete(query);
    }

    public ArrayList<GameLogger> getAllGameLoggers()
    {
        final Query<MyLogger> query = datastore.createQuery(MyLogger.class);
        ArrayList<MyLogger> loggers = new ArrayList<MyLogger>(query.asList());
        ArrayList<GameLogger> gameLoggers = new ArrayList<>();
        for(MyLogger log : loggers)
        {
            if(log.getFilename().equals("ActionLog") ||log.getFilename().equals("ErrorLog") ) {}
            else
            {
                GameLogger g = new GameLogger(log.getFilename());
                g.setContent(g.getContentOfFile());
                gameLoggers.add(g);
            }
        }
        return gameLoggers;
    }

}
