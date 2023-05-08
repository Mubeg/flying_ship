package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Backend extends Thread {

    public native void run();

    static {
        System.load(
                System.getProperty("user.dir") + "/JNI//flyingship/src/libnative.so");
       }
}