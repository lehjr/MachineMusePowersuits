package lehjr.powersuits.client.overlay;

import lehjr.numina.client.gui.meter.EnergyMeter;
import lehjr.numina.client.gui.meter.HeatMeter;
import lehjr.numina.client.gui.meter.PlasmaChargeMeter;
import lehjr.numina.client.gui.meter.WaterMeter;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.*;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Objects;

public class MPSMeterOverlay {
    private static HeatMeter heatMeter = null;
    private static HeatMeter energyMeter = null;
    private static WaterMeter waterMeter = null;
    private static PlasmaChargeMeter plasmaMeter = null;
    final static double meterTextOffsetY = 0;

    public static void render(Player player, GuiGraphics gfx, float partialTick, float scaledWidth, float scaledHeight) {
        float top = scaledHeight / 2.0F - 16F;
        float left = scaledWidth - 36;
        double stringX = left - 2;

        // energy
        double maxEnergy = ElectricItemUtils.getMaxPlayerEnergy(player);
        double currEnergy = ElectricItemUtils.getPlayerEnergy(player);
        String currEnergyStr = StringUtils.formatNumberShort(currEnergy) + "FE";
        String maxEnergyStr = StringUtils.formatNumberShort(maxEnergy)+ "FE";;

        // heat
        HeatUtils.PlayerHeat playerHeat =HeatUtils.getPlayerHeat(player);
        float maxHeat = (float) playerHeat.maxHeat();
        float currHeat = (float) playerHeat.currentHeat();

        String currHeatStr = StringUtils.formatNumberShort(currHeat);
        String maxHeatStr = StringUtils.formatNumberShort(maxHeat);

        // Water
        // Water
        float currWater = 0;
        float maxWater = 0;
        String currWaterStr = "";
        String maxWaterStr = "";

        ItemStack chestPlate = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST);
        IFluidHandlerItem fh = chestPlate.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
        if(fh != null) {
            for (int i = 0; i < fh.getTanks(); i++) {
                if(fh.isFluidValid(i, new FluidStack(Fluids.WATER, 1000))) {
                    maxWater = maxWater + fh.getTankCapacity(i);
                    if (maxWater > 0) {
                        FluidStack fluidStack = fh.getFluidInTank(i);
                        if (fluidStack.getFluid() == Fluids.WATER){
                            currWater = currWater + fluidStack.getAmount();
                        }
                    }
                }
            }
            currWaterStr = StringUtils.formatNumberShort(currWater);
            maxWaterStr = StringUtils.formatNumberShort(maxWater);
        }

        // Plasma
        float currentPlasma = 0;
        float maxPlasma = 0;
        if (player.isUsingItem()) {
            IModeChangingItem modeChanging = NuminaCapabilities.getModeChangingModularItem(player.getItemInHand(player.getUsedItemHand()));
            if(modeChanging != null){
                ItemStack module = modeChanging.getActiveModule();
                int actualCount = 0;

                int maxDuration = modeChanging.getModularItemStack().getUseDuration();
                if (!module.isEmpty()) {
                    // Plasma Cannon
                    if (Objects.equals(ItemUtils.getRegistryName(module), MPSConstants.PLASMA_CANNON_MODULE)) {
                        actualCount = (maxDuration - player.getUseItemRemainingTicks());
                        currentPlasma = currentPlasma  + (Math.min(actualCount, 50)) * 2;
                        maxPlasma = maxPlasma + 100F;

                        // Ore Scanner or whatever
                    } else {
                        actualCount = (maxDuration - player.getUseItemRemainingTicks());
                        currentPlasma = currentPlasma + (Math.min(actualCount, 40)) * 2.5F;
                        maxPlasma = maxPlasma + 100F;
                    }
                }
            }
        }

        float val = currentPlasma;
        String currPlasmaStr = StringUtils.formatNumberShort((int)val) + "%";
        String maxPlasmaStr = StringUtils.formatNumberShort(maxPlasma);

        if (MPSClientConfig.hud_use_graphical_meters) {
            int numMeters = 0;

//            if (maxEnergy > 0) {
            numMeters++;
            if (energyMeter == null) {
                energyMeter = new EnergyMeter(MPSClientConfig::getEnergyMeterConfig);
            }
//            } else energyMeter = null;

//            if (maxHeat > 0) {
            numMeters++;
            if (heatMeter == null) {
                heatMeter = new HeatMeter(MPSClientConfig::getHeatMeterConfig);
            }
//            } else heatMeter = null;

//            if (maxWater > 0 ) {
            numMeters++;
            if(waterMeter == null) {
                waterMeter = new WaterMeter(MPSClientConfig::getWaterMeterConfig);
            }
//            }

//            if (maxPlasma > 0 /* && drawPlasmaMeter */) {
            numMeters++;
            if (plasmaMeter == null) {
                plasmaMeter = new PlasmaChargeMeter(MPSClientConfig::getPlasmaMeterConfig);
            }
//            } else plasmaMeter = null;


            final int totalMeters = numMeters;
            //"(totalMeters-numMeters) * 9" = 0 for whichever of these is first,
            //but including it won't hurt and this makes it easier to swap them around.

            if (energyMeter != null) {
                energyMeter.draw(gfx, left, top + (totalMeters - numMeters) * 9, (float)(currEnergy / maxEnergy));
                StringUtils.drawRightAlignedShadowedString(gfx, currEnergyStr, stringX, meterTextOffsetY + top);
                numMeters--;
            }

            if (heatMeter != null) {
                heatMeter.draw(gfx, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currHeat, 0, maxHeat) / maxHeat);
                StringUtils.drawRightAlignedShadowedString(gfx, currHeatStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (waterMeter != null) {
                waterMeter.draw(gfx, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currWater, 0, maxWater) / maxWater);
                StringUtils.drawRightAlignedShadowedString(gfx, currWaterStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (plasmaMeter != null) {
                plasmaMeter.draw(gfx, left, top + (totalMeters - numMeters) * 9, currentPlasma / maxPlasma);
                StringUtils.drawRightAlignedShadowedString(gfx, currPlasmaStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
            }

        } else {
            stringX = left + 30;

            int numReadouts = 0;
            if (maxEnergy > 0) {
                StringUtils.drawRightAlignedShadowedString(gfx, currEnergyStr + '/' + maxEnergyStr, stringX, top);
                numReadouts += 1;
            }

            StringUtils.drawRightAlignedShadowedString(gfx, currHeatStr + '/' + maxHeatStr + " C", stringX, top + (numReadouts * 9));
            numReadouts += 1;

            if (maxWater > 0) {
                StringUtils.drawRightAlignedShadowedString(gfx, currWaterStr + '/' + maxWaterStr + " buckets", stringX, top + (numReadouts * 9));
                numReadouts += 1;
            }

            if (maxPlasma > 0 /* && drawPlasmaMeter */) {
                StringUtils.drawRightAlignedShadowedString(gfx, currPlasmaStr + '/' + maxPlasmaStr + "%", stringX, top + (numReadouts * 9));
            }
        }
    };
}