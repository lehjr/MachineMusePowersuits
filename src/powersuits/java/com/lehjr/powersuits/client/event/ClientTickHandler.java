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

package com.lehjr.powersuits.client.event;

import com.lehjr.numina.client.render.NuminaRenderer;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.string.StringUtils;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import com.lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This handler is called before/after the game processes input events and
 * updates the gui state mainly. *independent of rendering, so don't do rendering here
 * -is also the parent class of KeyBindingHandleryBaseIcon
 *
 * @author MachineMuse
 */
public class ClientTickHandler {
    static final ItemStack food = new ItemStack(Items.COOKED_BEEF);
    PoseStack matrixStack = new PoseStack();
    final double meterTextOffsetY = 6;

//    @SubscribeEvent
//    public void onPreClientTick(TickEvent.ClientTickEvent event) {
//        if (Minecraft.getInstance().player == null) {
//            return;
//        }
//
////        if (event.phase == TickEvent.Phase.START) {
////            System.out.println("fixme");
////
//////            for (ClickableKeybinding kb : KeybindManager.INSTANCE.getKeybindings()) {
//////                kb.doToggleTick();
//////            }
////        }
//    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        int yOffsetString = 18;
        float yOffsetIcon = 16.0F;
        float yBaseIcon;
        int yBaseString;
        if (MPSSettings.useGraphicalMeters()) {
            yBaseIcon = 150.0F;
            yBaseString = 155;
        } else {
            yBaseIcon = 26.0F;
            yBaseString = 32;
        }

        if (event.phase == TickEvent.Phase.END) {
            Player player = minecraft.player;
            if (player != null && Minecraft.renderNames() && minecraft.screen == null) {
                Minecraft mc = minecraft;
                Window screen = mc.getWindow();

                // Misc Overlay Items ---------------------------------------------------------------------------------
                AtomicInteger index = new AtomicInteger(0);

                // Helmet modules with overlay
                player.getItemBySlot(EquipmentSlot.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(h -> {
                            // AutoFeeder
                            ItemStack autoFeeder = h.getOnlineModuleOrEmpty(MPSRegistryNames.AUTO_FEEDER_MODULE);
                            if (!autoFeeder.isEmpty()) {
                                int foodLevel = (int) ((AutoFeederModule) autoFeeder.getItem()).getFoodLevel(autoFeeder);
                                String num = StringUtils.formatNumberShort(foodLevel);
                                StringUtils.drawShadowedString(matrixStack, num, 17, yBaseString + (yOffsetString * index.get()));
                                NuminaRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon * index.get()), food);
                                index.addAndGet(1);
                            }

                            // Clock
                            ItemStack clock = h.getOnlineModuleOrEmpty(Items.CLOCK.getRegistryName());
                            if (!clock.isEmpty()) {
                                String ampm;
                                long time = player.level.getDayTime();
                                long hour = ((time % 24000) / 1000);
                                if (MPSSettings.use24HourClock()) {
                                    if (hour < 19) {
                                        hour += 6;
                                    } else {
                                        hour -= 18;
                                    }
                                    ampm = "h";
                                } else {
                                    if (hour < 6) {
                                        hour += 6;
                                        ampm = " AM";
                                    } else if (hour == 6) {
                                        hour = 12;
                                        ampm = " PM";
                                    } else if (hour > 6 && hour < 18) {
                                        hour -= 6;
                                        ampm = " PM";
                                    } else if (hour == 18) {
                                        hour = 12;
                                        ampm = " AM";
                                    } else {
                                        hour -= 18;
                                        ampm = " AM";
                                    }

                                    StringUtils.drawShadowedString(matrixStack, hour + ampm, 17, yBaseString + (yOffsetString * index.get()));
                                    NuminaRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon * index.get()), clock);

                                    index.addAndGet(1);
                                }
                            }

                            // Compass
                            ItemStack compass = h.getOnlineModuleOrEmpty(Items.COMPASS.getRegistryName());
                            if (!compass.isEmpty()) {
                                NuminaRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon * index.get()), compass);
                                index.addAndGet(1);
                            }
                        });
            }
        }
    }
}
