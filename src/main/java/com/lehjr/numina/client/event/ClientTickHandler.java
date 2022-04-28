package com.lehjr.numina.client.event;

import com.lehjr.numina.api.energy.ElectricItemUtils;
import com.lehjr.numina.api.gui.meter.EnergyMeter;
import com.lehjr.numina.api.gui.meter.HeatMeter;
import com.lehjr.numina.api.gui.meter.PlasmaChargeMeter;
import com.lehjr.numina.api.gui.meter.WaterMeter;
import com.lehjr.numina.api.heat.HeatUtils;
import com.lehjr.numina.api.render.NuminaRenderer;
import com.lehjr.numina.api.string.StringUtils;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.concurrent.atomic.AtomicReference;

@OnlyIn(Dist.CLIENT)
public enum ClientTickHandler {
    INSTANCE;

    protected HeatMeter heatMeter = null;
    protected EnergyMeter energyMeter = null;
    protected WaterMeter waterMeter = null;
    protected PlasmaChargeMeter plasmaMeter = null;
    PoseStack poseStack = new PoseStack();
    final double meterTextOffsetY = 6;


    @SubscribeEvent
    public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        Player player = minecraft.player;
        Window screen = minecraft.getWindow();

        // Meters ---------------------------------------------------------------------------------------------
        float top = (float) screen.getGuiScaledHeight() / 2.0F - 16F;
        float left = screen.getGuiScaledWidth() - 34;

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

        player.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fh -> {
            for (int i = 0; i < fh.getTanks(); i++) {
                maxWater.set(maxWater.get() + fh.getTankCapacity(i));
                if (maxWater.get() > 0) {
                    FluidStack fluidStack = fh.getFluidInTank(i);
                    currWater.set(currWater.get() + fluidStack.getAmount());
//                    waterMeter = new WaterMeter();
                    currWaterStr.set(StringUtils.formatNumberShort(currWater.get()));
                    maxWaterStr.set(StringUtils.formatNumberShort(maxWater.get()));
                }
            }
        });

        // Plasma
        AtomicReference<Float> currentPlasma = new AtomicReference<Float>(0F);
        AtomicReference<Float> maxPlasma = new AtomicReference<Float>(0F);

        // TODO: some type of method to designate whether certain modules display a charge meter
//        if (player.isUsingItem()) {
//            player.getItemInHand(player.getUsedItemHand()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                    .filter(IModeChangingItem.class::isInstance)
//                    .map(IModeChangingItem.class::cast)
//                    .ifPresent(modechanging -> {
//                        ItemStack module = modechanging.getActiveModule();
//                        int actualCount = 0;
//
//                        int maxDuration = modechanging.getModularItemStack().getUseDuration();
//                        if (!module.isEmpty()) {
//                            // Plasma Cannon
//                            if (module.getItem().getRegistryName().equals(MPSRegistryNames.PLASMA_CANNON_MODULE_REGNAME)) {
//                                actualCount = (maxDuration - player.getUseItemRemainingTicks());
//                                currentPlasma.set(
//                                        currentPlasma.get() + (actualCount > 50 ? 50 : actualCount) * 2);
//                                maxPlasma.set(maxPlasma.get() + 100F);
//
//                                // Ore Scanner or whatever
//                            } else {
//                                actualCount = (maxDuration - player.getUseItemRemainingTicks());
//                                currentPlasma.set(
//                                        currentPlasma.get() + (actualCount > 40 ? 40 : actualCount) * 2.5F);
//                                maxPlasma.set(maxPlasma.get() + 100F);
//                            }
//                        }
//                    });
//        }

        float val = currentPlasma.get();
        String currPlasmaStr = StringUtils.formatNumberShort((int) val) + "%";
        String maxPlasmaStr = StringUtils.formatNumberShort(maxPlasma.get());
        // fixme
        if (true /*MPSSettings.useGraphicalMeters()*/) {
            int numMeters = 0;

            if (maxEnergy > 0) {
                numMeters++;
                if (energyMeter == null) {
                    energyMeter = new EnergyMeter();
                }
            } else energyMeter = null;

            if (maxHeat > 0) {
                numMeters++;
                if (heatMeter == null) {
                    heatMeter = new HeatMeter();
                }
            } else heatMeter = null;

            if (maxWater.get() > 0 && waterMeter != null) {
                numMeters++;
            }

            if (maxPlasma.get() > 0 /* && drawPlasmaMeter */) {
                numMeters++;
                if (plasmaMeter == null) {
                    plasmaMeter = new PlasmaChargeMeter();
                }
            } else plasmaMeter = null;

            double stringX = left - 2;
            final int totalMeters = numMeters;
            //"(totalMeters-numMeters) * 8" = 0 for whichever of these is first,
            //but including it won't hurt and this makes it easier to swap them around.

            if (energyMeter != null) {
                energyMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, currEnergy / maxEnergy);
                StringUtils.drawRightAlignedShadowedString(poseStack, currEnergyStr, stringX, meterTextOffsetY + top);
                numMeters--;
            }

            if (heatMeter != null) {
                heatMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, Mth.clamp(currHeat, 0F, maxHeat) / maxHeat);
                StringUtils.drawRightAlignedShadowedString(poseStack, currHeatStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (waterMeter != null) {
                waterMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, Mth.clamp(currWater.get(), 0, maxWater.get()) / maxWater.get());
                StringUtils.drawRightAlignedShadowedString(poseStack, currWaterStr.get(), stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (plasmaMeter != null) {
                plasmaMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, currentPlasma.get() / maxPlasma.get());
                StringUtils.drawRightAlignedShadowedString(poseStack, currPlasmaStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
            }

        } else {
            int numReadouts = 0;
            if (maxEnergy > 0) {
                StringUtils.drawShadowedString(poseStack, currEnergyStr + '/' + maxEnergyStr + " \u1D60", 2, 2);
                numReadouts += 1;
            }

            StringUtils.drawShadowedString(poseStack, currHeatStr + '/' + maxHeatStr + " C", 2, 2 + (numReadouts * 9));
            numReadouts += 1;

            if (maxWater.get() > 0) {
                StringUtils.drawShadowedString(poseStack, currWaterStr.get() + '/' + maxWaterStr.get() + " buckets", 2, 2 + (numReadouts * 9));
                numReadouts += 1;
            }

            if (maxPlasma.get() > 0 /* && drawPlasmaMeter */) {
                StringUtils.drawShadowedString(poseStack, currPlasmaStr + '/' + maxPlasmaStr + "%", 2, 2 + (numReadouts * 9));
            }
        }
    }
}