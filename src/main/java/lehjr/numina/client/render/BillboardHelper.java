//package lehjr.numina.client.render;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.GL11;
//
//import java.nio.FloatBuffer;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 4:21 AM, 11/13/13
// * <p>
// * Ported to Java by lehjr on 10/25/16.
// */
//public final class BillboardHelper {
// NOTE: EXTREMELY BROKEN IN THIS STATE
//    private static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
//
//    private static void unrotate() {
//        matrix.clear();
//        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, matrix);
//        float scalex = pythag(matrix.get(0), matrix.get(1), matrix.get(2));
//        float scaley = pythag(matrix.get(4), matrix.get(5), matrix.get(6));
//        float scalez = pythag(matrix.get(8), matrix.get(9), matrix.get(10));
//        for (int i = 0; i < 12; i++) {
//            matrix.put(i, 0);
//        }
//        matrix.put(0, scalex);
//        matrix.put(5, scaley);
//        matrix.put(10, scalez);
//        GL11.glLoadMatrixf(matrix);
//    }
//
//    public static void unRotate() {
//        RenderSystem.assertOnRenderThread();
//        unrotate();
//    }
//
//    private static float pythag(final float x, final float y, final float z) {
//        return (float) Math.sqrt(x * x + y * y + z * z);
//    }
//}