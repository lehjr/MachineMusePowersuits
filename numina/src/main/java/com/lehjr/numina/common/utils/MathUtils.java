package com.lehjr.numina.common.utils;

import com.mojang.datafixers.util.Pair;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Random;

public class MathUtils {
    // Multiplying by 0.0625 is faster than dividing by 16
    public static final float DIV_16F = 0.0625F;
    public static final double DIV_16D = 0.0625D;

    /* Copied from Minecraft 1.18.2 */
    public static Vector3f XN = new Vector3f(-1.0F, 0.0F, 0.0F);
    public static Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
    public static Vector3f YN = new Vector3f(0.0F, -1.0F, 0.0F);
    public static Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
    public static Vector3f ZN = new Vector3f(0.0F, 0.0F, -1.0F);
    public static Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);

    public static double clampDouble(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clampFloat(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    private static Random random = new Random();

    public static Random random() {
        return random;
    }

    public static double nextDouble() {
        return random().nextDouble();
    }

    public static double sumsq(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double pythag(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static boolean isIntInRange(@Nullable Pair<Integer, Integer> range, int val) {
        if (range != null) {
            return val >= range.getFirst() && val < range.getSecond();
        }
        return false;
    }

//    public static boolean isIntInRange(@Nullable Pair<Integer, Integer> range, int val) {
//        if (range != null) {
//            return val >= range.getFirst() && val < range.getSecond();
//        }
//        return false;
//    }

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
