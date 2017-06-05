package Games;

/**
 * Created by tamir on 04/06/2017.
 */
public class Message {
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getData() {
        return data;
    }

    public Message(String from, String to, String data) {

        this.from = from;
        this.to = to;
        this.data = data;
    }

    private String data;
}
