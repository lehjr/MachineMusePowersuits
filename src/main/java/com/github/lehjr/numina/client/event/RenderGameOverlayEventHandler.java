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

package com.github.lehjr.numina.client.event;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:17 PM, 9/6/13
 * <p>
 * Ported to Java by lehjr on 10/25/16.
 */
@OnlyIn(Dist.CLIENT)
public class RenderGameOverlayEventHandler {
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(final RenderGameOverlayEvent.Post e) {
        if (e.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
            drawModeChangeIcons();
        }
    }

    public void drawModeChangeIcons() {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        int i = player.inventory.currentItem;
        ItemStack stack = player.inventory.getCurrentItem();
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler->{
            if (handler instanceof IModeChangingItem) {
                ItemStack module = ((IModeChangingItem) handler).getActiveModule();
                if (!module.isEmpty()) {
                    MainWindow screen = mc.getMainWindow();
                    double currX;
                    double currY;
                    int sw = screen.getScaledWidth();
                    int baroffset = 22;
                    if (!player.abilities.isCreativeMode) {
                        baroffset += 16;

                        int totalArmorValue = player.getTotalArmorValue();
                        baroffset += 8 * (int) Math.ceil((double)totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
                    }
                    baroffset = screen.getScaledHeight() - baroffset;
                    currX = sw / 2.0 - 89.0 + 20.0 * i;
                    currY = baroffset - 18;
                    boolean isActive = module.getCapability(PowerModuleCapability.POWER_MODULE).map(pm->pm instanceof IToggleableModule && ((IToggleableModule) pm).isModuleOnline()).orElse(true);

                    if (isActive) {
                        mc.getItemRenderer().renderItemIntoGUI(module, (int)currX, (int)currY);
                    } else {
                        MuseRenderer.drawModuleAt(new MatrixStack(), currX, currY, module, false);
                    }
                }
            }
        });
    }
}