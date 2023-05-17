package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Player {

    private Integer id;
    private String name;
    private Integer highScore;

    public Player(Integer unique_id, String m_name, Integer m_highScore){
        id = unique_id;
        name = m_name;
        highScore = m_highScore;
    }

    public String getName(){
        return name;
    }

    public Integer getHighScore(){
        return highScore;
    }

    public void setHighScore(Integer m_highScore){
        highScore = m_highScore;
    }

    public Integer getID(){
        return id;
    }

    public void setID(Integer m_id){
        id = m_id;
    }

}