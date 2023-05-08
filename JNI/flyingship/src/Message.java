package JNI.flyingship.src;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author Mubeg
 * MSG_LEN = 103
 * DATA_LEN = 100
 */
class Message {

    private static int DATA_LEN = 100;
    public static int MSG_LEN = 103;
    public byte type;
    public byte receiver;
    public byte sender;
    private byte[] data;

    public Message()
    {
        type = 0;
        receiver = 0;
        sender = 0;
        data = new byte[DATA_LEN];
    }

    public Message(byte[] msg)
    {
        if(msg.length != MSG_LEN){return;}

        type = msg[0];
        receiver = msg[1];
        sender = msg[2];
        data = new byte[DATA_LEN];
        System.arraycopy(msg, 3, data, 0, DATA_LEN);
    }

    public void set_data(String msg)
    {
        if(msg.length() < DATA_LEN)
        {
            data = msg.getBytes(StandardCharsets.ISO_8859_1);
        }
    }

    public void set_data(byte[] msg)
    {
        data = msg;
    }
    public byte[] get_data()
    {
        return data;
    }

    public String get_data_string()
    {
        return new String(data, StandardCharsets.ISO_8859_1);
    }

    public byte[] toBytes()
    {
        byte[] msg = new byte[Message.MSG_LEN];
        msg[0] = type;
        msg[1] = receiver;
        msg[2] = sender;
        for(int i = 0; i < data.length; i++){
            msg[3 + i] = data[i];
        }
        
        return msg;
    }
}