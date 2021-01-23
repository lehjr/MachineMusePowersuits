package com.github.lehjr.powersuits.client.model.item;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.CosmeticInfoPacket;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.render.IHandHeldModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelPartSpec;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelRegistry;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelSpec;
import com.github.lehjr.numina.util.capabilities.render.modelspec.PartSpecBase;
import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.nbt.NBTTagAccessor;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by lehjr on 12/19/16.
 */
@OnlyIn(Dist.CLIENT)
public class PowerFistModel extends BakedModelWrapper {
    static ItemCameraTransforms.TransformType modelcameraTransformType;
    static ItemStack itemStack;
    static boolean isFiring = false;

    public PowerFistModel(IBakedModel bakedModelIn) {
        super(bakedModelIn);
//        calibration = new ModelTransformCalibration();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return this.getQuads(state, side, rand, EmptyModelData.INSTANCE);
    }

    /**
     * We don't actually have any IModelData being passed here, so we can ignore the parameter.
     *
     * @param state
     * @param side
     * @param rand
     * @param extraData
     * @return
     */
    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if (side != null)
            return ImmutableList.of();

        switch (modelcameraTransformType) {
            case GUI:
            case FIXED:
            case NONE:
                return originalModel.getQuads(state, side, rand, extraData);
        }
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        itemStack.getCapability(ModelSpecNBTCapability.RENDER).ifPresent(specNBTCap -> {
            if (specNBTCap instanceof IHandHeldModelSpecNBT) {
                CompoundNBT renderSpec = specNBTCap.getRenderTag();

                // Set the tag on the item so this lookup isn't happening on every loop.
                if (renderSpec == null || renderSpec.isEmpty()) {
                    renderSpec = specNBTCap.getDefaultRenderTag();

                    // first person transform type insures THIS client's player is the one holding the item rather than this
                    // client's player seeing another player holding it
                    if (renderSpec != null && !renderSpec.isEmpty() &&
                            (modelcameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND ||
                                    (modelcameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND))) {
                        PlayerEntity player = Minecraft.getInstance().player;
                        int slot = -1;
                        if (player.getHeldItemMainhand().equals(itemStack)) {
                            slot = player.inventory.currentItem;
                        } else {
                            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                                if (player.inventory.getStackInSlot(i).equals(itemStack)) {
                                    slot = i;
                                    break;
                                }
                            }
                        }

                        if (slot != -1) {
                            specNBTCap.setRenderTag(renderSpec, NuminaConstants.TAG_RENDER);
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slot, NuminaConstants.TAG_RENDER, renderSpec));
                        }
                    }
                }

                if (renderSpec != null) {
                    int[] colours = renderSpec.getIntArray(NuminaConstants.TAG_COLOURS);
                    Colour partColor;
                    TransformationMatrix transform;

                    for (CompoundNBT nbt : NBTTagAccessor.getValues(renderSpec)) {
                        PartSpecBase partSpec = ModelRegistry.getInstance().getPart(nbt);
                        if (partSpec instanceof ModelPartSpec) {

                            // only process this part if it's for the correct hand
                            if (partSpec.getBinding().getTarget().name().toUpperCase().equals(
                                    modelcameraTransformType.equals(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) ||
                                            modelcameraTransformType.equals(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) ?
                                            "LEFTHAND" : "RIGHTHAND")) {

                                transform = ((ModelSpec) partSpec.spec).getTransform(modelcameraTransformType);
                                String itemState = partSpec.getBinding().getItemState();

                                int ix = partSpec.getColourIndex(nbt);
                                if (ix < colours.length && ix >= 0) {
                                    partColor = new Colour(colours[ix]);
                                } else {
                                    partColor = Colour.WHITE;
                                }
                                boolean glow = ((ModelPartSpec) partSpec).getGlow(nbt);

                                if ((!isFiring && (itemState.equals("all") || itemState.equals("normal"))) ||
                                        (isFiring && (itemState.equals("all") || itemState.equals("firing")))) {
                                    builder.addAll(ModelHelper.getColouredQuadsWithGlowAndTransform(((ModelPartSpec) partSpec).getPart().getQuads(state, side, rand, extraData), partColor, transform, glow));
                                }
                            }
                        }
                    }
                }
            }
        });
        return builder.build();
    }

    /**
     * this is great for single models or those that share the exact same transforms for the different camera transform
     * type. However, when dealing with quads from different models, it's useless.
     */
    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        modelcameraTransformType = cameraTransformType;
        switch (cameraTransformType) {
            case FIRST_PERSON_LEFT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_RIGHT_HAND: {
                return this;
            }
            default:
                return super.handlePerspective(cameraTransformType, mat);
        }
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new PowerFistItemOverrideList();
    }

    /**
     * Overrides are interesting. If you set them up both in the model and in the item's constructor,
     * the model being passed to the IBaked parameter here should change depending on that.
     */
    public class PowerFistItemOverrideList extends ItemOverrideList {
        @Nullable
        @Override
        public IBakedModel getOverrideModel(IBakedModel originalModel, ItemStack itemStackIn, @Nullable ClientWorld world, @Nullable LivingEntity entityIn) {
            itemStack = itemStackIn;
            if (entityIn instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityIn;
                if (player.isHandActive()) {
                    player.getHeldItem(player.getActiveHand()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(modechanging -> {
                        if (!(modechanging instanceof IModeChangingItem)) {
                            return;
                        }
                        ItemStack module = ((IModeChangingItem) modechanging).getActiveModule();
                        int actualCount = 0;

                        int maxDuration = ((IModeChangingItem) modechanging).getModularItemStack().getUseDuration();
                        if (!module.isEmpty()) {
                            actualCount = (maxDuration - player.getItemInUseCount());
                        }
                        isFiring = actualCount > 0;
                    });
                } else {
                    isFiring = false;
                }
            }
            return originalModel;
        }
    }
}