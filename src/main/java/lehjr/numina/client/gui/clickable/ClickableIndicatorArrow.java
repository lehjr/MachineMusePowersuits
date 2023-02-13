package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Matrix4f matrix4f = matrixStack.last().pose();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_LIGHTMAP);

        // DOWN
        switch (arrowDirection) {

            case UP: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 5, (float) this.top() + 2, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();


                // bottom left
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 6, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                break;
            }

            case RIGHT: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 3, (float)this.top() + 6, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                break;
            }

            case DOWN: {
                // top left
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 5, (float) this.top() + 6, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // top right
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                break;
            }

            case LEFT: {
                // top
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // bottom
                buffer.vertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                // right
                buffer.vertex(matrix4f, (float) this.left() + 3, (float) this.top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }
        }
        tessellator.end();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public enum ArrowDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }
}
