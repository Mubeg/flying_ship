package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public enum SenderIds {
    Bad,
    Overseer,
    Backend,
    Frontend,
    Database,
    Logger,
    MessengingSystem;
    
    public int value(){
        return (int) ordinal();
    }
}