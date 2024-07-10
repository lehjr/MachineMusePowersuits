package lehjr.numina.client.gui.geometry;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class DrawableMuseCircle {
    public static final double detail = 4;
    protected static FloatBuffer points;
    protected FloatBuffer color;

    public DrawableMuseCircle(Color c1, Color c2) {
        if (points == null) {
            FloatBuffer arcPoints = GradientAndArcCalculator.getArcPoints(0, (float) (Math.PI * 2 + 0.0001), 0, 0, 0, 0);
            points = BufferUtils.createFloatBuffer(arcPoints.limit() + 6);
            points.put(new float[]{0, 0, 0});
            points.put(arcPoints);
            arcPoints.rewind();
            points.put(arcPoints.get());
            points.put(arcPoints.get());
            points.put(arcPoints.get());
            points.flip();
        }
        FloatBuffer colourPoints = GradientAndArcCalculator.getColorGradient(c1, c1, points.limit() / 3);
        color = BufferUtils.createFloatBuffer(colourPoints.limit() + 4);
        color.put(c2.asArray());
        color.put(colourPoints);
        color.flip();
    }

    public void render(VertexConsumer vertexconsumer, PoseStack poseStack, double radius, double x, double y, double z) {
        points.rewind();
        color.rewind();
        NuminaLogger.logDebug("points size: " + points.limit());
        NuminaLogger.logDebug("color size: " + color.limit());
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale((float) (radius / detail), (float) (radius / detail), 1.0F);
//        RenderState.on2D();
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        RenderState.arraysOnC();
//        RenderState.texturelessOn();
////        RenderState.blendingOn();
//        RenderSystem.enableBlend();
//        GL11.glColorPointer(4, 0, color);
//        GL11.glVertexPointer(3, 0, points);
//        GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, points.limit() / 3);

        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);



        Matrix4f matrix4f = poseStack.last().pose();

        while (points.hasRemaining() && color.hasRemaining()) {
            NuminaLogger.logDebug("points remaining: " + points.remaining() +", color remaining: " + color.remaining());


            vertexconsumer.vertex(matrix4f, points.get(), points.get(), points.get()).color(color.get(), color.get(), color.get(), color.get()).endVertex();
        }


//        RenderState.blendingOff();
        RenderSystem.disableBlend();
//        RenderState.texturelessOff();
//        RenderState.arraysOff();
//        RenderState.off2D();
        poseStack.popPose();
        RenderSystem.setShader(() -> oldShader);


    }


    public void draw(PoseStack matrixStack, double radius, double x, double y) {
        points.rewind();
        color.rewind();
        matrixStack.pushPose();
        matrixStack.translate(x, y, 0);
        matrixStack.scale((float) (radius / detail), (float) (radius / detail), 1.0F);
//        RenderState.on2D();
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        RenderState.arraysOnC();
//        RenderState.texturelessOn();
////        RenderState.blendingOn();
//        RenderSystem.enableBlend();
//        GL11.glColorPointer(4, 0, color);
//        GL11.glVertexPointer(3, 0, points);
//        GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, points.limit() / 3);

        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        Matrix4f matrix4f = matrixStack.last().pose();

        while (points.hasRemaining() && color.hasRemaining()) {
            buffer.vertex(matrix4f, points.get(), points.get(), 0).color(color.get(), color.get(), color.get(), color.get()).endVertex();
        }


//        RenderState.blendingOff();
        RenderSystem.disableBlend();
//        RenderState.texturelessOff();
//        RenderState.arraysOff();
//        RenderState.off2D();
        matrixStack.popPose();
        RenderSystem.setShader(() -> oldShader);
    }
}
