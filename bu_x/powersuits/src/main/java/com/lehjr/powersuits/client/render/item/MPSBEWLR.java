package com.lehjr.powersuits.client.render.item;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.tags.NBTTagAccessor;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.client.model.block.TinkerTableModel;
import com.lehjr.powersuits.client.model.item.PowerFistModel2;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.electric.tool.PowerFist;
import com.lehjr.powersuits.common.registration.MPSItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class MPSBEWLR extends BlockEntityWithoutLevelRenderer {
    TinkerTableModel tinkerTableModel;
    PowerFistModel2 powerFistModelRight;
    PowerFistModel2 powerFistModelLeft;
    int WHITE = Color.WHITE.getARGBInt();

    public static final ResourceLocation powerFistIcon = ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "textures/item/handitem.png");
//    IconModel icon = new IconModel();

    public MPSBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
        tinkerTableModel = new TinkerTableModel();
        powerFistModelRight = new PowerFistModel2(true);
        powerFistModelLeft = new PowerFistModel2(false);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = itemStack.getItem();

        /** Important: the render types used here are not the same for each transform due to issues each one has in different perspectives (rendering issues with the screens) */
        if (item.equals(MPSItems.TINKER_TABLE_ITEM.get())) {
            switch(transformType) {
                case FIRST_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case FIRST_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case GUI -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case HEAD -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case FIXED -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case GROUND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case THIRD_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
                case THIRD_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, WHITE);
            }
        }

        if (item instanceof PowerFist) {
            IModelSpec specNBTCap = itemStack.getCapability(NuminaCapabilities.RENDER);
            if(specNBTCap != null) {
                float firingPercent = 0F;
                if (firingData != null && itemStack.matches(itemStack, firingData.itemInHand)
                        /*ItemStack.isSameItemSameTags(itemStack, firingData.itemInHand()) */&& firingData.player().isUsingItem()) {
                    LocalPlayer player = firingData.player();
                    IModeChangingItem modeChanging = NuminaCapabilities.getModeChangingModularItem(firingData.itemInHand);
                    if(modeChanging != null) {
                        ItemStack module = modeChanging.getActiveModule();
                        int actualCount = 0;
                        float maxPlasma = 0.01F;
                        float currentPlasma = 0F;

                        int maxDuration = modeChanging.getModularItemStack().getUseDuration(player);
                        if (!module.isEmpty()) {
                            actualCount = (maxDuration - player.getUseItemRemainingTicks());

                            // Plasma Cannon
                            if (ItemUtils.getRegistryName(module).equals(MPSConstants.PLASMA_CANNON_MODULE)) {
                                currentPlasma = (actualCount > 50F ? 50F : actualCount) * 2F;

                                // Ore Scanner or whatever
                            } else {
                                currentPlasma = (actualCount > 40F ? 40F : actualCount) * 2.5F;
                            }
                        }
                        if (currentPlasma > 0) {
                            firingPercent = currentPlasma * maxPlasma;
                        }

                    }
                }

                CompoundTag renderTag =  specNBTCap.getRenderTag();
                PowerFistModel2 modelToRender;
                if (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
                    modelToRender = powerFistModelLeft;
                } else {
                    modelToRender = powerFistModelRight;
                }

                if (firingPercent > 0) {
                    modelToRender.setFiringPose(firingPercent);
                } else {
                    modelToRender.setNeutralPose();
                }
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentCull(PowerFistModel2.TEXTURE));

                if (renderTag != null && !renderTag.isEmpty()) {
                    int[] colors = renderTag.getIntArray(NuminaConstants.COLORS);

                    if (colors.length == 0) {
                        colors = new int[]{Color.WHITE.getARGBInt()};
                    }
                    for (CompoundTag nbt : NBTTagAccessor.getValues(renderTag)) {
                        modelToRender.renderPart(nbt, colors, poseStack, consumer, packedLight, packedOverlay);
                    }
                    // default render strategy
                } else {
                    modelToRender.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, WHITE);
                }
            }
        }
    }

    FiringData firingData = null;

    public FiringData getFiringData() {
        return firingData;
    }

    public void setFiringData(FiringData firingData) {
        this.firingData = firingData;
    }

    public record FiringData(LocalPlayer player, HumanoidArm arm, ItemStack itemInHand) {
        public FiringData {
            Objects.requireNonNull(arm);
            Objects.requireNonNull(itemInHand);
            Objects.requireNonNull(player);
        }
    }
}
