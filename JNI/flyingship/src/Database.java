package JNI.flyingship.src;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Mubeg
 */
public class Database extends Thread {

    final static String filename = new String("database.db");
    final static MessagesTypes[] types = MessagesTypes.values();
    final static SenderIds[] senders = SenderIds.values();
    ArrayList<Replay> replays;
    ArrayList<Player> players; 
    int currentPlayerID;
    int currentReplayID;

    public Database(){
        try (FileReader reader = new FileReader(filename)) {
        } catch (IOException e) {}
        replays = new ArrayList<Replay>(null);
    }

    public void run(){

        Messenger messenger = new Messenger(SenderIds.Database.value());

        Message msg = new Message();
        msg.type = MessagesTypes.Checkin.value();
        msg.receiver = SenderIds.Backend.value();
        msg.set_data("Database, cheking in.");
        messenger.sendMessage(msg);
        
        boolean is_running = true;
        while(is_running){
            msg = messenger.getMessage();
            switch(types[msg.type]){
                case PlayerLogIn:
                    String playerName = msg.get_data_string();
                    boolean found = false;
                    for(int i = 0; i < players.size(); i++){
                        if (playerName == players.get(i).getName()){
                            currentPlayerID = i;
                            found = true;
                        }
                    }
                    if(!found){
                        currentPlayerID = players.size();
                        players.add(new Player(currentPlayerID, playerName, 0));
                    }
                    break;
                case StartGame:
                    replays.add(new Replay(replays.size(), currentPlayerID));
                    currentReplayID = replays.size();
                break;
                case UpdateFrame:
                    replays.get(currentReplayID).add_frame(msg);
                break;
                case ReCheckin:
                    msg.receiver = msg.sender;
                    msg.type = MessagesTypes.Checkin.value();
                    messenger.sendMessage(msg);
                    break;
                case End:
                    byte sender_id = msg.sender;
                    for(byte i = 1; i < senders.length; i++){
                        if(i != sender_id){
                            msg.receiver = i; 
                            messenger.sendMessage(msg);       
                        }
                    }
                    is_running = false;
                break;
                default:
                break;

            }
        };

    } 

}