package JNI.flyingship.src;

import flyingship.gui.GUI;

/**
 *
 * @author Mubeg
 */
public class FlyingShip {

    public static void main(String[] args) {

        Messenger messenger = new Messenger((byte) 2);  //2 is GUI's reserved ID
        Backend backend = new Backend();
        GUI gui = new GUI();
        backend.start();
        gui.start();

        Message msg = new Message();
        msg.type = 1;
        msg.receiver = 1;
        msg.set_data("Hello, Backend!!!");
        messenger.sendMessage(msg);

        Message received;
        do{
            received = messenger.getMessage();
        } while(received == null);
        
        System.out.println(received.get_data_string());
    }
}