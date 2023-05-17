package JNI.flyingship.src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

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
    Integer currentPlayerID;
    Integer currentReplayID;

    public Database(){
        replays = new ArrayList<Replay>();
        players = new ArrayList<Player>();
        try{
            String[] content = new String(Files.readAllBytes(Paths.get(filename))).split("\n");
            Gson gson = new Gson();
            replays = gson.fromJson(content[0], replays.getClass());
            players = gson.fromJson(content[1], players.getClass());

            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getID() != i){
                    map.put(players.get(i).getID(), i);
                    players.get(i).setID(i);
                }
            }
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getID() != i){
                    map.put(players.get(i).getID(), i);
                    players.get(i).setID(i);
                }
            }

            for(int i = 0; i < replays.size(); i++){
                int key = replays.get(i).getPlayerID();
                if(map.containsKey(key)){
                    replays.get(i).setPlayerID(map.get(key));
                }
                if(replays.get(i).getID() != i){
                    replays.get(i).setID(i);
                }
            }

        } catch (IOException e) {
        }
    }

    public void writeOut(){
        try {
        File file = new File(filename);
        file.createNewFile();
        FileWriter writer = new FileWriter(filename);
        Gson gson = new Gson();

        String json = gson.toJson(replays);
        json += "\n";
        json += gson.toJson(players);
            
        writer.append(json);
        writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        Messenger messenger = new Messenger(SenderIds.Database.value());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {    
                writeOut();
            }
        });

        Message msg = new Message();
        msg.type = MessagesTypes.Checkin.value();
        msg.receiver = SenderIds.Backend.value();
        msg.set_data("Database, cheking in.");
        messenger.sendMessage(msg);

        boolean is_running = true;
        while(is_running){
            msg = messenger.getMessage();
            if(msg != null){
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
                        int sender_id = msg.sender;
                        for(int i = 1; i < senders.length; i++){
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
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        writeOut();

    } 

}
