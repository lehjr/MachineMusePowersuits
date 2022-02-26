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

package com.github.lehjr.powersuits.client.event;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.render.highlight.HighLightCapability;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.control.KeybindManager;
import com.github.lehjr.powersuits.client.control.MPSKeyBinding;
import com.github.lehjr.powersuits.client.gui.clickable.ClickableKeybinding;
import com.github.lehjr.powersuits.client.model.helper.MPSModelHelper;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RenderEventHandler {
    INSTANCE;
    private static boolean ownFly = false;
    private final DrawableRelativeRect frame = new DrawableRelativeRect(MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16, MPSSettings.getHudKeybindY() +  16, true, Colour.DARK_GREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));


    /**
     * Just for a couple modules that can break multiple blocks at once
     * @param event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderBlockHighlight(DrawHighlightEvent event) {
        if (event.getTarget().getType() != RayTraceResult.Type.BLOCK || !(event.getInfo().getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = ((PlayerEntity) event.getInfo().getEntity());

        player.getMainHandItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
                    iModeChangingItem.getActiveModule().getCapability(HighLightCapability.HIGHLIGHT).ifPresent(iHighlight -> {
                        BlockRayTraceResult result = (BlockRayTraceResult) event.getTarget();
                        NonNullList<BlockPos> blocks = iHighlight.getBlockPositions(result);

                        if(blocks.isEmpty()) {
                            return;
                        }

                        MatrixStack matrixStack = event.getMatrix();
                        IRenderTypeBuffer buffer = event.getBuffers();
                        IVertexBuilder lineBuilder = buffer.getBuffer(RenderType.LINES);

                        double partialTicks = event.getPartialTicks();
                        double x = player.xOld + (player.getX() - player.xOld) * partialTicks;
                        double y = player.yOld + player.getEyeHeight() + (player.getY() - player.yOld) * partialTicks;
                        double z = player.zOld + (player.getZ() - player.zOld) * partialTicks;

                        matrixStack.pushPose();
                        blocks.forEach(blockPos -> {
                            AxisAlignedBB aabb = new AxisAlignedBB(blockPos).move(-x, -y, -z);

                            WorldRenderer.renderLineBox(matrixStack, lineBuilder, aabb, blockPos.equals(result.getBlockPos()) ? 1 : 0 , 0, 0, 0.4F);
                        });
                        matrixStack.popPose();
                        event.setCanceled(true);
                    });
                });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        System.out.println("stitching called here: " + event);
        MPSModelHelper.loadArmorModels(event, null);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
        RenderGameOverlayEvent.ElementType elementType = e.getType();
        if (RenderGameOverlayEvent.ElementType.HOTBAR.equals(elementType)) {
            this.drawKeybindToggles(e.getMatrixStack());
        }
    }

//    @OnlyIn(Dist.CLIENT) // was this supposed to do something?!
//    @SubscribeEvent
//    public void renderLast(RenderWorldLastEvent event) {
//        Minecraft minecraft = Minecraft.getInstance();
//        MainWindow screen = minecraft.getMainWindow();
//    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getPlayer().abilities.flying && !event.getPlayer().isOnGround() && this.playerHasFlightOn(event.getPlayer())) {
            event.getPlayer().abilities.flying = true;
            RenderEventHandler.ownFly = true;
        }
    }

    private boolean playerHasFlightOn(PlayerEntity player) {
        return
                player.getItemBySlot(EquipmentSlotType.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(iModularItem ->
                                iModularItem.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlotType.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETPACK_MODULE_REGNAME) ||
                                                iModularItem.isModuleOnline(MPSRegistryNames.GLIDER_MODULE_REGNAME)).orElse(false) ||

                        player.getItemBySlot(EquipmentSlotType.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .map(iModularItem ->
                                        iModularItem.isModuleOnline(MPSRegistryNames.JETBOOTS_MODULE_REGNAME)).orElse(false);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getPlayer().abilities.flying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        e.getEntity().getItemBySlot(EquipmentSlotType.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(h-> {
                            if (h instanceof IModularItem) {
                                ItemStack binnoculars = h.getOnlineModuleOrEmpty(MPSRegistryNames.BINOCULARS_MODULE_REGNAME);
                                if (!binnoculars.isEmpty())
                                    e.setNewfov((float) (e.getNewfov() / binnoculars.getCapability(PowerModuleCapability.POWER_MODULE)
                                            .map(m->m.applyPropertyModifiers(MPSConstants.FOV)).orElse(1D)));
                            }
                        }
                );
    }

    final List<KBDisplay> kbDisplayList = new ArrayList<>();
    public void makeKBDisplayList() {
        kbDisplayList.clear();
        KeybindManager.INSTANCE.getMPSKeyBinds().stream().filter(kb->!kb.isUnbound()).map(kb-> kb.getKey()).distinct().collect(Collectors.toList());




    }




    @OnlyIn(Dist.CLIENT)
    public void drawKeybindToggles(MatrixStack matrixStack) {
        if (MPSSettings.displayHud()) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPlayerEntity player = minecraft.player;
            frame.setLeft(MPSSettings.getHudKeybindX());
            frame.setTop(MPSSettings.getHudKeybindY());
            frame.setBottom(frame.top() + 16);
            InputMappings.Input id = null;

            List<InputMappings.Input> test =
            KeybindManager.INSTANCE.getMPSKeyBinds().stream().filter(kb->!kb.isUnbound()).map(kb-> kb.getKey()).distinct().collect(Collectors.toList());

            System.out.println("found kb to display: " + test.size());


            /**
             * TODO: create a holder class to put the keybinds in
             *
             */


//System.out.println("fixme");
//            for (ClickableKeybinding kb : KeybindManager.INSTANCE.getKeybindings()) {
//                if (kb.displayOnHUD) {
//                    float stringwidth = (float) MuseRenderer.getFontRenderer().width(kb.getLabel());
//                    frame.setWidth(stringwidth + 8 + kb.getBoundModules().size() * 18);
//                    frame.render(matrixStack, 0, 0, 0); // FIXME
//
//                    matrixStack.pushPose();
//                    matrixStack.translate(0,0,100);
//                    MuseRenderer.drawLeftAlignedText(matrixStack, kb.getLabel(), (float) frame.left() + 4, (float) frame.top() + 9, (kb.toggleval) ? Colour.RED : Colour.GREEN);
//                    matrixStack.popPose();
//
//                    double x = frame.left() + stringwidth + 8;
//                    for (ClickableModule module : kb.getBoundModules()) {
////                        TextureUtils.pushTexture(TextureUtils.TEXTURE_QUILT);
//                        boolean active = false;
//                        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
//                            ItemStack stack = player.getItemBySlot(slot);
//                            active = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                                    .filter(IModularItem.class::isInstance)
//                                    .map(IModularItem.class::cast)
//                                    .map(iItemHandler -> {
//                                        if (iItemHandler instanceof IModeChangingItem) {
//                                            return ((IModeChangingItem) iItemHandler).isModuleActiveAndOnline(module.getRegName());
//                                        }
//                                        return iItemHandler.isModuleOnline(module.getRegName());
//                                    }).orElse(false);
//                            // stop at the first active instance
//                            if(active) {
//                                break;
//                            }
//                        }
//                        MuseRenderer.drawModuleAt(matrixStack, x, frame.top(), module.getModule(), active);
////                        TextureUtils.popTexture();
//                        x += 16;
//                    }
//                    frame.setTop(frame.top() + 16);
//                    frame.setBottom(frame.top() + 16);
//                }
//            }
        }
    }

    class KBDisplay extends DrawableRelativeRect {
        public List<MPSKeyBinding> boundKeybinds = new ArrayList<>();
        public KBDisplay(double left, double top, double right, double bottom) {
            super(left, top, right, bottom, true, Colour.DARK_GREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));
        }

        public ITextComponent getLabel() {
            return boundKeybinds.stream().findFirst().map(kb->kb.getKey().getDisplayName()).orElse(new StringTextComponent("NONE"));
        }

        List<MPSKeyBinding> getKeyBindingsToDisplay () {
            return boundKeybinds.stream().filter(kb->kb.showOnHud).collect(Collectors.toList());
        }




        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {

//            for (ClickableKeybinding kb : KeybindManager.INSTANCE.getKeybindings()) {
//                if (kb.displayOnHUD) {
//                    float stringwidth = (float) MuseRenderer.getFontRenderer().width(kb.getLabel());
//                    frame.setWidth(stringwidth + 8 + kb.getBoundModules().size() * 18);
//                    frame.render(matrixStack, 0, 0, 0); // FIXME
//
//                    matrixStack.pushPose();
//                    matrixStack.translate(0,0,100);
//                    MuseRenderer.drawLeftAlignedText(matrixStack, kb.getLabel(), (float) frame.left() + 4, (float) frame.top() + 9, (kb.toggleval) ? Colour.RED : Colour.GREEN);
//                    matrixStack.popPose();
//
//                    double x = frame.left() + stringwidth + 8;
//                    for (ClickableModule module : kb.getBoundModules()) {
////                        TextureUtils.pushTexture(TextureUtils.TEXTURE_QUILT);
//                        boolean active = false;
//                        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
//                            ItemStack stack = player.getItemBySlot(slot);.map(kb->kb.getKey().getDisplayName()).orElse(new StringTextComponent
//                            active = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                                    .filter(IModularItem.class::isInstance)
//                                    .map(IModularItem.class::cast)
//                                    .map(iItemHandler -> {
//                                        if (iItemHandler instanceof IModeChangingItem) {
//                                            return ((IModeChangingItem) iItemHandler).isModuleActiveAndOnline(module.getRegName());
//                                        }
//                                        return iItemHandler.isModuleOnline(module.getRegName());
//                                    }).orElse(false);
//                            // stop at the first active instance
//                            if(active) {
//                                break;
//                            }
//                        }
//                        MuseRenderer.drawModuleAt(matrixStack, x, frame.top(), module.getModule(), active);
////                        TextureUtils.popTexture();
//                        x += 16;
//                    }
//                    frame.setTop(frame.top() + 16);
//                    frame.setBottom(frame.top() + 16);
//                }
//            }

            super.render(matrixStack, mouseX, mouseY, frameTime);
        }
    }


}