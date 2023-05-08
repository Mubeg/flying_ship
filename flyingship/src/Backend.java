package flyingship.src;

/**
 *
 * @author Mubeg
 */
public class Backend extends Thread {

    public native void run();

    static {
        System.load(
                System.getProperty("user.dir") + "/flyingship/src/libnative.so");
       }
}