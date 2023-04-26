package flyingship.src;

/**
 *
 * @author Mubeg
 */
public class FlyingShip {

    private native void nativePrint();

    static {
        System.load(
            System.getProperty("user.dir") + "/flyingship/src/libnativeprint.so");
       }
    public static void main(String[] args) {
        FlyingShip j = new FlyingShip();
        j.nativePrint();
    }
}