package JNI.flyingship.src;

import java.util.ArrayList;

/**
 *
 * @author Mubeg
 */
public class Replay {

    private int id;
    private int length; //in frames
    private int player_id;
    private ArrayList<Message> frames;

    public void add_frame(Message frame){
        frames.add(frame);
        length+=1;
    }

    public Replay(int unique_id, int m_player_id){
        id = unique_id;
        player_id = m_player_id;
        length = 0;
        frames = new ArrayList<Message>(null);
    }

}