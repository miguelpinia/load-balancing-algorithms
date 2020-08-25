package org.mx.unam.imate.concurrent;

/**
 *
 * @author miguel
 */
public class Utils {

    /**
     * Returns true if all values are true otherwise returns false. Return false
     * if the array is empty.
     *
     * @param array
     * @return
     */
    public static boolean all(boolean... array) {
        if (array.length < 1) {
            return false;
        }
        for (boolean b : array) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if any value is true otherwise returns false. Return false
     * if the array is empty.
     *
     * @param array
     * @return
     */
    public static boolean any(boolean... array) {
        if (array.length < 1) {
            return false;
        }
        for (boolean b : array) {
            if (b) {
                return true;
            }
        }
        return false;
    }

}
