package lehjr.numina.common.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.phys.Vec3;
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

    public static double horizontalMovement(Vec3 movementVec) {
        return Math.sqrt(movementVec.x * movementVec.x + movementVec.z + movementVec.z);
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
     * Used for packing a set of up to 8 boolean values into a single byte.
     * Handles short arrays safely by leaving missing bits as 0.
     */
    public static byte boolArrayToByte(boolean[] boolArray) {
        byte keysOut = 0;
        // Limit the loop to the shorter of 8 or the actual array length
        int limit = Math.min(8, boolArray.length);
        for (int i = 0; i < limit; i++) {
            if (boolArray[i]) {
                keysOut |= (1 << i);
            }
        }
        return keysOut;
    }

    /**
     * Used for unpacking a byte into a set of 8 boolean values.
     * Safely handles Java's signed bytes using a bitmask.
     */
    public static boolean[] byteToBooleanArray(byte byteVal) {
        boolean[] boolArray = new boolean[8];
        for (int i = 0; i < 8; i++) {
            // Shift 1 to the bit position and check if that bit is set
            boolArray[i] = (byteVal & (1 << i)) != 0;
        }
        return boolArray;
    }
}
