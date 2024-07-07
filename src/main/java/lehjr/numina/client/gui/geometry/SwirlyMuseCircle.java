package lehjr.numina.client.gui.geometry;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class SwirlyMuseCircle {
    public static final float detail = 2;
    protected FloatBuffer points;
    protected FloatBuffer color;
    int numsegments;

    public SwirlyMuseCircle() {
        this(new Color(0.0f, 1.0f, 0.0f, 0.0f), new Color(0.8f, 1.0f, 0.8f, 1.0f));
    }

    public SwirlyMuseCircle(Color c1, Color c2) {
        if (points == null) {
            points = GradientAndArcCalculator.getArcPoints(0, (float) (Math.PI * 2 + 0.0001), detail, 0, 0);
        }
        numsegments = points.limit() / 2;
        color = GradientAndArcCalculator.getColorGradient(c1, c2, points.limit() / 2);
    }

    public void draw(PoseStack matrixStack, float radius, double x, double y, float zLevel) {
        float ratio = (System.currentTimeMillis() % 2000) / 2000.0F;
        color.rewind();
        points.rewind();
        matrixStack.pushPose();
        matrixStack.translate(x, y, zLevel);
        matrixStack.scale(radius / detail, radius / detail, 1.0F);
//        RenderSystem.rotatef((float) (-ratio * 360.0), 0, 0, 1);
        matrixStack.mulPose(Axis.ZP.rotationDegrees(-ratio * 360.0F));
        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix4f = matrixStack.last().pose();

        while (points.hasRemaining() && color.hasRemaining()) {
            buffer.vertex(matrix4f, points.get(), points.get(), zLevel).color(color.get(), color.get(), color.get(), color.get()).endVertex();
        }
        tessellator.end();
        matrixStack.popPose();

        RenderSystem.disableBlend();
//        RenderSystem.enableTexture();
        RenderSystem.setShader(() -> oldShader);
    }
}
