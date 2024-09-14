package com.lehjr.numina.common.utils;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.client.gui.NuminaSpriteUploader;
import com.lehjr.numina.client.gui.geometry.SwirlyMuseCircle;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
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
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots");

    public static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.withDefaultNamespace("item/empty_slot_hoe");
    public static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.withDefaultNamespace("item/empty_slot_axe");
    public static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.withDefaultNamespace("item/empty_slot_sword");
    public static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.withDefaultNamespace("item/empty_slot_shovel");
    public static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe");

    public static final Map<EquipmentSlot, ResourceLocation> ARMOR_SLOT_TEXTURES = new HashMap<>(){{
        put(EquipmentSlot.HEAD, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
        put(EquipmentSlot.CHEST, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
        put(EquipmentSlot.LEGS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS);
        put(EquipmentSlot.FEET, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS);
        put(EquipmentSlot.OFFHAND, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
        put(EquipmentSlot.MAINHAND, EMPTY_SLOT_SWORD);
    }};

    public static final Pair<ResourceLocation, ResourceLocation> getSlotBackground(EquipmentSlot slotType) {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
    }

    static final Map<ModuleCategory, Pair<ResourceLocation, ResourceLocation>> slotBackgrounds = new HashMap<>(){{
        put(ModuleCategory.PICKAXE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_PICKAXE));
        put(ModuleCategory.SHOVEL, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_SHOVEL));
        put(ModuleCategory.AXE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_AXE));
        put(ModuleCategory.HOE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_HOE));

        put(ModuleCategory.ARMOR, Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD));

        put(ModuleCategory.ENERGY_STORAGE, Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, getIcon().energystorage.getLocation()));
        put(ModuleCategory.ENERGY_GENERATION, Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, getIcon().energygeneration.getLocation()));
    }};

    @Nullable
    public static Pair<ResourceLocation, ResourceLocation> getIconLocationPairForCategory(ModuleCategory category) {
        if(slotBackgrounds.containsKey(category)) {
            return slotBackgrounds.get(category);
        }
        return null;
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

    static TextureAtlasSprite getMissingIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
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





    //    /**
    //     * Draws a TextureAtlasSprite
    //     *
    //     * @param x
    //     * @param y
    //     * @param icon
    //     * @param color
    //     */
    //    public static void drawIconAt(float x, float y, @Nonnull TextureAtlasSprite icon, Color color) {
    //        drawIconPartial(x, y, icon, color, 0, 0, 16, 16);
    //    }
    //
    //    public static void drawIconPartialOccluded(float x, float y, @Nonnull TextureAtlasSprite icon, Color color, float textureStarX, float textureStartY, float textureEndX, float textureEndY) {
    //        float xmin = MathUtils.clampFloat(textureStarX - x, 0, icon.contents().width());
    //        float ymin = MathUtils.clampFloat(textureStartY - y, 0, icon.contents().height());
    //        float xmax = MathUtils.clampFloat(textureEndX - x, 0, icon.contents().width());
    //        float ymax = MathUtils.clampFloat(textureEndY - y, 0, icon.contents().height());
    //        drawIconPartial(x, y, icon, color, xmin, ymin, xmax, ymax);
    //    }
    //
    //    /**
    //     * Draws a MuseIcon
    //     *
    //     * @param x
    //     * @param y
    //     * @param icon
    //     * @param color
    //     */
    //    public static void drawIconPartial(float x, float y, TextureAtlasSprite icon, Color color, float left, float top, float right, float bottom) {
    //        if (icon == null) {
    //            icon = getMissingIcon();
    //        }
    //
    //        RenderSystem.setShaderTexture(0, icon.atlasLocation());
    //        Tesselator tess = Tesselator.getInstance();
    //        BufferBuilder bufferBuilder = tess.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
    //
    //        float uMin = icon.getU0();
    //        float vMin = icon.getV0();
    //        float uMax = icon.getU1();
    //        float vMax = icon.getV1();
    //
    //        float xoffset1 = left * (uMax - uMin) / 16.0f;
    //        float yoffset1 = top * (vMax - vMin) / 16.0f;
    //        float xoffset2 = right * (uMax - uMin) / 16.0f;
    //        float yoffset2 = bottom * (vMax - vMin) / 16.0f;
    //
    //        // top left
    //        bufferBuilder.addVertex(x + left, y + top, 0)
    //            .setColor(color.r, color.g, color.b, color.a)
    //            .setUv(uMin + xoffset1, vMin + yoffset1);
    //
    //        // bottom left
    //        bufferBuilder.addVertex(x + left, y + bottom, 0)
    //            .setColor(color.r, color.g, color.b, color.a)
    //            .setUv(uMin + xoffset1, vMin + yoffset2);
    //
    //
    //        // bottom right
    //        bufferBuilder.addVertex(x + right, y + bottom, 0)
    //            .setColor(color.r, color.g, color.b, color.a)
    //            .setUv(uMin + xoffset2, vMin + yoffset2);
    //
    //        // top right
    //        bufferBuilder.addVertex(x + right, y + top, 0)
    //            .setColor(color.r, color.g, color.b, color.a)
    //            .setUv(uMin + xoffset2, vMin + yoffset1);
    //        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
    //    }



    /** Code below based on Minecraft's AbstractGui ------------------------------------------------------------------------------------------------------------------------ */



    public void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float uOffset, float vOffset, float uWidth, float vHeight) {
        blit(pPoseStack, drawStartX, drawStartY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256);
    }

    public void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float uOffset, float vOffset, float uWidth, float vHeight, Color color) {
        blit(pPoseStack, drawStartX, drawStartY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256, color);
    }


    public static void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float uOffset, float vOffset, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        blit(pPoseStack, drawStartX, drawStartY, uWidth, vHeight, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float uOffset, float vOffset, float pWidth, float pHeight, float textureWidth, float textureHeight, Color color) {
        blit(pPoseStack, drawStartX, drawStartY, pWidth, pHeight, uOffset, vOffset, pWidth, pHeight, textureWidth, textureHeight, color);
    }



    public static void blit(PoseStack pPoseStack, float drawStartx, float drawStartY, float blitOffset, float uOffset, float vOffset, float uWidth, float vHeight, float textureHeight, float textureWidth) {
        innerBlit(pPoseStack, drawStartx, drawStartx + uWidth, drawStartY, drawStartY + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float blitOffset, float pUOffset, float vOffset, float uWidth, float vHeight, float textureHeight, float textureWidth, Color color) {
        innerBlit(pPoseStack,
            drawStartX,
            drawStartX + uWidth,
            drawStartY,
            drawStartY + vHeight,
            blitOffset,
            uWidth,
            vHeight,
            pUOffset,
            vOffset,
            textureWidth,
            textureHeight, color);
    }


    public static void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float drawWidth, float drawHeight, float uOffset, float vOffset, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        innerBlit(pPoseStack, drawStartX, drawStartX + drawWidth, drawStartY, drawStartY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, float drawStartX, float drawStartY, float drawWidth, float drawHeight, float uOffset, float vOffset, float uWidth, float vHeight, float textureWidth, float textureHeight, Color color) {
        innerBlit(pPoseStack, drawStartX, drawStartX + drawWidth, drawStartY, drawStartY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight, color);
    }


    public static void blit(PoseStack matrixStack, float drawStartX, float drawStartY, float pBlitOffset, float drawWidth, float drawHeight, TextureAtlasSprite pSprite) {
        innerBlit(matrixStack.last().pose(),
            drawStartX, // drawStartX
            drawStartX + drawWidth, // drawEndX
            drawStartY, // drawStartY
            drawStartY + drawHeight, // drawEndY
            pBlitOffset, // z
            pSprite.getU0(), // minU
            pSprite.getU1(), // maxU
            pSprite.getV0(), // minV
            pSprite.getV1()); // maxV
    }

    public static void blit(PoseStack matrixStack, float drawStartX, float drawStartY, float pBlitOffset, float drawWidth, float drawHeight, TextureAtlasSprite sprite, Color color) {
        innerBlit(matrixStack.last().pose(), drawStartX, // drawStartX
            drawStartX + drawWidth, // drawEndX
            drawStartY, // drawStartY
            drawStartY + drawHeight, // drawEndY
            pBlitOffset, // z
            sprite.getU0(), // minU
            sprite.getU1(), // maxU
            sprite.getV0(), // minV
            sprite.getV1(),// maxV
            color);
    }

    /**
     *
     * @param drawStartX the left most position of the drawing rectangle
     * @param drawEndX the right most position of the drawing rectangle
     * @param drawStartY the top most position of the drawing rectangle
     * @param drawEndY the bottom most position of the drawing rectangle
     * @param blitOffset depth to render at
     * @param iconWidth width of the portion of the texture to display
     * @param iconHeight iconHeight of the portion of texture to display
     * @param uOffset location of the left of the texture on the sheet
     * @param vOffset location of the top of the texture on the sheet
     * @param textureWidth total texture sheet width
     * @param textureHeight total texture sheet iconHeight
     */
    public static void innerBlit(PoseStack pPoseStack, float drawStartX, float drawEndX, float drawStartY, float drawEndY, float blitOffset, float iconWidth, float iconHeight, float uOffset, float vOffset, float textureWidth, float textureHeight) {
        innerBlit(pPoseStack.last().pose(),
            drawStartX,
            drawEndX,
            drawStartY,
            drawEndY,
            blitOffset,
            uOffset / textureWidth,
            (uOffset + iconWidth) / textureWidth,
            vOffset / textureHeight,
            (vOffset + iconHeight) / textureHeight);
    }

    /**
     *
     * @param drawStartX the left most position of the drawing rectangle
     * @param drawEndX the right most position of the drawing rectangle
     * @param drawStartY the top most position of the drawing rectangle
     * @param drawEndY the bottom most position of the drawing rectangle
     * @param blitOffset depth to render at
     * @param iconWidth width of the portion of the texture to display
     * @param iconHeight iconHeight of the portion of texture to display
     * @param uOffset location of the left of the texture on the sheet
     * @param vOffset location of the top of the texture on the sheet
     * @param textureWidth total texture sheet width
     * @param textureHeight total texture sheet iconHeight
     * @param color color to apply to the texture
     */
    public static void innerBlit(PoseStack matrixStack, float drawStartX, float drawEndX, float drawStartY, float drawEndY, float blitOffset, float iconWidth, float iconHeight, float uOffset, float vOffset, float textureWidth, float textureHeight, Color color) {
        innerBlit(matrixStack.last().pose(),
            drawStartX,
            drawEndX,
            drawStartY,
            drawEndY,
            blitOffset,
            uOffset / textureWidth,
            (uOffset + iconWidth) / textureWidth,
            vOffset / textureHeight,
            (vOffset + iconHeight) / textureHeight,
            color);
    }

    /**
     * Basically like vanilla's version but with floats and a color parameter
     * Only does the inner texture rendering
     *
     * @param matrix4f
     * @param drawStartX the left most position of the drawing rectangle
     * @param drawEndX the right most position of the drawing rectangle
     * @param drawStartY the top most position of the drawing rectangle
     * @param drawEndY the bottom most position of the drawing rectangle
     * @param blitOffset the depth position of the drawing rectangle
     * Note: UV positions are scaled (0.0 - 1.0)
     * @param minU the left most UV mapped position
     * @param maxU the right most UV mapped position
     * @param minV the top most UV mapped position
     * @param maxV the bottom most UV mapped position
     */
    public static void innerBlit(Matrix4f matrix4f, float drawStartX, float drawEndX, float drawStartY, float drawEndY, float blitOffset, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        // bottom left
        bufferbuilder.addVertex(matrix4f, drawStartX, drawEndY, blitOffset).setUv(minU, maxV);
        // bottom right
        bufferbuilder.addVertex(matrix4f, drawEndX, drawEndY, blitOffset).setUv(maxU, maxV);
        // top right
        bufferbuilder.addVertex(matrix4f, drawEndX, drawStartY, blitOffset).setUv(maxU, minV);
        // top left
        bufferbuilder.addVertex(matrix4f, drawStartX, drawStartY, blitOffset).setUv(minU, minV);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    /**
     * Basically like vanilla's version but with floats and a color parameter
     * Only does the inner texture rendering
     *
     * @param matrix4f
     * @param drawStartX the left most position of the drawing rectangle
     * @param drawEndX the right most position of the drawing rectangle
     * @param drawStartY the top most position of the drawing rectangle
     * @param drawEndY the bottom most position of the drawing rectangle
     * @param blitOffset the depth position of the drawing rectangle
     * Note: UV positions are scaled (0.0 - 1.0)
     * @param minU the left most UV mapped position
     * @param maxU the right most UV mapped position
     * @param minV the top most UV mapped position
     * @param maxV the bottom most UV mapped position
     * @param color the Color to apply to the texture
     */
    public static void innerBlit(Matrix4f matrix4f, float drawStartX, float drawEndX, float drawStartY, float drawEndY, float blitOffset, float minU, float maxU, float minV, float maxV, Color color) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        color.setShaderColor();
        // bottom left
        bufferbuilder.addVertex(matrix4f, drawStartX, drawEndY, blitOffset).setUv(minU, maxV).setColor(color.r, color.g, color.b, color.a);
        // bottom right
        bufferbuilder.addVertex(matrix4f, drawEndX, drawEndY, blitOffset).setUv(maxU, maxV).setColor(color.r, color.g, color.b, color.a);
        // top right
        bufferbuilder.addVertex(matrix4f, drawEndX, drawStartY, blitOffset).setUv(maxU, minV).setColor(color.r, color.g, color.b, color.a);
        // top left
        bufferbuilder.addVertex(matrix4f, drawStartX, drawStartY, blitOffset).setUv(minU, minV).setColor(color.r, color.g, color.b, color.a);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }

    float getBlitOffset() {
        return 0;
    }

    // Lightning ---------------------------------------------------------------------------------------
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
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
        } else {
            float mid_x = (x1 + x2)  * 0.5F;
            float mid_y = (y1 + y2) * 0.5F;
            float mid_z = (z1 + z2) * 0.5F;
            mid_x += (float) ((Math.random() - 0.5) * displacement);
            mid_y += (float) ((Math.random() - 0.5) * displacement);
            mid_z += (float) ((Math.random() - 0.5) * displacement);
            drawMPDLightning(matrix4f, x1, y1, z1, mid_x, mid_y, mid_z, color, displacement * 0.5F, detail);
            drawMPDLightning(matrix4f, mid_x, mid_y, mid_z, x2, y2, z2, color, displacement * 0.5F, detail);
        }
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

    public static void drawLightningTextured(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2,
        Color color, TextureAtlasSprite icon, float textureWidth, float textureHeight) {
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
            cx += (float) (Math.random() * tx * jagfactor - 0.1 * tx);
            cy += (float) (Math.random() * ty * jagfactor - 0.1 * ty);
            cz += (float) (Math.random() * tz * jagfactor - 0.1 * tz);
            bx = x1 + cx;
            by = y1 + cy;
            bz = z1 + cz;

            int index = getRandomNumber(0, 50);
            float minU = icon.getU0() + uSize * (index * 0.2F); // 1/50, there are 50 different lightning elements in the texture
            float maxU = minU + uSize * 0.2F;

            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, color, minU, maxU, minV, maxV);
        }
    }

    static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    static void drawLightningBetweenPointsFast(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2,
        Color color, float minU, float maxU, float minV, float maxV) {
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
}
