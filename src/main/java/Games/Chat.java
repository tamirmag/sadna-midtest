package Games;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tamir on 04/06/2017.
 */
@Entity
public class Chat {
    @Transient
    HashMap<String, ArrayList<Message>> players;
    @Transient
    HashMap<String, ArrayList<Message>> spectators;
    @Id
    private String id = new ObjectId().toString();
    public Chat() {
        players = new HashMap<>();
        spectators = new HashMap<>();
    }

    public void attach(Player player){
        players.put(player.getName(), new ArrayList<Message>());
    }
    public void attach(String spectator){
        spectators.put(spectator, new ArrayList<Message>());
    }

    public void sendMessage(String from, String to, String data){
        if(spectators.get(from) != null) { // spectator message
            if (to == null) { // public message

               Iterator it = spectators.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry spectator = (Map.Entry) it.next();
                    ((ArrayList<Message>)spectator.getValue()).add(new Message(from, to, data));
                }

                return;

            } else { //regular message
                spectators.get(to).add(new Message(from,to,data));
            }
        }else{
            if (to == null) { // public message
                Iterator it = spectators.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry spectator = (Map.Entry) it.next();
                    ((ArrayList<Message>)spectator.getValue()).add(new Message(from, to, data));
                }

                it = players.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry player = (Map.Entry) it.next();
                    ((ArrayList<Message>)player.getValue()).add(new Message(from, to, data));
                }

                return;

            } else { //regular message
                ArrayList<Message> frind = players.get(to);
                if(frind != null)
                    frind.add(new Message(from, to, data));
                else
                {
                    frind = spectators.get(to);
                    frind.add(new Message(from, to, data));
                }


            }
        }
    }
}
