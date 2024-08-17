package com.lehjr.numina.client.gui.clickable;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
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
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
//        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
        Matrix4f matrix4f = gfx.pose().last().pose();

        // DOWN
        switch (arrowDirection) {

            case UP: {
                // top
                buffer.addVertex(matrix4f, (float) this.left() + 5, (float) this.top() + 2, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);


                // bottom left
                buffer.addVertex(matrix4f, (float) this.left() + 3, (float) this.top() + 6, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // bottom right
                buffer.addVertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                break;
            }

            case RIGHT: {
                // top
                buffer.addVertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // bottom
                buffer.addVertex(matrix4f, (float) this.left() + 3, (float)this.top() + 6, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // right
                buffer.addVertex(matrix4f, (float) this.left() + 7, (float) this.top() + 4, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);
                break;
            }

            case DOWN: {
                // top left
                buffer.addVertex(matrix4f, (float) this.left() + 3, (float) this.top() + 2, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // bottom
                buffer.addVertex(matrix4f, (float) this.left() + 5, (float) this.top() + 6, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // top right
                buffer.addVertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                break;
            }

            case LEFT: {
                // top
                buffer.addVertex(matrix4f, (float) this.left() + 7, (float) this.top() + 2, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // bottom
                buffer.addVertex(matrix4f, (float) this.left() + 7, (float) this.top() + 6, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);

                // right
                buffer.addVertex(matrix4f, (float) this.left() + 3, (float) this.top() + 4, zLevel)
                        .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .setLight(0x00F000F0);
            }
        }
        RenderSystem.disableBlend();
//        RenderSystem.enableTexture();
//        RenderSystem.setShader(() -> oldShader);
    }

    public enum ArrowDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }
}
