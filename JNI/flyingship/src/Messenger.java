package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Messenger {

    private byte my_id;

    public Messenger(byte id){
        my_id = id;
    }

    public Message getMessage()
    {
        byte receiver = my_id;
        byte[] msg = new byte[Message.MSG_LEN];
        byte err = getMessageNative(receiver, msg);
        if(err != 0){
            return null;
        }
        Message message = new Message(msg);
        return message;
    }

    public void sendMessage(Message msg)
    {
        msg.sender = my_id;
        sendMessageNative(msg.toBytes(), Message.MSG_LEN);
    }

    public void sendMessage(byte[] msg)
    {
        sendMessageNative(msg, Message.MSG_LEN);
    }

    private native byte getMessageNative(byte receiver, byte[] buff);
    private native void sendMessageNative(byte msg[], int msg_len);

    static {
        System.load(
            System.getProperty("user.dir") + "/../build/JNI/flyingship/src/libnative.so");
       }
}
