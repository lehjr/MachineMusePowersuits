package com.lehjr.numina.client.gui;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class NuminaIcons {
    private final NuminaSpriteUploader spriteUploader;
    private static final String iconPrefix = "gui";

    public final DrawableIcon armordisplayselect;
    public final DrawableIcon checkmark;
    public final DrawableIcon colorclicker;
    public final DrawableIcon energygeneration;
    public final DrawableIcon energystorage;
    public final DrawableIcon glowArmor;

    public final DrawableIcon lightning;
    public final DrawableIcon lightning2;
    public final DrawableIcon lightningmedium;
    public final DrawableIcon minusSign;
    public final DrawableIcon normalArmor;
    public final DrawableIcon plusSign;
    public final DrawableIcon transparentArmor;
    public final DrawableIcon weapon;

    public NuminaIcons(NuminaSpriteUploader spriteUploader) {
        this.spriteUploader = spriteUploader;
        this.armordisplayselect = registerIcon("armordisplayselect", 8, 8);
        this.checkmark = registerIcon("checkmark", 16, 16);
        this.colorclicker = registerIcon("colorclicker", 8, 8);
        this.energygeneration = registerIcon("energygeneration",32, 32);
        this.energystorage = registerIcon("energystorage",32, 32);
        this.glowArmor= registerIcon("glowarmor", 8, 8);
        this.lightning = registerIcon("lightning", 800, 62);
        this.lightning2 = registerIcon("lightning2", 800, 62);
        this.lightningmedium = registerIcon("lightningmedium", 800, 62);
        this.minusSign = registerIcon("minussign", 8, 8);
        this.normalArmor = registerIcon("normalarmor", 8, 8);
        this.plusSign= registerIcon("plussign", 8, 8);
        this.transparentArmor = registerIcon("transparentarmor", 8, 8);
        this.weapon = registerIcon("weapon", 16, 16);
    }

    private DrawableIcon registerIcon(String name, int width, int height) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, name);
        return new DrawableIcon(location, width, height);
    }

    public NuminaSpriteUploader  getSpriteUploader() {
        return spriteUploader;
    }

    public class DrawableIcon {

        final ResourceLocation location;
        private final int width;
        private final int height;

        protected DrawableIcon(ResourceLocation locationIn, int width, int height) {
            this.location = locationIn;
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(PoseStack matrixStack, double x, double y, Color color) {
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.enableBlend();
            draw(matrixStack, x, y, 0, 0, 0, 0, color);
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft, double posTop, double width, double height, Color color) {
            renderIconScaledWithColor(matrixStack, posLeft, posTop, width, height, 0, color);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft, double posTop, double width, double height, float zLevel, Color color) {
//            bindTexture();
//            TextureAtlasSprite icon = getSprite();
//            RenderSystem.enableBlend();
////            RenderSystem.disableAlphaTest();
//            RenderSystem.defaultBlendFunc();
//            innerBlit(matrixStack.last().pose(), posLeft, posLeft + width, posTop, posTop + height, zLevel, icon.getU0(), icon.getU1(), icon.getV0(), icon.getV1(), color);
//            RenderSystem.disableBlend();
////            RenderSystem.enableAlphaTest();
//            RenderSystem.enableDepthTest();

            bindTexture();
            TextureAtlasSprite icon = getSprite();
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.enableBlend();
            innerBlit(matrixStack.last().pose(), posLeft, posLeft + width, posTop, posTop + height, zLevel, icon.getU0(), icon.getU1(), icon.getV0(), icon.getV1(), color);
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }

        public void draw(PoseStack poseStack, double xOffset, double yOffset, double maskTop, double maskBottom, double maskLeft, double maskRight, Color color) {
            double textureWidth = this.width;
            double textureHeight = this.height;

            bindTexture();
            TextureAtlasSprite icon = getSprite();
            float zLevel = 0;

            double posLeft = xOffset + maskLeft;
            double posTop = yOffset + maskTop;
            double width = textureWidth - maskRight - maskLeft;
            double height = textureHeight - maskBottom - maskTop;

            double posRight = posLeft + width;
            double posBottom = posTop + height;

            double uSize = icon.getU1() - icon.getU0();
            double vSize = icon.getV1() - icon.getV0();
            float minU = (float) (icon.getU0() + uSize * (maskLeft / textureWidth));
            float minV = (float) (icon.getV0() + vSize * (maskTop / textureHeight));
            float maxU = (float) (icon.getU1() - uSize * (maskRight / textureWidth));
            float maxV = (float) (icon.getV1() - vSize * (maskBottom / textureHeight));

//            RenderSystem.enableBlend();
////            RenderSystem.disableAlphaTest();
//            RenderSystem.defaultBlendFunc();
            innerBlit(poseStack.last().pose(), posLeft, posRight, posTop, posBottom, zLevel, minU, maxU, minV, maxV, color);
//            RenderSystem.disableBlend();
////            RenderSystem.enableAlphaTest();
//            RenderSystem.enableDepthTest();
        }

        public TextureAtlasSprite getSprite() {
            return spriteUploader.getSprite(location);
        }

        @Override
        public String toString() {
//            Minecraft minecraft = Minecraft.getInstance();
//            TextureManager textureManager = minecraft.getTextureManager();
//            textureManager.bindForSetup(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
            TextureAtlasSprite icon = getSprite();

            if (icon != null) {
                return "icon: " + icon;
            } else {
                return "icon is null for location: " + location.toString();
            }
        }

        public void drawLightning(MultiBufferSource bufferIn, PoseStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Color color) {
            TextureAtlasSprite icon = getSprite();
            bindTexture();
            drawLightningTextured(bufferIn.getBuffer(RenderType.lightning()),
                    matrixStack.last().pose(),
                    x1,
                    y1,
                    z1,
                    x2,
                    y2,
                    z2,
                    color,
                    icon,
                    this.width,
                    this.height);
        }
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon8(location, matrixStack, left, top, right, bottom, zLevel, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param color color to apply to the texture
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel, Color color) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, zLevel, 8, 8, 0, 0, 8, 8, color);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon16(location, matrixStack, left, top, right, bottom, zLevel, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param color color to apply to the texture
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel, Color color) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, zLevel, 16, 16, 0, 0, 16, 16, color);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel depth at which to draw (usually Minecraft.getInstance().currentScreen.getBlitLevel())
     * @param iconWidth actual width of the icon to draw
     * @param iconHeight actual height of the icon to draw
     * @param texStartX the leftmost point of the texture on the sheet (usually 0 for an icon)
     * @param texStartY the topmost point of the texture on the sheet (usually 0 for an icon)
     * @param textureWidth the width of the texture (often 8 or 16 for icons)
     * @param textureHeight the height of the texture (often 8 or 16 for icons)
     * @param color the Color to apply to the texture
     */
    public static void renderTextureWithColor(ResourceLocation location, PoseStack matrixStack,
                                              double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Color color) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        innerBlit(matrixStack, left, right, top, bottom, zLevel, iconWidth, iconHeight, texStartX, texStartY, textureWidth, textureHeight, color);
        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
    }

    /**
     *
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel depth to render at
     * @param iconWidth width of the portion of the texture to display
     * @param iconHeight iconHeight of the portion of texture to display
     * @param texStartX location of the left of the texture on the sheet
     * @param texStartY location of the top of the texture on the sheet
     * @param textureWidth total texture sheet width
     * @param textureHeight total texture sheet iconHeight
     * @param color color to apply to the texture
     */
    private static void innerBlit(PoseStack matrixStack, double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Color color) {
        innerBlit(matrixStack.last().pose(), left, right, top, bottom, zLevel,
                (float)((texStartX + 0.0F) / textureWidth),
                (float)((texStartX + iconWidth) / textureWidth),
                (float)((texStartY + 0.0F) / textureHeight),
                (float)((texStartY + iconHeight) / textureHeight),
                color);
    }

    /**
     * Basically like vanilla's version but with floats and a color parameter
     * Only does the inner texture rendering
     *
     * @param matrix4f
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel the depth position of the drawing rectangle
     * Note: UV positions are scaled (0.0 - 1.0)
     * @param minU the left most UV mapped position
     * @param maxU the right most UV mapped position
     * @param minV the top most UV mapped position
     * @param maxV the bottom most UV mapped position
     * @param color the Color to apply to the texture
     */
    private static void innerBlit(Matrix4f matrix4f, double left, double right, double top, double bottom, float zLevel, float minU, float maxU, float minV, float maxV, Color color) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        color.setShaderColor();


        // bottom left
        bufferbuilder.addVertex(matrix4f, (float)left, (float)bottom, zLevel).setUv(minU, maxV)
                .setColor(color.r, color.g, color.b, color.a);

        // bottom right
        bufferbuilder.addVertex(matrix4f, (float)right, (float)bottom, zLevel).setUv(maxU, maxV)
                .setColor(color.r, color.g, color.b, color.a);

        // top right
        bufferbuilder.addVertex(matrix4f, (float)right, (float)top, zLevel).setUv(maxU, minV)
                .setColor(color.r, color.g, color.b, color.a);

        // top left
        bufferbuilder.addVertex(matrix4f, (float)left, (float)top, zLevel).setUv(minU, minV)
                .setColor(color.r, color.g, color.b, color.a);

        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public void drawLightningTextured(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Color color, TextureAtlasSprite icon, float textureWidth, float textureHeight) {
        float minV = icon.getV0();
        float maxV = icon.getV1();
        float uSize = icon.getU1() - icon.getU0();

        float tx = x2 - x1, ty = y2 - y1, tz = z2 - z1;
        float ax, ay, az;
        float bx, by, bz;
        float cx = 0, cy = 0, cz = 0;
        float jagfactor = 0.3F;
        while (Math.abs(cx) < Math.abs(tx) && Math.abs(cy) < Math.abs(ty) && Math.abs(cz) < Math.abs(tz)) {
            ax = x1 + cx;
            ay = y1 + cy;
            az = z1 + cz;
            cx += Math.random() * tx * jagfactor - 0.1 * tx;
            cy += Math.random() * ty * jagfactor - 0.1 * ty;
            cz += Math.random() * tz * jagfactor - 0.1 * tz;
            bx = x1 + cx;
            by = y1 + cy;
            bz = z1 + cz;

            int index = getRandomNumber(0, 50);
            float minU = icon.getU0() + uSize * (index * 0.2F); // 1/50, there are 50 different lightning elements in the texture
            float maxU = minU + uSize * 0.2F;

            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, color, minU, maxU, minV, maxV);
        }
    }

    void drawLightningBetweenPointsFast(VertexConsumer bufferIn,
                                        Matrix4f matrix4f,
                                        float x1,
                                        float y1,
                                        float z1,
                                        float x2,
                                        float y2,
                                        float z2,
                                        Color color,
                                        float minU, float maxU, float minV, float maxV) {
        float px = (y1 - y2) * 0.125F;
        float py = (x2 - x1) * 0.125F;

        bufferIn.addVertex(matrix4f, x1 - px, y1 - py, z1) // top left front
                .setColor(color.r, color.g, color.b, color.a)
                .setUv(minU, minV)
                .setLight(0x00F000F0);

        bufferIn.addVertex(matrix4f, x1 + px, y1 + py, z1) // bottom right front
                .setColor(color.r, color.g, color.b, color.a)
                .setUv(maxU, minV) // right top
                .setLight(0x00F000F0);

        bufferIn.addVertex(matrix4f, x2 - px, y2 - py, z2) //  top left back
                .setColor(color.r, color.g, color.b, color.a)
                .setUv(minU, maxV) // left bottom
                .setLight(0x00F000F0);

        bufferIn.addVertex(matrix4f, x2 + px, y2 + py, z2) // bottom right back
                .setColor(color.r, color.g, color.b, color.a)
                .setUv(maxU, maxV) // right bottom
                .setLight(0x00F000F0);
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    void bindTexture() {
        RenderSystem.setShaderTexture(0, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
    }

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

//    public void unRotate() {
//        BillboardHelper.unRotate();
//    }
}