package com.lehjr.numina.common.item;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.numina.common.config.NuminaCommonConfig;
import com.lehjr.numina.common.utils.AdditionalInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class Battery extends Item {
    public Battery() {
        super(new Properties().stacksTo(1));
    }

    public static class BatteryPowerModule extends PowerModule {
        int tier;
        public BatteryPowerModule(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_STORAGE, ModuleTarget.ALLITEMS);
            this.tier = tier;
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> NuminaCommonConfig.batteryIsAllowed1;
                case 2 -> NuminaCommonConfig.batteryIsAllowed2;
                case 3 -> NuminaCommonConfig.batteryIsAllowed3;
                case 4 -> NuminaCommonConfig.batteryIsAllowed4;
                default -> false;
            };
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        IEnergyStorage iEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(iEnergyStorage != null) {
            return iEnergyStorage.getMaxEnergyStored() > 0;
        }
        return false;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage iEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        // prevent integer overflow by using double in calculation
        double retVal = 1D;
        if(iEnergyStorage != null) {
            return (int) (iEnergyStorage.getEnergyStored() * 13.0D / iEnergyStorage.getMaxEnergyStored());
        }
        return (int)retVal;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> toolTips, TooltipFlag flags) {
        super.appendHoverText(itemStack, context, toolTips, flags);
        AdditionalInfo.appendHoverText(itemStack, context, toolTips, flags, Screen.hasShiftDown());
    }
}
