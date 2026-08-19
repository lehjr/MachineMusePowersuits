package lehjr.powersuits.client.overlay;

import lehjr.numina.client.gui.meter.EnergyMeter;
import lehjr.numina.client.gui.meter.HeatMeter;
import lehjr.numina.client.gui.meter.PlasmaChargeMeter;
import lehjr.numina.client.gui.meter.WaterMeter;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.MathUtils;
import lehjr.numina.common.utils.StringUtils;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import java.util.Objects;

public class MPSMeterOverlay {
    private static HeatMeter heatMeter = null;
    private static HeatMeter energyMeter = null;
    private static WaterMeter waterMeter = null;
    private static PlasmaChargeMeter plasmaMeter = null;
    final static double meterTextOffsetY = 0;

    /**
     *
     * @param player
     * @param poseStack
     * @param deltaTracker unused, left for potential future "features"
     * @param scaledWidth
     * @param scaledHeight
     */
    public static void render(Player player, GuiGraphics poseStack, DeltaTracker deltaTracker, float scaledWidth, float scaledHeight) {
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
        float currWater = 0;
        float maxWater = 0;
        String currWaterStr = "";
        String maxWaterStr = "";

        ItemStack chestPlate = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST);
        IFluidHandlerItem fh = chestPlate.getCapability(Capabilities.FluidHandler.ITEM);
        if(fh != null) {
            for (int i = 0; i < fh.getTanks(); i++) {
                if(fh.isFluidValid(i, new FluidStack(Fluids.WATER, 1000))) {
                    maxWater = maxWater + fh.getTankCapacity(i);
                    if (maxWater > 0) {
                        FluidStack fluidStack = fh.getFluidInTank(i);
                        if(fluidStack.is(Fluids.WATER)) {
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

                int maxDuration = modeChanging.getModularItemStack().getUseDuration(player);
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
                energyMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, (float)(currEnergy / maxEnergy));
                StringUtils.drawRightAlignedShadowedString(poseStack, currEnergyStr, stringX, meterTextOffsetY + top);
                numMeters--;
            }

            if (heatMeter != null) {
                heatMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currHeat, 0, maxHeat) / maxHeat);
                StringUtils.drawRightAlignedShadowedString(poseStack, currHeatStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (waterMeter != null) {
                waterMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currWater, 0, maxWater) / maxWater);
                StringUtils.drawRightAlignedShadowedString(poseStack, currWaterStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (plasmaMeter != null) {
                plasmaMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, currentPlasma / maxPlasma);
                StringUtils.drawRightAlignedShadowedString(poseStack, currPlasmaStr, stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
            }

        } else {
            stringX = left + 30;

            int numReadouts = 0;
            if (maxEnergy > 0) {
                StringUtils.drawRightAlignedShadowedString(poseStack, currEnergyStr + '/' + maxEnergyStr, stringX, top);
                numReadouts += 1;
            }

            StringUtils.drawRightAlignedShadowedString(poseStack, currHeatStr + '/' + maxHeatStr + " C", stringX, top + (numReadouts * 9));
            numReadouts += 1;

            if (maxWater > 0) {
                StringUtils.drawRightAlignedShadowedString(poseStack, currWaterStr + '/' + maxWaterStr + " buckets", stringX, top + (numReadouts * 9));
                numReadouts += 1;
            }

            if (maxPlasma > 0 /* && drawPlasmaMeter */) {
                StringUtils.drawRightAlignedShadowedString(poseStack, currPlasmaStr + '/' + maxPlasmaStr + "%", stringX, top + (numReadouts * 9));
            }
        }
    };
}
