package com.lehjr.numina.common.utils;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.client.gui.NuminaSpriteUploader;
import com.lehjr.numina.client.gui.geometry.SwirlyMuseCircle;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FIXME: sort this mess out and merge redundant code with NuminaIcons
public enum IconUtils {
    INSTANCE;
    private NuminaIcons icon;
    public static NuminaIcons getIcon() {
        if (INSTANCE.icon == null) {
            Minecraft minecraft = Minecraft.getInstance();
            TextureManager textureManager = minecraft.getTextureManager();
            NuminaSpriteUploader spriteUploader = new NuminaSpriteUploader(textureManager);
            INSTANCE.icon = new NuminaIcons(spriteUploader);
        }
        return INSTANCE.icon;
    }

    // FIXME: Needed?
    public static void drawLightningBetweenPointsFast(double x1, double y1, double z1, double x2, double y2, double z2, int index) {

        double u1 = index / 50.0;
        double u2 = u1 + 0.02;
        double px = (y1 - y2) * 0.125;
        double py = (x2 - x1) * 0.125;
        GL11.glTexCoord2d(u1, 0);
        GL11.glVertex3d(x1 - px, y1 - py, z1);
        GL11.glTexCoord2d(u2, 0);
        GL11.glVertex3d(x1 + px, y1 + py, z1);
        GL11.glTexCoord2d(u1, 1);
        GL11.glVertex3d(x2 - px, y2 - py, z2);
        GL11.glTexCoord2d(u2, 1);
        GL11.glVertex3d(x2 + px, y2 + py, z2);
    }

    public static void drawLightningTextured(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        double tx = x2 - x1, ty = y2 - y1, tz = z2 - z1;

        //        double ax = 0, ay = 0, az = 0;
        //        double bx = 0, by = 0, bz = 0;
        //        double cx = 0, cy = 0, cz = 0;
        //
        //        double jagfactor = 0.3;
        //        RenderState.on2D();
        //        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //        MuseTextureUtils.pushTexture(Config.LIGHTNING_TEXTURE);
        //        RenderState.blendingOn();
        //        color.doGL();
        //        GL11.glBegin(GL11.GL_QUADS);
        //        while (Math.abs(cx) < Math.abs(tx) && Math.abs(cy) < Math.abs(ty) && Math.abs(cz) < Math.abs(tz)) {
        //            ax = x1 + cx;
        //            ay = y1 + cy;
        //            az = z1 + cz;
        //            cx += Math.random() * tx * jagfactor - 0.1 * tx;
        //            cy += Math.random() * ty * jagfactor - 0.1 * ty;
        //            cz += Math.random() * tz * jagfactor - 0.1 * tz;
        //            bx = x1 + cx;
        //            by = y1 + cy;
        //            bz = z1 + cz;
        //
        //            int index = (int) (Math.random() * 50);
        //
        //            drawLightningBetweenPointsFast(ax, ay, az, bx, by, bz, index);
        //        }
        //        GL11.glEnd();
        //        RenderState.blendingOff();
        //        RenderState.off2D();
    }

    public static void drawMPDLightning(Matrix4f matrix4f,
        float x1, float y1, float z1,
        float x2, float y2, float z2,
        Color color,
        double displacement,
        double detail) {
        if (displacement < detail) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

            color.addToVertex(bufferbuilder.addVertex(matrix4f, x1, y1, z1));
            color.addToVertex(bufferbuilder.addVertex(matrix4f, x2, y2, z2));

            //            tesselator.end();
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());

            RenderSystem.enableDepthTest();
            //            RenderSystem.enableTexture();
            RenderSystem.depthMask(true);
        } else {
            float mid_x = (x1 + x2)  * 0.5F;
            float mid_y = (y1 + y2) * 0.5F;
            float mid_z = (z1 + z2) * 0.5F;
            mid_x += (Math.random() - 0.5) * displacement;
            mid_y += (Math.random() - 0.5) * displacement;
            mid_z += (Math.random() - 0.5) * displacement;
            drawMPDLightning(matrix4f, x1, y1, z1, mid_x, mid_y, mid_z, color, displacement * 0.5F, detail);
            drawMPDLightning(matrix4f, mid_x, mid_y, mid_z, x2, y2, z2, color, displacement * 0.5F, detail);
        }
    }

    public static void drawLightning(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        drawLightningTextured(x1, y1, z1, x2, y2, z2, color);
    }

    /**
     * Only used by the energy meter
     * @param gfx
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @param color
     * @param displacement
     * @param detail
     */
    public static void drawMPDLightning(GuiGraphics gfx, float x1, float y1, float z1, float x2, float y2, float z2, Color color, double displacement, double detail) {
        drawMPDLightning(gfx.pose(), x1, y1, z1, x2, y2, z2, color, displacement, detail);
    }

    public static void drawMPDLightning(PoseStack poseStack, float x1, float y1, float z1, float x2, float y2, float z2, Color color, double displacement,
        double detail) {
        Matrix4f matrix4f = poseStack.last().pose();
        ShaderInstance oldShader = RenderSystem.getShader();
        float lineWidth = RenderSystem.getShaderLineWidth();
        RenderSystem.lineWidth(1F);
        RenderSystem.enableBlend();

        drawMPDLightning(matrix4f, x1, y1, z1, x2, y2, z2, color, displacement * 0.5F, detail);

        RenderSystem.disableBlend();
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.setShader(() -> oldShader);
    }


    /**
     * Does the rotating green circle around the selection, e.g. in GUI.
     *
     *
     * @param matrixStack
     * @param xoffset
     * @param yoffset
     * @param radius
     * @param zLevel
     */
    public static void drawCircleAround(SwirlyMuseCircle selectionCircle, PoseStack matrixStack, double xoffset, double yoffset, double radius, float zLevel) {
        selectionCircle.draw(matrixStack, (float) radius, xoffset, yoffset, zLevel);
    }

    public static ItemRenderer getItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    public static TextureManager getTextureManager() {
        return Minecraft.getInstance().getTextureManager();
    }

    static ItemModelShaper getItemModelShaper() {
        return getItemRenderer().getItemModelShaper();
    }

    public static void drawItemAt(GuiGraphics gfx, double x, double y, @Nonnull ItemStack itemStack, Color color) {
        if (!itemStack.isEmpty()) {
            gfx.renderItem(itemStack, (int) x, (int) y);
            gfx.renderItemDecorations(StringUtils.getFontRenderer(), itemStack, (int) x, (int) y, null);
        }
    }

    static BakedModel getModel(@Nonnull ItemStack itemStack) {
        return getItemRenderer().getModel(itemStack, null, null, 0);
    }

    public static void drawModuleAt(GuiGraphics gfx, double x, double y, @Nonnull ItemStack itemStack, boolean active) {
        if (!itemStack.isEmpty()) {
            BakedModel model = getModel(itemStack);
            renderGuiItem(itemStack, gfx.pose(), (float)x, (float) y, model, active? Color.WHITE : Color.DARK_GRAY.withAlpha(0.5F));
        }
    }

    private static void renderGuiItem(ItemStack itemStack, PoseStack poseStack, float posX, float posY, BakedModel bakedModel, Color color) {
        getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(posX, posY, 100.0F + 0);
        poseStack.translate(8.0F, 8.0F, 0.0F);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        //        RenderSystem.applyModelViewMatrix();
        //        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedModel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        render(itemStack, ItemDisplayContext.GUI, false, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, bakedModel, color);
        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static void render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel modelIn, Color color) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            boolean flag = displayContext == ItemDisplayContext .GUI || displayContext == ItemDisplayContext.GROUND || displayContext == ItemDisplayContext.FIXED;

            modelIn = ClientHooks.handleCameraTransforms(poseStack, modelIn, displayContext, leftHand);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            if (!modelIn.isCustomRenderer() && (!itemStack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (displayContext != ItemDisplayContext.GUI && !displayContext.firstPerson() && itemStack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)itemStack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : modelIn.getRenderPasses(itemStack, flag1)) {
                    for (var rendertype : model.getRenderTypes(itemStack, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (itemStack.is(ItemTags.COMPASSES) && itemStack.hasFoil()) {
                            poseStack.pushPose();
                            PoseStack.Pose posestack$pose = poseStack.last();
                            if (displayContext == ItemDisplayContext.GUI) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                            } else if (displayContext.firstPerson()) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                            }

                            if (flag1) {
                                vertexconsumer =  ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, itemStack.hasFoil());
                            } else {
                                getItemRenderer();
                                vertexconsumer = ItemRenderer.getCompassFoilBuffer(buffer, rendertype, posestack$pose);
                            }

                            poseStack.popPose();
                        } else if (flag1) {
                            getItemRenderer();
                            vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, itemStack.hasFoil());
                        } else {
                            getItemRenderer();
                            vertexconsumer = ItemRenderer.getFoilBuffer(buffer, rendertype, true, itemStack.hasFoil());
                        }

                        renderModelLists(model, itemStack, combinedLight, combinedOverlay, poseStack, vertexconsumer, color);
                    }
                }
            } else {
                IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, displayContext, poseStack, buffer, combinedLight, combinedOverlay);
            }

            poseStack.popPose();
        }
    }

    public static void renderModelLists(BakedModel pModel, ItemStack pStack, int pCombinedLight, int pCombinedOverlay, PoseStack pMatrixStack, VertexConsumer pBuffer, Color color) {
        RandomSource randomsource = RandomSource.create();
        long i = 42L;

        for(Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            renderQuads(pMatrixStack, pBuffer, pModel.getQuads(null, direction, randomsource), pStack, pCombinedLight, pCombinedOverlay, color);
        }

        randomsource.setSeed(42L);
        renderQuads(pMatrixStack, pBuffer, pModel.getQuads(null, null, randomsource), pStack, pCombinedLight, pCombinedOverlay, color);
    }
    public static void renderQuads(PoseStack matrixStackIn, VertexConsumer bufferIn, List<BakedQuad> quadsIn, ItemStack itemStackIn, int combinedLightIn, int combinedOverlayIn, Color color) {
        if (color == null) {
            Minecraft.getInstance().getItemRenderer().renderQuadList(matrixStackIn, bufferIn, quadsIn, itemStackIn, combinedLightIn, combinedOverlayIn);
        } else {
            PoseStack.Pose matrixstack$entry = matrixStackIn.last();

            for (BakedQuad bakedquad : quadsIn) {
                bufferIn.putBulkData(matrixstack$entry, bakedquad, color.r, color.g, color.b, color.a, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }

    //
    //    public static void renderItem(ItemStack itemStack, ItemDisplayContext transformType, boolean leftHand, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, Color color) {
    //        if (!itemStack.isEmpty()) {
    //            matrixStack.pushPose();
    //            boolean flag = transformType == ItemDisplayContext.GUI || transformType == ItemDisplayContext.GROUND || transformType == ItemDisplayContext.FIXED;
    //            if (flag) {
    //                if (itemStack.is(Items.TRIDENT)) {
    //                    model = getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
    //                } else if (itemStack.is(Items.SPYGLASS)) {
    //                    model = getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
    //                }
    //            }
    //
    //            model = net.neoforged.neoforge.client.ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, leftHand);
    //            matrixStack.translate(-0.5D, -0.5D, -0.5D);
    //            if (!model.isCustomRenderer() && (!itemStack.is(Items.TRIDENT) || flag)) {
    //                boolean flag1;
    //                if (transformType != ItemDisplayContext.GUI && !transformType.firstPerson() && itemStack.getItem() instanceof BlockItem) {
    //                    Block block = ((BlockItem)itemStack.getItem()).getBlock();
    //                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
    //                } else {
    //                    flag1 = true;
    //                }
    //                if (model.isLayered()) { net.neoforged.neoforge.client.ForgeHooksClient.drawItemLayered(getItemRenderer(), model, itemStack, matrixStack, buffer, combinedLight, combinedOverlay, flag1); }
    //                else {
    //                    RenderType rendertype = ItemBlockRenderTypes.getRenderType(itemStack, flag1);
    //                    VertexConsumer vertexconsumer;
    //                    if (itemStack.is(Items.COMPASS) && itemStack.hasFoil()) {
    //                        matrixStack.pushPose();
    //                        PoseStack.Pose posestack$pose = matrixStack.last();
    //                        if (transformType == ItemDisplayContext.GUI) {
    //                            posestack$pose.pose().m_27630_(0.5F);
    //                        } else if (transformType.firstPerson()) {
    //                            posestack$pose.pose().m_27630_(0.75F);
    //                        }
    //
    //                        if (flag1) {
    //                            vertexconsumer = getItemRenderer().getCompassFoilBufferDirect(buffer, rendertype, posestack$pose);
    //                        } else {
    //                            vertexconsumer = getItemRenderer().getCompassFoilBuffer(buffer, rendertype, posestack$pose);
    //                        }
    //
    //                        matrixStack.popPose();
    //                    } else if (flag1) {
    //                        vertexconsumer = getItemRenderer().getFoilBufferDirect(buffer, rendertype, true, itemStack.hasFoil());
    //                    } else {
    //                        vertexconsumer = getItemRenderer().getFoilBuffer(buffer, rendertype, true, itemStack.hasFoil());
    //                    }
    //
    //                    renderModel(model, itemStack, combinedLight, combinedOverlay, matrixStack, vertexconsumer, color);
    //                }
    //            } else {
    //                net.neoforged.neoforge.client.RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
    //            }
    //
    //            matrixStack.popPose();
    //        }
    //    }
    //
    //    public static void renderModel(BakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, VertexConsumer bufferIn, Color color) {
    //        Random random = new Random();
    //        long i = 42L;
    //
    //        for(Direction direction : Direction.values()) {
    //            random.setSeed(42L);
    //            renderQuads(matrixStackIn, bufferIn, modelIn.m_6840_((BlockState)null, direction, random), stack, combinedLightIn, combinedOverlayIn, color);
    //        }
    //
    //        random.setSeed(42L);
    //        renderQuads(matrixStackIn, bufferIn, modelIn.m_6840_((BlockState)null, (Direction)null, random), stack, combinedLightIn, combinedOverlayIn, color);
    //    }
    //



















    static TextureAtlasSprite getMissingIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
    }

    /**
     * Draws a TextureAtlasSprite
     *
     * @param x
     * @param y
     * @param icon
     * @param color
     */
    public static void drawIconAt(float x, float y, @Nonnull TextureAtlasSprite icon, Color color) {
        drawIconPartial(x, y, icon, color, 0, 0, 16, 16);
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param argbColor
     */
    public static void drawIconAt(PoseStack poseStack, double x, double y, @Nonnull TextureAtlasSprite icon, int argbColor) {
        drawIconPartial(poseStack, x, y, icon, argbColor, 0, 0, icon.contents().width(), icon.contents().height());
    }




    public static void drawIconPartialOccluded(float x, float y, @Nonnull TextureAtlasSprite icon, Color color, float textureStarX, float textureStartY, float textureEndX, float textureEndY) {
        float xmin = MathUtils.clampFloat(textureStarX - x, 0, icon.contents().width());
        float ymin = MathUtils.clampFloat(textureStartY - y, 0, icon.contents().height());
        float xmax = MathUtils.clampFloat(textureEndX - x, 0, icon.contents().width());
        float ymax = MathUtils.clampFloat(textureEndY - y, 0, icon.contents().height());
        drawIconPartial(x, y, icon, color, xmin, ymin, xmax, ymax);
    }

    /**
     * Draws a MuseIcon
     *
     * @param posX
     * @param posY
     * @param icon
     * @param argbColor
     */
    public static void drawIconPartial(PoseStack poseStack, double posX, double posY, TextureAtlasSprite icon, int argbColor, double textureStartX, double textureStartY, double textureEndX, double textureEndY) {
        if (icon == null) {
            icon = getMissingIcon();
        }
        RenderSystem.setShaderTexture(0, icon.atlasLocation());
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();


        float iconWidthDiv = 1F/icon.contents().width();
        float icomHeightDiv = 1F/icon.contents().height();

        double xoffsetMin = textureStartX * (maxU - minU) * iconWidthDiv; // 0
        double xoffsetMax = textureEndX * (maxU - minU) * iconWidthDiv;
        double yoffsetMin = textureStartY * (maxV - minV) * icomHeightDiv; // 0
        double yoffsetMax = textureEndY * (maxV - minV) * icomHeightDiv;

        Matrix4f matrix4f = poseStack.last().pose();
        //        NuminaLogger.logDebug("textureStartX: " + textureStartX + ", textureEndX: " + textureEndX + ", textureStartY: " + textureStartY +", textureEndY: " + textureEndY);
        //        NuminaLogger.logDebug("icon.contents: " + icon.contents());
        // top left
        bufferBuilder.addVertex(matrix4f, (float) (posX + textureStartX), (float)(posY + textureStartY), 0)
            .setUv((float) (minU + xoffsetMin), (float) (minV + yoffsetMin))
            .setColor(argbColor);

        // textureEndY left
        bufferBuilder.addVertex(matrix4f, (float) (posX + textureStartX), (float) (posY + textureEndY), 0)
            .setUv((float) (minU + xoffsetMin), (float) (minV + yoffsetMax))
            .setColor(argbColor);

        // textureEndY right
        bufferBuilder.addVertex(matrix4f, (float) (posX + textureEndX), (float) (posY + textureEndY), 0)
            .setUv((float) (minU + xoffsetMax), (float) (minV + yoffsetMax))
            .setColor(argbColor);

        // top right
        bufferBuilder.addVertex(matrix4f, (float) (posX + textureEndX), (float) (posY + textureStartY), 0)
            .setUv((float) (minU + xoffsetMax), (float) (minV + yoffsetMin))
            .setColor(argbColor);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param color
     */
    public static void drawIconPartial(float x, float y, TextureAtlasSprite icon, Color color, float left, float top, float right, float bottom) {
        if (icon == null) {
            icon = getMissingIcon();
        }

        RenderSystem.setShaderTexture(0, icon.atlasLocation());
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tess.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        float uMin = icon.getU0();
        float vMin = icon.getV0();
        float uMax = icon.getU1();
        float vMax = icon.getV1();

        float xoffset1 = left * (uMax - uMin) / 16.0f;
        float yoffset1 = top * (vMax - vMin) / 16.0f;
        float xoffset2 = right * (uMax - uMin) / 16.0f;
        float yoffset2 = bottom * (vMax - vMin) / 16.0f;

        // top left
        bufferBuilder.addVertex((float) (x + left), (float) (y + top), 0)
            .setColor(color.r, color.g, color.b, color.a)
            .setUv(uMin + xoffset1, vMin + yoffset1);

        // bottom left
        bufferBuilder.addVertex(x + left, y + bottom, 0)
            .setColor(color.r, color.g, color.b, color.a)
            .setUv(uMin + xoffset1, vMin + yoffset2);


        // bottom right
        bufferBuilder.addVertex(x + right, y + bottom, 0)
            .setColor(color.r, color.g, color.b, color.a)
            .setUv(uMin + xoffset2, vMin + yoffset2);

        // top right
        bufferBuilder.addVertex(x + right, y + top, 0)
            .setColor(color.r, color.g, color.b, color.a)
            .setUv(uMin + xoffset2, vMin + yoffset1);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    }

    public static final Map<EquipmentSlot, ResourceLocation> ARMOR_SLOT_TEXTURES = new HashMap<>(){{
        put(EquipmentSlot.HEAD, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
        put(EquipmentSlot.CHEST, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
        put(EquipmentSlot.LEGS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS);
        put(EquipmentSlot.FEET, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS);
        put(EquipmentSlot.OFFHAND, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
        put(EquipmentSlot.MAINHAND, NuminaConstants.WEAPON_SLOT_BACKGROUND);
    }};

    public static final Pair<ResourceLocation, ResourceLocation> getSlotBackground(EquipmentSlot slotType) {
        switch (slotType) {
        case MAINHAND:
            return Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
        default:
            return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
        }
    }

    /** Code below based on Minecraft's AbstractGui ------------------------------------------------------------------------------------------------------------------------ */

    public static void blit(PoseStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite pSprite) {
        innerBlit(matrixStack.last().pose(),
            posX, // drawStartX
            posX + drawWidth, // drawEndX
            posY, // drawStartY
            posY + drawHeight, // drawEndY
            pBlitOffset, // z
            pSprite.getU0(), // minU
            pSprite.getU1(), // maxU
            pSprite.getV0(), // minV
            pSprite.getV1()); // maxV
    }

    public void blit(PoseStack pPoseStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight) {
        blit(pPoseStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double blitOffset, double uOffset, double vOffset, double uWidth, double vHeight, double textureHeight, double textureWidth) {
        innerBlit(pPoseStack, posX, posX + uWidth, posY, posY + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double pBlitOffset, double pUOffset, double vOffset, double uWidth, double pVHeight, double textureHeight, double textureWidth, Color color) {
        innerBlit(pPoseStack, posX, posX + uWidth, posY, posY + pVHeight, pBlitOffset, uWidth, pVHeight, pUOffset, vOffset, textureWidth, textureHeight, color);
    }

    public static void blit(PoseStack pPoseStack, double drawStartX, double drawStartY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight) {
        blit(pPoseStack, drawStartX, drawStartY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, double drawStartX, double drawStartY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight) {
        innerBlit(pPoseStack, drawStartX, drawStartX + drawWidth, drawStartY, drawStartY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void innerBlit(PoseStack pPoseStack, double posX, double pX2, double pY1, double pY2, double pBlitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight) {
        innerBlit(pPoseStack.last().pose(), posX, pX2, pY1, pY2, pBlitOffset,
            (uOffset + 0.0F) / textureWidth,
            (uOffset + uWidth) / textureWidth, (vOffset + 0.0F) / textureHeight,
            (vOffset + vHeight) / textureHeight);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset).setUv((float) uMin, (float) vMax);
        bufferbuilder.addVertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset).setUv((float) uMax, (float) vMax);
        bufferbuilder.addVertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset).setUv((float) uMax, (float) vMin);
        bufferbuilder.addVertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset).setUv((float) uMin, (float) vMin);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    public static void blit(PoseStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite sprite, Color color) {
        innerBlit(matrixStack.last().pose(),
            posX, // drawStartX
            posX + drawWidth, // drawEndX
            posY, // drawStartY
            posY + drawHeight, // drawEndY
            pBlitOffset, // z
            sprite.getU0(), // minU
            sprite.getU1(), // maxU
            sprite.getV0(), // minV
            sprite.getV1(),// maxV
            color);
    }

    public void blit(PoseStack pPoseStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight, Color color) {
        blit(pPoseStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256, color);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight, Color color) {
        blit(pPoseStack, posX, posY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight, color);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight, Color color) {
        innerBlit(pPoseStack, posX, posX + drawWidth, posY, posY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight, color);
    }

    public static void innerBlit(PoseStack pPoseStack, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight, Color color) {
        innerBlit(pPoseStack.last().pose(), drawStartX, drawEndX, drawStartY, drawEndY, blitOffset,
            (uOffset) / textureWidth,
            (uOffset + uWidth) / textureWidth,
            (vOffset) / textureHeight,
            (vOffset + vHeight) / textureHeight,
            color);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax, Color color) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        bufferbuilder.addVertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset)
            .setUv((float) uMin, (float) vMax)
            .setColor(color.r, color.g, color.b, color.a);
        bufferbuilder.addVertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset)
            .setUv((float) uMax, (float) vMax)
            .setColor(color.r, color.g, color.b, color.a);
        bufferbuilder.addVertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset)
            .setUv((float) uMax, (float) vMin)
            .setColor(color.r, color.g, color.b, color.a);
        bufferbuilder.addVertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset)
            .setUv((float) uMin, (float) vMin)
            .setColor(color.r, color.g, color.b, color.a);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    double getBlitOffset() {
        return 0;
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
}
