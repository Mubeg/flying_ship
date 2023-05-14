package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public enum SenderIds {
    Overseer,
    Backend,
    Frontend;

    public byte getValue() {
        return (byte) (ordinal() + 1);
    }
}