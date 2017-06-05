package DB;

import Games.IGame;

/**
 * Created by tamir on 05/06/2017.
 */
public interface IGamesDB {
    IGame getGame(int id);

    void save(IGame game);
}
