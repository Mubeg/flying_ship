package JNI.flyingship.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Mubeg
 * <p>
 * MSG_LEN = 403
 * <p>
 * DATA_LEN = 100
 */
public class Message {

    public static int DATA_LEN = 100;
    public static int MSG_LEN = 412;
    public int type;
    public int receiver;
    public int sender;
    private int[] data;

    public Message()
    {
        type = 0;
        receiver = 0;
        sender = 0;
        data = new int[DATA_LEN];
    }

    public Message(int[] msg_data)
    {
        if(msg_data.length > MSG_LEN){return;}
        type = msg_data[0];
        receiver = msg_data[1];
        sender = msg_data[2];
        data = new int[DATA_LEN];
        System.arraycopy(msg_data, 3, data, 0, DATA_LEN);
    }
    
    public Message(int msg_type, int msg_receiver, int[] msg_data)
    {
        if(msg_data.length >= DATA_LEN){return;}

        type = msg_type;
        receiver = msg_receiver;
        data = new int[DATA_LEN];
        System.arraycopy(msg_data, 0, data, 0, msg_data.length);
    }

    /**
     * Sets data<p>
     * Does not perform length check
     */
    public void set_data(int[] msg)
    {
        data = msg;
    }
    public int[] get_data()
    {
        return data;
    }

    public void set_data(String str)
    {
        IntBuffer intBuf = ByteBuffer.wrap(str.getBytes(StandardCharsets.ISO_8859_1)).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        data = new int[intBuf.remaining()];
    }

    public String get_data_string()
    {
        if(data == null){
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(DATA_LEN * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);

        byte[] array = byteBuffer.array();

        return new String(array, StandardCharsets.ISO_8859_1);
    }

    /**
     * Makes bytearray from class fields
     * @return new bytearray[MSG_LEN]
     */
    public int[] toArray()
    {
        int[] msg = new int[Message.MSG_LEN];
        msg[0] = type;
        msg[1] = receiver;
        msg[2] = sender;
        for(int i = 0; i < data.length; i++){
            msg[3 + i] = data[i];
        }
        
        return msg;
    }
}
