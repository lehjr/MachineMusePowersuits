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

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableMuseRect;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.control.KeybindManager;
import com.github.lehjr.powersuits.client.gui.clickable.ClickableKeybinding;
import com.github.lehjr.powersuits.client.model.helper.MPSModelHelper;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public enum RenderEventHandler {
    INSTANCE;
    private static boolean ownFly = false;
    private final DrawableMuseRect frame = new DrawableMuseRect(MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY(), MPSSettings.getHudKeybindX() + (float) 16, MPSSettings.getHudKeybindY() +  16, true, Colour.DARK_GREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
//        MuseIcon.registerIcons(event);
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
        if (!event.getPlayer().abilities.isFlying && !event.getPlayer().isOnGround() && this.playerHasFlightOn(event.getPlayer())) {
            event.getPlayer().abilities.isFlying = true;
            RenderEventHandler.ownFly = true;
        }
    }

    private boolean playerHasFlightOn(PlayerEntity player) {
        return

                player.getItemStackFromSlot(EquipmentSlotType.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .map(iModularItem ->
                                (iModularItem instanceof IModularItem) && ((IModularItem) iModularItem).isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)).orElse(false) ||

                        player.getItemStackFromSlot(EquipmentSlotType.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .map(iModularItem ->
                                        (iModularItem instanceof IModularItem) &&
                                                ((IModularItem) iModularItem).isModuleOnline(MPSRegistryNames.JETPACK_MODULE_REGNAME) ||
                                                ((IModularItem) iModularItem).isModuleOnline(MPSRegistryNames.GLIDER_MODULE_REGNAME)).orElse(false) ||

                        player.getItemStackFromSlot(EquipmentSlotType.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .map(iModularItem ->
                                        (iModularItem instanceof IModularItem) && ((IModularItem) iModularItem).isModuleOnline(MPSRegistryNames.JETBOOTS_MODULE_REGNAME)).orElse(false);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getPlayer().abilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        ItemStack helmet = e.getEntity().getItemStackFromSlot(EquipmentSlotType.HEAD);
        helmet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h-> {
                    if (h instanceof IModularItem) {
                        ItemStack binnoculars = ((IModularItem) h).getOnlineModuleOrEmpty(MPSRegistryNames.BINOCULARS_MODULE_REGNAME);
                        if (!binnoculars.isEmpty())
                            e.setNewfov((float) (e.getNewfov() / binnoculars.getCapability(PowerModuleCapability.POWER_MODULE)
                                    .map(m->m.applyPropertyModifiers(MPSConstants.FOV)).orElse(1D)));
                    }
                }
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void drawKeybindToggles(MatrixStack matrixStack) {
        float zLevel = Minecraft.getInstance().currentScreen != null ? Minecraft.getInstance().currentScreen.getBlitOffset() : 0;

        if (MPSSettings.displayHud()) {

            Minecraft minecraft = Minecraft.getInstance();
            ClientPlayerEntity player = minecraft.player;
            frame.setLeft(MPSSettings.getHudKeybindX());
            frame.setTop(MPSSettings.getHudKeybindY());
            frame.setBottom(frame.top() + 16);
            for (ClickableKeybinding kb : KeybindManager.INSTANCE.getKeybindings()) {
                if (kb.displayOnHUD) {
                    float stringwidth = (float) MuseRenderer.getFontRenderer().getStringPropertyWidth(kb.getLabel());
                    frame.setWidth(stringwidth + kb.getBoundModules().size() * 16);
                    frame.draw(matrixStack, zLevel);
                    MuseRenderer.drawCenteredText(matrixStack, kb.getLabel(), (float) frame.left() + 1, (float) frame.top() + 3, (kb.toggleval) ? Colour.RED : Colour.GREEN);

                    double x = frame.left() + stringwidth;
                    for (ClickableModule module : kb.getBoundModules()) {
//                        TextureUtils.pushTexture(TextureUtils.TEXTURE_QUILT);
                        boolean active = false;
                        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                            ItemStack stack = player.getItemStackFromSlot(slot);
                            active = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> {
                                if (iItemHandler instanceof IModularItem) {
                                    return ((IModularItem) iItemHandler).isModuleOnline(module.getModule().getItem().getRegistryName());
                                }
                                return false;
                            }).orElse(false);
                        }

                        MuseRenderer.drawModuleAt(matrixStack, x, frame.top(), module.getModule(), active);
//                        TextureUtils.popTexture();
                        x += 16;
                    }
                    frame.setTop(frame.top() + 16);
                    frame.setBottom(frame.top() + 16);
                }
            }
        }
    }
}