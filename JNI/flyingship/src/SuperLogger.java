package JNI.flyingship.src;

import java.io.IOException;
import java.util.logging.*;

/**
 *
 * @author Mubeg
 */
public class SuperLogger extends Thread {

    final static MessagesTypes[] types = MessagesTypes.values();
    final static SenderIds[] senders = SenderIds.values();

    public void run(){

        Logger log = Logger.getLogger("SuperLogger");
        log.setLevel(Level.ALL);
        FileHandler fh;

        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler(System.getProperty("user.dir") + "/flyingship.log");  
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);    

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {    
                    fh.close();
                }
            });
    
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

        Messenger messenger = new Messenger(SenderIds.Logger.value());

        Message msg = new Message();
        msg.type = MessagesTypes.Checkin.value();
        msg.receiver = SenderIds.Overseer.value();
        msg.set_data("SuperLogger, cheking in.");
        messenger.sendMessage(msg);
        
        boolean is_running = true;
        while(is_running){
            msg = messenger.getMessage();
            if(msg != null){
                switch(types[msg.type]){
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
                        log.log(Level.FINEST, String.format("Msg of type %d, from sender %d with data %s\n", msg.type, msg.sender, msg.get_data_string()));
                    break;

                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

    } 

}