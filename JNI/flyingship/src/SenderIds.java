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
    
    public byte value(){
        return (byte) ordinal();
    }
}