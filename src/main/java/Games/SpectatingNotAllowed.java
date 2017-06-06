package Games;

public class SpectatingNotAllowed extends Exception {
    public SpectatingNotAllowed(int num) {
        super("cannot spectate game " + num);
    }
}
