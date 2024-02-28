//package lehjr.powersuits.client.render.item;
//
//import lehjr.powersuits.basemod.MPSConstants;
//import lehjr.powersuits.client.model.item.PowerFistModel2;
//import lehjr.powersuits.network.MPSPackets;
//import lehjr.powersuits.network.packets.CosmeticInfoPacket;
//import lehjr.numina.basemod.NuminaConstants;
//import lehjr.numina.common.capabilities.render.IHandHeldModelSpecNBT;
//import lehjr.numina.common.capabilities.render.ModelSpecNBTCapability;
//import lehjr.numina.common.capabilities.render.modelspec.ModelPartSpec;
//import lehjr.numina.common.capabilities.render.modelspec.ModelRegistry;
//import lehjr.numina.common.capabilities.render.modelspec.ModelSpec;
//import lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
//import lehjr.numina.common.math.Color;
//import lehjr.numina.common.tags.NBTTagAccessor;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.model.ItemTransforms;
//import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.util.math.vector.Transformation;
//
//public class PowerFistRenderer extends ItemStackTileEntityRenderer {
//    PowerFistModel2 powerFist = new PowerFistModel2();
//    boolean isFiring = false;
//
//    @Override
//    public void renderByItem/*render*/(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
//
//
//        VertexConsumer builder = buffer.getBuffer(powerFist.getRenderType(MPSConstants.POWER_FIST_TEXTURE));
//
//        stack.getCapability(ModelSpecNBTCapability.RENDER).ifPresent(specNBTCap -> {
//            if (specNBTCap instanceof IHandHeldModelSpecNBT) {
//                CompoundTag renderSpec = specNBTCap.getRenderTag();
//
//                // Set the tag on the item so this lookup isn't happening on every loop.
//                if (renderSpec == null || renderSpec.isEmpty()) {
//                    renderSpec = specNBTCap.getDefaultRenderTag();
//
//                    // first person transform type insures THIS client's player is the one holding the item rather than this
//                    // client's player seeing another player holding it
//                    if (renderSpec != null && !renderSpec.isEmpty() &&
//                            (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
//                                    (transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND))) {
//                        Player player = Minecraft.getInstance().player;
//                        int slot = -1;
//                        if (player.getHeldItemMainhand().equals(stack)) {
//                            slot = player.inventory.currentItem;
//                        } else {
//                            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
//                                if (player.inventory.getStackInSlot(i).equals(stack)) {
//                                    slot = i;
//                                    break;
//                                }
//                            }
//                        }
//
//                        if (slot != -1) {
//                            specNBTCap.setRenderTag(renderSpec, TagConstants.RENDER);
//                            MPAPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(slot, TagConstants.RENDER, renderSpec));
//                        }
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
//
//                        String partName = nbt.getString("part");
//
//
//
//                        if (partSpec instanceof ModelPartSpec) {
//
//                            // only process this part if it's for the correct hand
//                            if (partSpec.getBinding().getTarget().name().toUpperCase().equals(
//                                    transformType.equals(ItemDisplayContext.FIRST_PERSON_LEFT_HAND) ||
//                                            transformType.equals(ItemDisplayContext.THIRD_PERSON_LEFT_HAND) ?
//                                            "LEFTHAND" : "RIGHTHAND")) {
//
//                                transform = ((ModelSpec) partSpec.spec).getTransform(transformType);
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
//                                if ((!isFiring && (itemState.equals("all") || itemState.equals("normal"))) ||
//                                        (isFiring && (itemState.equals("all") || itemState.equals("firing")))) {
//
//                                    NuminaLogger.logDebug("partname: " + partName);
//                                    matrixStack.push();
//                                    matrixStack.translate(transform.getTranslation().getX(), transform.getTranslation().getY(), transform.getTranslation().getZ());
//                                    matrixStack.rotate(transform.getRightRot());
//                                    matrixStack.scale(transform.getScale().getX(), transform.getScale().getX(), transform.getScale().getZ());
//                                    matrixStack.pop();
//
////
//                                    powerFist.renderPart(partName, matrixStack, builder, combinedLight, combinedOverlay, partColor.r, partColor.g, partColor.b, partColor.a);
//
//
//
//
////                                    builder.addAll(ModelHelper.getColoredQuadsWithGlowAndTransform(((ModelPartSpec) partSpec).getPart().getQuads(state, side, rand, extraData), partColor, transform, glow));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
//
//
//              /*
//        matrixStack.scale(0.5F, 0.5F, 0.5F);
//
//
//
//
//                        <transformationMatrix type="third_person_right_hand" translation="8, 8.01, 9" rotation="-15, 180, 0" scale="0.630, 0.630, 0.630"/>
//                <transformationMatrix type="first_person_right_hand" translation="11.8, 8, 7" rotation="-16, -162, 0" scale="0.5, 0.5, 0.5"/>
//                <transformationMatrix type="ground" translation="0, 5, 0" rotation="0,0,0" scale="0.630, 0.630, 0.630"/>
//
//
//                            <modelTransforms>
//                <transformationMatrix type="third_person_right_hand" translation="8, 8.01, 9" rotation="-15, 180, 0" scale="0.630, 0.630, 0.630"/>
//                <transformationMatrix type="first_person_right_hand" translation="11.8, 8, 7" rotation="-16, -162, 0" scale="0.5, 0.5, 0.5"/>
//                <transformationMatrix type="ground" translation="0, 5, 0" rotation="0,0,0" scale="0.630, 0.630, 0.630"/>
//            </modelTransforms>
//         */
//
//
////        matrixStack.rotate(Vector3f.ZP.rotationDegrees(270F));
////        matrixStack.rotate(Vector3f.XP.rotationDegrees(90F));
////        for (String partName : powerFist.partlMap.keySet()) {
////            powerFist.renderPart(partName, matrixStack, builder, combinedLight, combinedOverlay, Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
////        }
//
//
//    }
//
//    String getPrefixString(ItemDisplayContext transformType) {
//        switch ((transformType)) {
//            case FIRST_PERSON_LEFT_HAND:
//            case THIRD_PERSON_LEFT_HAND: {
//                return "powerfist_left.";
//            }
//
//            default:
//                return "powerfist_right.";
//        }
//    }
//}