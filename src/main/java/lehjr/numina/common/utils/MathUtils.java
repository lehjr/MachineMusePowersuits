package lehjr.numina.common.utils;

public class MathUtils {
    // Multiplying by 0.0625 is faster than dividing by 16
    public static final float DIV_16F = 0.0625F;
    public static final double DIV_16D = 0.0625D;


    public static double clampDouble(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Used for packing a set of up to boolean values in to a single byte. This is used for sending
     * key press tracking for modifying movement because keys are client side, but movement functions
     * are server side.
     *
     * @param boolArray
     * @return
     */
    public static byte boolArrayToByte(boolean[] boolArray) {
        byte keysOut = 0;
        for(int i = 0; i < 8; i++ ) {
            if (boolArray.length > i) {
                keysOut = (byte) (keysOut | (byte) (((boolArray[i] ? 1 : 0) <<  i)));
            }
        }
        return keysOut;
    }

    /**
     * Used for unpacking a byte into a set of 8 boolean values
     * @param byteVal
     * @return
     */
    public static boolean[] byteToBooleanArray(byte byteVal) {
        boolean[] boolArray = new boolean[8];
        for (int i=0; i< 8; i++) {
            boolArray[i] = byteVal >> i > 0;
        }
        return boolArray;
    }
}
