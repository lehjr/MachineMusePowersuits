/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.overlay.ClientOverlayHandler;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public enum RenderEventHandler {
    INSTANCE;









    /**
     * Just for a couple modules that can break multiple blocks at once
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderBlockHighlight(RenderHighlightEvent.Block event) {
        Color.WHITE.setShaderColor();
        Level level = Minecraft.getInstance().level;

        if (event.getTarget().getType() != HitResult.Type.BLOCK || !(event.getCamera().getEntity() instanceof Player player) || level == null) {
            return;
        }

        BlockHitResult result = event.getTarget();
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        ItemStack heldItem = player.getMainHandItem();
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(heldItem);
        if (mci != null) {
            ItemStack module = mci.getActiveModule();
            IPowerModule pm = mci.getModuleCapability(module);

            if (pm instanceof IHighlight highlight && pm.isModuleOnline()) {
                playerEnergy = playerEnergy - pm.getEnergyUsage();
                NonNullList<IBlockBreakingModule> modules = NonNullList.create();
                for (int i = 0; i < mci.getSlots(); i++) {
                    IPowerModule pm1 = mci.getModuleCapability(mci.getStackInSlot(i));
                    if (pm1 instanceof IBlockBreakingModule bbm && pm1 != pm) {
                        modules.add(bbm);
                    }
                }

                HashMap<IHighlight.BlockPostionData, Integer> blockPositions = highlight
                        .getBlockPositions(
                                heldItem,
                                result,
                                player,
                                level,
                                modules,
                                playerEnergy
                        );

                if (blockPositions.isEmpty()) {
                    return;
                }

                PoseStack matrixStack = event.getPoseStack();
                MultiBufferSource buffer = event.getMultiBufferSource();
                VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);

                // FIX 1: Extract the true camera position vector.
                // This accurately shifts the matrix origin regardless of first or third-person views.
                Camera camera = event.getCamera();
                Vec3 cameraPos = camera.getPosition();
                double camX = cameraPos.x();
                double camY = cameraPos.y();
                double camZ = cameraPos.z();

                matrixStack.pushPose();

                blockPositions.forEach((blockPostionData, miningLevel) -> {
                    BlockPos blockPos = blockPostionData.pos();

                    // FIX 2: Move the bounding box relative to the true camera position
                    AABB aabb = new AABB(blockPos).move(-camX, -camY, -camZ);

                    float r = 0F;
                    float g = 0F;
                    float b = 0;
                    float a = 0.2F;

                    // Render the targeted block at full brightness to distinguish it from others
                    if (blockPos.compareTo(result.getBlockPos()) == 0) {
                        a = 1;
                    }

                    if (blockPostionData.canHarvest()) {
                        if (miningLevel == 1) {
                            g = 0.8F;
                        } else {
                            g = 1F;
                        }
                    } else {
                        r = 1;
                    }

                    LevelRenderer.renderLineBox(matrixStack, lineBuilder, aabb, r, g, b, a);
                });
                matrixStack.popPose();
                event.setCanceled(true);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGuiOverlayEvent.Pre /*CustomizeGuiOverlayEvent*/ e) {
//        if (Re.getType() == RenderGameOverlayEvent.ElementType.LAYER) { // opaque rendering, completely ignores alpha setting
        if (e.getOverlay().id().equals(VanillaGuiOverlay.TITLE_TEXT.id())) { // this one allows translucent rendering
            this.drawKeybindToggles(e.getGuiGraphics());
            ClientOverlayHandler.INSTANCE.render(e);
        }
    }



    private boolean playerHasFlightOn(Player player) {
        return
                ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem ->
                                iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false) ||

                        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE) ||
                                                iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE)).orElse(false) ||

                        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETBOOTS_MODULE)).orElse(false);
    }





    @OnlyIn(Dist.CLIENT)
    public void drawKeybindToggles(GuiGraphics gfx) {
//        if (MPSSettings.displayHud() && isModularItemEquuiiped()) {
//            Minecraft minecraft = Minecraft.getInstance();
//            AtomicDouble top = new AtomicDouble(MPSSettings.getHudKeybindY());
//            kbDisplayList.forEach(kbDisplay -> {
//                if (!kbDisplay.boundKeybinds.isEmpty()) {
//                    kbDisplay.setLeft(MPSSettings.getHudKeybindX());
//                    kbDisplay.setTop(top.get());
//                    kbDisplay.setBottom(top.get() + 16);
//                    kbDisplay.render(matrixStack, 0, 0, minecraft.getFrameTime());
//                    top.getAndAdd(16);
//                }
//            });
//        }
    }


}