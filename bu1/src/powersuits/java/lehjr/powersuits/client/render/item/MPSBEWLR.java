package lehjr.powersuits.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.tags.NBTTagAccessor;
import com.lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.model.block.TinkerTableModel;
import lehjr.powersuits.client.model.item.PowerFistModel2;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.electric.tool.PowerFist;
import lehjr.powersuits.common.registration.MPSItems;
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
import java.util.concurrent.atomic.AtomicReference;

public class MPSBEWLR extends BlockEntityWithoutLevelRenderer {
    TinkerTableModel tinkerTableModel;
    PowerFistModel2 powerFistModelRight;
    PowerFistModel2 powerFistModelLeft;
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
                case FIRST_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIRST_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GUI -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case HEAD -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIXED -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GROUND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
            }
        }

        if (item instanceof PowerFist) {
            AtomicReference<Float> firingPercent = new AtomicReference<>(0F);
            NuminaCapabilities.getRenderCapability(itemStack).ifPresent(specNBTCap -> {

                if (firingData != null && itemStack.matches(itemStack, firingData.itemInHand)
                        /*ItemStack.isSameItemSameTags(itemStack, firingData.itemInHand()) */&& firingData.player().isUsingItem()) {
                    LocalPlayer player = firingData.player();
                    firingPercent.set(NuminaCapabilities.getModeChangingModularItemCapability(firingData.itemInHand())
                            .map(IModeChangingItem.class::cast)
                            .map(modechanging -> {
                                ItemStack module = modechanging.getActiveModule();
                                int actualCount = 0;
                                float maxPlasma = 0.01F;
                                float currentPlasma = 0F;

                                int maxDuration = modechanging.getModularItemStack().getUseDuration();
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
                                    return currentPlasma * maxPlasma;
                                }
                                return 0F;
                            }).orElse(0F));
                }

                CompoundTag renderTag =  specNBTCap.getRenderTag();
                PowerFistModel2 modelToRender;
                if (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
                    modelToRender = powerFistModelLeft;
                } else {
                    modelToRender = powerFistModelRight;
                }

                if (firingPercent.get() > 0) {
                    modelToRender.setFiringPose(firingPercent.get());
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
                    modelToRender.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1, 1, 1, 1);
                }
            });
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
