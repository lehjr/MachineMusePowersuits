package lehjr.numina.common.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3f;

import java.util.Random;

public class MathUtils {
    public static byte boolArrayToByte(boolean[] boolArray) {
        byte keysOut = 0;
        for(int i = 0; i < 8; i++ ) {
            if (boolArray.length > i) {
                keysOut = (byte) (keysOut | (byte) (((boolArray[i] ? 1 : 0) <<  i)));
            }
        }
        return keysOut;
    }

    public static boolean[] byteToBooleanArray(byte byteVal) {
        boolean[] boolArray = new boolean[8];
        for (int i=0; i< 8; i++) {
            boolArray[i] = byteVal >> i > 0;
        }
        return boolArray;
    }

    public static final float DIV_16F = 0.0625F;
    public static final double DIV_16D = 0.0625D;

    /* Copied from Minecraft 1.18.2 */
    public static Vector3f XN = new Vector3f(-1.0F, 0.0F, 0.0F);
    public static Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
    public static Vector3f YN = new Vector3f(0.0F, -1.0F, 0.0F);
    public static Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
    public static Vector3f ZN = new Vector3f(0.0F, 0.0F, -1.0F);
    public static Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);


    private static Random random = new Random();

    public static Random random() {
        return random;
    }

    public static double nextDouble() {
        return random().nextDouble();
    }

    public static double clampDouble(double value, double min, double max) {
        return value < min ? min : (value > max ? max : value);
    }

    public static float clampFloat(float value, float min, float max) {
        /**
         * Float#compare(f1, f2);
         * F1 < F2 = -1
         * F1 > F2 = 1
         * F1 == F2 = 0;
         */
        // return value < min ? min : (value > max ? max : value);
        return Float.compare(value, min) == -1 ? min : (Float.compare(value, max) == 1 ? max : value );
    }

    public static double sumsq(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double pythag(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static boolean isIntInRange(Pair<Integer, Integer> range, int val) {
        if (range != null) {
            return val >= range.getLeft() && val < range.getRight();
        }
        return false;
    }
}
