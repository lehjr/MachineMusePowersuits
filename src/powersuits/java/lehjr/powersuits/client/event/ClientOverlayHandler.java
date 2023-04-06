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

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.meter.EnergyMeter;
import lehjr.numina.client.gui.meter.HeatMeter;
import lehjr.numina.client.gui.meter.PlasmaChargeMeter;
import lehjr.numina.client.gui.meter.WaterMeter;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public enum ClientOverlayHandler {
    INSTANCE;

    private HeatMeter heatMeter = null;
    private HeatMeter energyMeter = null;
    private WaterMeter waterMeter = null;
    private PlasmaChargeMeter plasmaMeter = null;

    static final ItemStack food = new ItemStack(Items.COOKED_BEEF);
    final double meterTextOffsetY = 0;

    public void render(/*CustomizeGuiOverlayEvent  */ RenderGuiOverlayEvent.Post e) {
        PoseStack matrixStack = e.getPoseStack();

        Minecraft minecraft = Minecraft.getInstance();
        Player player;
        if (minecraft.player == null) {
            return;
        }
        player = minecraft.player;

        int yOffsetString = 18;
        float yOffsetIcon = 16.0F;
        float yBase;
        if (MPSSettings.useGraphicalMeters()) {
            yBase = 150;

        } else {
            yBase = 26.0F;
        }

        if (player != null && (MPSSettings.showMetersWhenPaused() || (Minecraft.renderNames() && minecraft.screen == null))) {
            Window screen = e.getWindow();

            // Misc Overlay Items ---------------------------------------------------------------------------------
            AtomicInteger index = new AtomicInteger(0);

            // Helmet modules with overlay
            player.getItemBySlot(EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)

                    // Looping this way is far more efficient than looping for each module
                    .ifPresent(h -> h.getInstalledModules().forEach(module -> {
                        // AutoFeeder
                        if (ItemUtils.getRegistryName(module).equals(MPSRegistryNames.AUTO_FEEDER_MODULE)) {
                            ItemStack autoFeeder = module;
                            if (autoFeeder.getCapability(NuminaCapabilities.POWER_MODULE).map(pm-> {
                                                                return pm.isModuleOnline();
                            }).orElse(false)) {
                                int foodLevel = (int) ((AutoFeederModule) autoFeeder.getItem()).getFoodLevel(autoFeeder);
                                String num = StringUtils.formatNumberShort(foodLevel);
                                StringUtils.drawShadowedString(matrixStack, num, 17, yBase + (yOffsetString * index.get()));
                                // FIXME
//                                NuminaRenderer.drawItemAt(-1.0, yBase + (yOffsetIcon * index.get()), food);
                                index.addAndGet(1);
                            }
                        }

                        // Clock
                        if (ItemUtils.getRegistryName(module).equals(ItemUtils.getRegistryName(Items.CLOCK))) {
                            ItemStack clock = module;
                            if (clock.getCapability(NuminaCapabilities.POWER_MODULE).map(pm -> pm.isModuleOnline()).orElse(false)) {
                                if (!clock.isEmpty() /*&& clock.getCapability(NuminaCapabilities.POWER_MODULE).map(pm->pm.isModuleOnline()).orElse(false)*/) {
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

                                        StringUtils.drawShadowedString(matrixStack, hour + ampm, 17, yBase + (yOffsetString * index.get()));
                                        // FIXME
//                                        NuminaRenderer.drawItemAt(-1.0, yBase + (yOffsetIcon * index.get()), clock);

                                        index.addAndGet(1);
                                    }
                                }
                            }
                        }

                        // Compass
                        if (ItemUtils.getRegistryName(module).equals(ItemUtils.getRegistryName(Items.COMPASS))) {
                            ItemStack compass = module;
                            if (compass.getCapability(NuminaCapabilities.POWER_MODULE).map(pm -> pm.isModuleOnline()).orElse(false)) {
                                // FIXME
//                                NuminaRenderer.drawItemAt(-1.0, yBase + (yOffsetIcon * index.get()), compass);
                                index.addAndGet(1);
                            }
                        }
                    }));

            // Meters ---------------------------------------------------------------------------------------------
            float sw = (float) ((double) screen.getWidth() / screen.getGuiScale());
            float sh = (float) ((double) screen.getHeight() / screen.getGuiScale());


//            float top = (float) screen.getGuiScaledHeight() / 2.0F - 16F;
//            float left = screen.getGuiScaledWidth() - 34;

            float top = sh / 2.0F - 16F;
            float left = sw - 36;



            // energy
            float maxEnergy = ElectricItemUtils.getMaxPlayerEnergy(player);
            float currEnergy = ElectricItemUtils.getPlayerEnergy(player);
            String currEnergyStr = StringUtils.formatNumberShort(currEnergy) + "FE";
            String maxEnergyStr = StringUtils.formatNumberShort(maxEnergy);

            // heat
            float maxHeat = (float) HeatUtils.getPlayerMaxHeat(player);
            float currHeat = (float) HeatUtils.getPlayerHeat(player);

            String currHeatStr = StringUtils.formatNumberShort(currHeat);
            String maxHeatStr = StringUtils.formatNumberShort(maxHeat);

            // Water
            AtomicReference<Float> currWater = new AtomicReference<Float>(0F);
            AtomicReference<Float> maxWater = new AtomicReference<Float>(0F);
            AtomicReference<String> currWaterStr = new AtomicReference<>("");
            AtomicReference<String> maxWaterStr = new AtomicReference<>("");

            player.getItemBySlot(EquipmentSlot.CHEST).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fh -> {
                for (int i = 0; i < fh.getTanks(); i++) {
                    maxWater.set(maxWater.get() + fh.getTankCapacity(i));
                    if (maxWater.get() > 0) {
                        FluidStack fluidStack = fh.getFluidInTank(i);
                        currWater.set(currWater.get() + fluidStack.getAmount());
                        waterMeter = new WaterMeter(MPSSettings::getWaterMeterConfig);
                        currWaterStr.set(StringUtils.formatNumberShort(currWater.get()));
                        maxWaterStr.set(StringUtils.formatNumberShort(maxWater.get()));
                    }
                }
            });

            // Plasma
            AtomicReference<Float> currentPlasma = new AtomicReference<Float>(0F);
            AtomicReference<Float> maxPlasma = new AtomicReference<Float>(0F);
            if (player.isUsingItem()) {
                player.getItemInHand(player.getUsedItemHand()).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modechanging -> {
                            ItemStack module = modechanging.getActiveModule();
                            int actualCount = 0;

                            int maxDuration = modechanging.getModularItemStack().getUseDuration();
                            if (!module.isEmpty()) {
                                // Plasma Cannon
                                if (ItemUtils.getRegistryName(module).equals(MPSRegistryNames.PLASMA_CANNON_MODULE)) {
                                    actualCount = (maxDuration - player.getUseItemRemainingTicks());
                                    currentPlasma.set(
                                            currentPlasma.get() + (actualCount > 50 ? 50 : actualCount) * 2);
                                    maxPlasma.set(maxPlasma.get() + 100F);

                                    // Ore Scanner or whatever
                                } else {
                                    actualCount = (maxDuration - player.getUseItemRemainingTicks());
                                    currentPlasma.set(
                                            currentPlasma.get() + (actualCount > 40 ? 40 : actualCount) * 2.5F);
                                    maxPlasma.set(maxPlasma.get() + 100F);
                                }
                            }
                        });
            }

            float val = currentPlasma.get();
            String currPlasmaStr = StringUtils.formatNumberShort((int)val) + "%";
            String maxPlasmaStr = StringUtils.formatNumberShort(maxPlasma.get());

            if (MPSSettings.useGraphicalMeters()) {
                int numMeters = 0;

                if (maxEnergy > 0) {
                    numMeters++;
                    if (energyMeter == null) {
                        energyMeter = new EnergyMeter(MPSSettings::getEnergyMeterConfig);
                    }
                } else energyMeter = null;

                if (maxHeat > 0) {
                    numMeters++;
                    if (heatMeter == null) {
                        heatMeter = new HeatMeter(MPSSettings::getHeatMeterConfig);
                    }
                } else heatMeter = null;

                if (maxWater.get() > 0 && waterMeter != null) {
                    numMeters++;
                }

                if (maxPlasma.get() > 0 /* && drawPlasmaMeter */) {
                    numMeters++;
                    if (plasmaMeter == null) {
                        plasmaMeter = new PlasmaChargeMeter(MPSSettings::getPlasmaMeterConfig);
                    }
                } else plasmaMeter = null;

                double stringX = left - 2;
                final int totalMeters = numMeters;
                //"(totalMeters-numMeters) * 8" = 0 for whichever of these is first,
                //but including it won't hurt and this makes it easier to swap them around.

                if (energyMeter != null) {
                    energyMeter.draw(matrixStack, left, top + (totalMeters - numMeters) * 9, currEnergy / maxEnergy);
                    StringUtils.drawRightAlignedShadowedString(matrixStack, currEnergyStr, stringX, meterTextOffsetY + top);
                    numMeters--;
                }

                if (heatMeter != null) {
                    heatMeter.draw(matrixStack, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currHeat, 0, maxHeat) / maxHeat);
                    StringUtils.drawRightAlignedShadowedString(matrixStack, currHeatStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                    numMeters--;
                }

                if (waterMeter != null) {
                    waterMeter.draw(matrixStack, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currWater.get(), 0, maxWater.get()) / maxWater.get());
                    StringUtils.drawRightAlignedShadowedString(matrixStack, currWaterStr.get(), stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                    numMeters--;
                }

                if (plasmaMeter != null) {
                    plasmaMeter.draw(matrixStack, left, top + (totalMeters - numMeters) * 9, currentPlasma.get() / maxPlasma.get());
                    StringUtils.drawRightAlignedShadowedString(matrixStack, currPlasmaStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                }

            } else {
                int numReadouts = 0;
                if (maxEnergy > 0) {
                    StringUtils.drawShadowedString(matrixStack, currEnergyStr + '/' + maxEnergyStr + " \u1D60", 2, 2);
                    numReadouts += 1;
                }

                StringUtils.drawShadowedString(matrixStack, currHeatStr + '/' + maxHeatStr + " C", 2, 2 + (numReadouts * 9));
                numReadouts += 1;

                if (maxWater.get() > 0) {
                    StringUtils.drawShadowedString(matrixStack, currWaterStr.get() + '/' + maxWaterStr.get() + " buckets", 2, 2 + (numReadouts * 9));
                    numReadouts += 1;
                }

                if (maxPlasma.get() > 0 /* && drawPlasmaMeter */) {
                    StringUtils.drawShadowedString(matrixStack, currPlasmaStr + '/' + maxPlasmaStr + "%", 2, 2 + (numReadouts * 9));
                }
            }
        }
    }
}