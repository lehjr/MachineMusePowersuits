///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.powersuits.client.model.item;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Transformation;
//import lehjr.numina.client.model.helper.ModelHelper;
//import lehjr.numina.common.capabilities.NuminaCapabilities;
//import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
//import lehjr.numina.common.capabilities.render.IHandHeldModelSpecNBT;
//import lehjr.numina.common.capabilities.render.modelspec.ModelPartSpec;
//import lehjr.numina.common.capabilities.render.modelspec.ModelRegistry;
//import lehjr.numina.common.capabilities.render.modelspec.ModelSpec;
//import lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
//import lehjr.numina.common.constants.TagConstants;
//import lehjr.numina.common.math.Color;
//import lehjr.numina.common.network.NuminaPackets;
//import lehjr.numina.common.network.packets.CosmeticInfoPacket;
//import lehjr.numina.common.tags.NBTTagAccessor;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.block.model.ItemOverrides;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.resources.model.BakedModel;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.model.BakedModelWrapper;
//import net.minecraftforge.client.model.data.ModelData;
//import net.minecraftforge.common.capabilities.ForgeCapabilities;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//
///**
// * Created by lehjr on 12/19/16.
// *
// * TODO: replace with original model
// */
//@OnlyIn(Dist.CLIENT)
//public class PowerFistModel extends BakedModelWrapper {
//    static ItemDisplayContext modelcameraTransformType;
//    static ItemStack itemStack;
//    static boolean isFiring = false;
//    Player player;
//
//    public PowerFistModel(BakedModel bakedModelIn) {
//        super(bakedModelIn);
////        calibration = new ModelTransformCalibration();
//    }
//
//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
//        return super.getQuads(state, side, rand);
//    }
//
//    public PowerFistModel getModel() {
//        return this;
//    }
//
//
//    /**
//     * We don't actually have any IModelData being passed here, so we can ignore the parameter.
//     *
//     */
//    @Override
//    public @Nonnull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, @Nullable RenderType renderType) {
//        if (side != null)
//            return ImmutableList.of();
//        switch (modelcameraTransformType) {
//            case GUI:
//            case FIXED:
//            case NONE:
//                return originalModel.getQuads(state, side, rand);//, extraData);
//        }
//        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
//
//        itemStack.getCapability(NuminaCapabilities.RENDER).ifPresent(specNBTCap -> {
//            if (specNBTCap instanceof IHandHeldModelSpecNBT) {
//                CompoundTag renderSpec = specNBTCap.getRenderTag();
//
//                // Set the tag on the item so this lookup isn't happening on every loop.
//                // Like the armor, empty or null tag signifies the models haven't been set up yet.
//                if (renderSpec == null || renderSpec.isEmpty()) {
//                    renderSpec = specNBTCap.getDefaultRenderTag();
//
//                    // first person transform type insures THIS client's player is the one holding the item rather than this
//                    // client's player seeing another player holding it
//                    if (renderSpec != null && !renderSpec.isEmpty() &&
//                            (modelcameraTransformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
//                                    (modelcameraTransformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND))) {
//                        Player player = Minecraft.getInstance().player;
//                        EquipmentSlot slotType = EquipmentSlot.OFFHAND;
//                        if (player.getMainHandItem().equals(itemStack)) {
//                            slotType = EquipmentSlot.MAINHAND;
//                        }
//                        specNBTCap.setRenderTag(renderSpec, TagConstants.RENDER);
//                        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slotType, TagConstants.RENDER, renderSpec));
//                    }
//                }
//
//                if (renderSpec != null) {
//                    int[] colors = renderSpec.getIntArray(TagConstants.COLORS);
//                    Color partColor;
//                    Transformation transform;
//
//                    for (CompoundTag nbt : NBTTagAccessor.getValues(renderSpec)) {
//                        PartSpecBase partSpec = ModelRegistry.getInstance().getPart(nbt);
//                        if (partSpec instanceof ModelPartSpec) {
//
//                            // only process this part if it's for the correct hand
//                            if (partSpec.getBinding().getTarget().name().toUpperCase().equals(
//                                    modelcameraTransformType.equals(ItemDisplayContext.FIRST_PERSON_LEFT_HAND) ||
//                                            modelcameraTransformType.equals(ItemDisplayContext.THIRD_PERSON_LEFT_HAND) ?
//                                            "LEFTHAND" : "RIGHTHAND")) {
//
//                                transform = ((ModelSpec) partSpec.spec).getTransform(modelcameraTransformType);
//                                String itemState = partSpec.getBinding().getItemState();
//
//                                int ix = partSpec.getColorIndex(nbt);
//                                if (ix < colors.length && ix >= 0) {
//                                    partColor = new Color(colors[ix]);
//                                } else {
//                                    partColor = Color.WHITE;
//                                }
//                                boolean glow = ((ModelPartSpec) partSpec).getGlow(nbt);
//
//                                if ((!isFiring && (itemState.equals("all") || itemState.equals("normal"))) || (isFiring && (itemState.equals("all") || itemState.equals("firing")))) {
//                                    // FIXME
//                                    builder.addAll(ModelHelper.getColoredQuadsWithGlowAndTransform(((ModelPartSpec) partSpec).getPart().getQuads(state, side, rand), /*extraData),*/ partColor, transform, glow));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        return builder.build();
//    }
//
//    @Override
//    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
//        modelcameraTransformType = cameraTransformType;
//
//        // TODO? Probably not... switching to other model
////        switch (cameraTransformType) {
////            case FIRST_PERSON_LEFT_HAND:
////            case THIRD_PERSON_LEFT_HAND:
////            case FIRST_PERSON_RIGHT_HAND:
////            case THIRD_PERSON_RIGHT_HAND: {
////                return this;
////            }
////            default:
////                return super.handlePerspective(cameraTransformType, mat);
////        }
//        return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }
//
//    @Override
//    public boolean useAmbientOcclusion() {
//        return true;
//    }
//
//    @Override
//    public boolean isCustomRenderer() {
//        return false;
//    }
//
//    @Override
//    public ItemOverrides getOverrides() {
////        NuminaLogger.logDebug("fetching overrides");
//
//        return new PowerFistItemOverrides();
//    }
//
//    /**
//     * Overrides are interesting. If you set them up both in the model and in the item's constructor,
//     * the model being passed to the IBaked parameter here should change depending on that.
//     */
//    public class PowerFistItemOverrides extends ItemOverrides {
//        @Nullable
//        @Override
//        public BakedModel resolve(BakedModel model, ItemStack itemStackIn, @Nullable ClientLevel level, @Nullable LivingEntity entityIn, int seed) {
//            itemStack = itemStackIn;
//            if (entityIn instanceof Player) {
//                Player player = (Player) entityIn;
//                if (player.isUsingItem()) {
//                    player.getItemInHand(player.getUsedItemHand()).getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(modechanging -> {
//                        if (!(modechanging instanceof IModeChangingItem)) {
//                            return;
//                        }
//                        ItemStack module = ((IModeChangingItem) modechanging).getActiveModule();
//                        int actualCount = 0;
//
//                        int maxDuration = ((IModeChangingItem) modechanging).getModularItemStack().getUseDuration();
//                        if (!module.isEmpty()) {
//                            actualCount = (maxDuration - player.getUseItemRemainingTicks());
//                        }
//                        isFiring = actualCount > 0;
//                    });
//                } else {
//                    isFiring = false;
//                }
//            }
//            return getModel();
//        }
//    }
//
//
//}