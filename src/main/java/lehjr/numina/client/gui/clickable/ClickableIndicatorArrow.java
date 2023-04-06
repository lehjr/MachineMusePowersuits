package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class ClickableIndicatorArrow extends Clickable {
    public float zLevel = 0;
    ArrowDirection arrowDirection = ArrowDirection.RIGHT;

    public ClickableIndicatorArrow() {
        super(MusePoint2D.ZERO, new MusePoint2D(8,8));
    }

    public void setDirection(ArrowDirection arrowDirection) {
        this.arrowDirection = arrowDirection;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
        Matrix4f matrix4f = matrixStack.last().pose();

        // DOWN
        switch (arrowDirection) {

            case UP: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 5, (float) this.top() + 2, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();


                // bottom left
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 6, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                break;
            }

            case RIGHT: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 3, (float)this.top() + 6, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 4, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                break;
            }

            case DOWN: {
                // top left
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 5, (float) this.top() + 6, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // top right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                break;
            }

            case LEFT: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // right
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 4, zLevel)
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }
        }
        tessellator.end();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
//        RenderSystem.setShader(() -> oldShader);
    }

    public enum ArrowDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }
}
