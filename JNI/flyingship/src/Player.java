package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Player {

    private int id;
    private String name;
    private int highScore;

    public Player(int unique_id, String m_name, int m_highScore){
        id = unique_id;
        name = m_name;
        highScore = m_highScore;
    }

    public String getName(){
        return name;
    }

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(int m_highScore){
        highScore = m_highScore;
    }

    public int getID(){
        return id;
    }

}