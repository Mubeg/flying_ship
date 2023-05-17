package JNI.flyingship.src;

import java.util.ArrayList;

/**
 *
 * @author Mubeg
 */
public class Replay {

    private Integer id;
    private Integer length; //in frames
    private Integer player_id;
    private ArrayList<Message> frames;

    public void add_frame(Message frame){
        frames.add(frame);
        length+=1;
    }

    public Replay(Integer unique_id, Integer m_player_id){
        id = unique_id;
        player_id = m_player_id;
        length = 0;
        frames = new ArrayList<Message>();
    }

    public Integer getPlayerID(){
        return player_id;
    }

    public void setPlayerID(Integer new_player_id){
        player_id = new_player_id;
    }

}