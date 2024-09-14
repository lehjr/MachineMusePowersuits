package com.lehjr.numina.client.gui;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

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
    public static final Map<ResourceLocation, DrawableIcon> iconMap = new HashMap<>();


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
    }

    private DrawableIcon registerIcon(String name, int width, int height) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, name);
        DrawableIcon icon = new DrawableIcon(location, width, height);
        iconMap.put(location, icon);
        return icon;
    }

    public static DrawableIcon getIcon(ResourceLocation location) {
        return iconMap.get(location);
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

        public TextureAtlasSprite getSprite() {
            return spriteUploader.getSprite(location);
        }

        public ResourceLocation getLocation() {
            return location;
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
            draw(matrixStack, (float)x, (float)y, 0, 0, 0, 0, color);
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
            double posLeft, double posTop, double width, double height, Color color) {
            renderIconScaledWithColor(matrixStack, (float)posLeft, (float)posTop, (float)width, (float)height, 0, color);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
            float posLeft, float posTop, float width, float height, float blitOffset, Color color) {
            bindNuminaGuiAtlas();
            TextureAtlasSprite icon = getSprite();
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.enableBlend();
            IconUtils.innerBlit(matrixStack.last().pose(), posLeft, posLeft + width, posTop, posTop + height, blitOffset, icon.getU0(), icon.getU1(), icon.getV0(), icon.getV1(), color);
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }

        public void draw(PoseStack poseStack, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight, Color color) {
            float textureWidth = this.width;
            float textureHeight = this.height;

            bindNuminaGuiAtlas();
            TextureAtlasSprite icon = getSprite();
            float zLevel = 0;

            float posLeft = xOffset + maskLeft;
            float posTop = yOffset + maskTop;
            float width = textureWidth - maskRight - maskLeft;
            float height = textureHeight - maskBottom - maskTop;

            float posRight = posLeft + width;
            float posBottom = posTop + height;

            float uSize = icon.getU1() - icon.getU0();
            float vSize = icon.getV1() - icon.getV0();
            float minU = icon.getU0() + uSize * (maskLeft / textureWidth);
            float minV = icon.getV0() + vSize * (maskTop / textureHeight);
            float maxU = icon.getU1() - uSize * (maskRight / textureWidth);
            float maxV = icon.getV1() - vSize * (maskBottom / textureHeight);

            IconUtils.innerBlit(poseStack.last().pose(), posLeft, posRight, posTop, posBottom, zLevel, minU, maxU, minV, maxV, color);
        }

        @Override
        public String toString() {
            TextureAtlasSprite icon = getSprite();

            if (icon != null) {
                return "icon: " + icon;
            } else {
                return "icon is null for location: " + location.toString();
            }
        }

        public void drawLightning(MultiBufferSource bufferIn, PoseStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Color color) {
            TextureAtlasSprite icon = getSprite();
            bindNuminaGuiAtlas();
            IconUtils.drawLightningTextured(bufferIn.getBuffer(RenderType.lightning()),
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
        renderTextureWithColor(location, matrixStack, (float)left, (float)right, (float)top, (float)bottom, zLevel, 8, 8, 0, 0, 8, 8, color);
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
        renderTextureWithColor(location, matrixStack, (float)left, (float)right, (float)top, (float)bottom, zLevel, 16, 16, 0, 0, 16, 16, color);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param blitOffset depth at which to draw (usually Minecraft.getInstance().currentScreen.getBlitLevel())
     * @param iconWidth actual width of the icon to draw
     * @param iconHeight actual height of the icon to draw
     * @param texStartX the leftmost point of the texture on the sheet (usually 0 for an icon)
     * @param texStartY the topmost point of the texture on the sheet (usually 0 for an icon)
     * @param textureWidth the width of the texture (often 8 or 16 for icons)
     * @param textureHeight the height of the texture (often 8 or 16 for icons)
     * @param color the Color to apply to the texture
     */
    public static void renderTextureWithColor(ResourceLocation location, PoseStack matrixStack,
        float left, float right, float top, float bottom, float blitOffset, float iconWidth, float iconHeight, float texStartX, float texStartY, float textureWidth, float textureHeight, Color color) {
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        IconUtils.innerBlit(matrixStack, left, right, top, bottom, blitOffset, iconWidth, iconHeight, texStartX,
            texStartY,
            textureWidth,
            textureHeight, color);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public static void bindNuminaGuiAtlas() {
        bindAtlass(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
    }

    /**
     * if the texture is already in an atlas then bind the atlas and use the info from the atlas
     * @param atlasLocation
     */
    public static void bindAtlass(ResourceLocation atlasLocation) {
        RenderSystem.setShaderTexture(0, atlasLocation);
    }
}
