package lehjr.powersuits.client.gui.overlay;

import lehjr.numina.client.gui.meter.EnergyMeter;
import lehjr.numina.client.gui.meter.HeatMeter;
import lehjr.numina.client.gui.meter.PlasmaChargeMeter;
import lehjr.numina.client.gui.meter.WaterMeter;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.util.concurrent.atomic.AtomicReference;

public class MPSMeterOverlay {
    private static HeatMeter heatMeter = null;
    private static HeatMeter energyMeter = null;
    private static WaterMeter waterMeter = null;
    private static PlasmaChargeMeter plasmaMeter = null;
    final static double meterTextOffsetY = 0;

    public static void render(Player player, GuiGraphics poseStack, float partialTick, float scaledWidth, float scaledHeight) {
        float top = scaledHeight / 2.0F - 16F;
        float left = scaledWidth - 36;
        double stringX = left - 2;

        // energy
        double maxEnergy = ElectricItemUtils.getMaxPlayerEnergy(player);
        double currEnergy = ElectricItemUtils.getPlayerEnergy(player);
        String currEnergyStr = StringUtils.formatNumberShort(currEnergy) + "FE";
        String maxEnergyStr = StringUtils.formatNumberShort(maxEnergy)+ "FE";;

        // heat
        float maxHeat = (float) HeatUtils.getPlayerMaxHeat(player);
        float currHeat = (float) HeatUtils.getPlayerHeat(player);

        String currHeatStr = StringUtils.formatNumberShort(currHeat);
        String maxHeatStr = StringUtils.formatNumberShort(maxHeat);

        // Water
        AtomicReference<Float> currWater = new AtomicReference<>(0F);
        AtomicReference<Float> maxWater = new AtomicReference<>(0F);
        AtomicReference<String> currWaterStr = new AtomicReference<>("");
        AtomicReference<String> maxWaterStr = new AtomicReference<>("");

        ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fh -> {
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
                    .ifPresent(modeChanging -> {
                        ItemStack module = modeChanging.getActiveModule();
                        int actualCount = 0;

                        int maxDuration = modeChanging.getModularItemStack().getUseDuration();
                        if (!module.isEmpty()) {
                            // Plasma Cannon
                            if (ItemUtils.getRegistryName(module).equals(MPSRegistryNames.PLASMA_CANNON_MODULE)) {
                                actualCount = (maxDuration - player.getUseItemRemainingTicks());
                                currentPlasma.set(currentPlasma.get() + (Math.min(actualCount, 50)) * 2);
                                maxPlasma.set(maxPlasma.get() + 100F);

                                // Ore Scanner or whatever
                            } else {
                                actualCount = (maxDuration - player.getUseItemRemainingTicks());
                                currentPlasma.set(currentPlasma.get() + (Math.min(actualCount, 40)) * 2.5F);
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
                waterMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, MathUtils.clampFloat(currWater.get(), 0, maxWater.get()) / maxWater.get());
                StringUtils.drawRightAlignedShadowedString(poseStack, currWaterStr.get(), stringX, meterTextOffsetY + top + (totalMeters - numMeters) * 9);
                numMeters--;
            }

            if (plasmaMeter != null) {
                plasmaMeter.draw(poseStack, left, top + (totalMeters - numMeters) * 9, currentPlasma.get() / maxPlasma.get());
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

            if (maxWater.get() > 0) {
                StringUtils.drawRightAlignedShadowedString(poseStack, currWaterStr.get() + '/' + maxWaterStr.get() + " buckets", stringX, top + (numReadouts * 9));
                numReadouts += 1;
            }

            if (maxPlasma.get() > 0 /* && drawPlasmaMeter */) {
                StringUtils.drawRightAlignedShadowedString(poseStack, currPlasmaStr + '/' + maxPlasmaStr + "%", stringX, top + (numReadouts * 9));
            }
        }
    };
}
