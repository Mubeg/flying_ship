package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Messenger {

    private byte my_id;

    /**
     * Creates new Messenger object with id
     * @param id is used to perform messaging
     */
    public Messenger(byte id){
        my_id = id;
    }

    /**
     * 
     * @return {@code Message mesage} with new bytearray copied from native queue or {@code null} if error occured
     */
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

    /**
     * Sends message, relies upon toBytes() to get correct length
     * @param msg message to be sent
     */
    public void sendMessage(Message msg)
    {
        msg.sender = my_id;
        sendMessageNative(msg.toBytes(), Message.MSG_LEN);
    }

    /**
     * Sends message only if msg length equals to MSG_LEN
     * @param msg message to be sent
     */
    public void sendMessage(byte[] msg)
    {
        sendMessageNative(msg, msg.length);
    }

    /**
     * 
     * @param receiver id of receiver (thread safe)
     * @param buff buffer for the message to stored, expected to be at least {@code MSG_LEN} long
     * @return error code or 0 for success
     */
    private native byte getMessageNative(byte receiver, byte[] buff);
    /**
     * Sends message to the queue (thread safe)<p>
     * Does not send message if msg_len != MSG_LEN
     * @param msg message to me sent
     * @param msg_len length of message
     */
    private native void sendMessageNative(byte msg[], int msg_len);

    static {
        System.load(
            System.getProperty("user.dir") + "/build/JNI/flyingship/src/libnative.so");
       }
}
