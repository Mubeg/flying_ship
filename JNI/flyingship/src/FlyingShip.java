package JNI.flyingship.src;

import flyingship.gui.GUI;

/**
 *
 * @author Mubeg
 */
public class FlyingShip {

    final static MessagesTypes[] types = MessagesTypes.values();
    final static SenderIds[] senders = SenderIds.values();

    public static void main(String[] args) {

        Messenger messenger = new Messenger(SenderIds.Overseer.value());
        Backend backend = new Backend();
        GUI gui = new GUI();
        Database database = new Database();
        SuperLogger logger = new SuperLogger();
        backend.start();
        gui.start();
        database.start();
        logger.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {    
        
            }
        });

        Message msg = new Message();
        msg.type = MessagesTypes.Checkin.value();
        msg.receiver = SenderIds.Overseer.value();
        msg.set_data("Overseer, cheking in.");
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