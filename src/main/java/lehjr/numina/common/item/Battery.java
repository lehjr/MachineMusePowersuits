package lehjr.numina.common.item;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.energy.IEnergyExtended;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.utils.AdditionalInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class Battery extends Item {
    public Battery(int tierIn) {
        super(new Item.Properties().stacksTo(1));
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
                case 1 -> NuminaCommonConfig.batteryBasicIsAllowed;
                case 2 -> NuminaCommonConfig.batteryAdvancedIsAllowed;
                case 3 -> NuminaCommonConfig.batteryEliteIsAllowed;
                case 4 -> NuminaCommonConfig.batteryUltimateIsAllowed;
                default -> false;
            };
        }
    }

    public static class BatteryEnergyStorage implements IEnergyExtended {
        protected final MutableDataComponentHolder parent;
        protected final DataComponentType<Integer> energyComponent;
        int tier;

        /**
         * Creates a new ComponentEnergyStorage with a data component as the backing store for the energy value.
         *
         * @param parent          The parent component holder, such as an {@link ItemStack}
         * @param energyComponent The data component referencing the stored energy of the item stack
         */
        public BatteryEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int tier) {
            this.parent = parent;
            this.energyComponent = energyComponent;
            this.tier = tier;
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            if (!canReceive() || toReceive <= 0) {
                return 0;
            }

            int energy = this.getEnergyStored();
            int energyReceived = Mth.clamp(getMaxEnergyStored() - energy, 0, Math.min(this.getMaxTransfer(), toReceive));
            if (!simulate && energyReceived > 0) {
                this.setEnergy(energy + energyReceived);
            }
            return energyReceived;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            if (!canExtract() || toExtract <= 0) {
                return 0;
            }

            int energy = this.getEnergyStored();
            int energyExtracted = Math.min(energy, Math.min(this.getMaxTransfer(), toExtract));
            if (!simulate && energyExtracted > 0) {
                this.setEnergy(energy - energyExtracted);
            }
            return energyExtracted;
        }

        @Override
        public int getEnergyStored() {
            int rawEnergy = this.parent.getOrDefault(this.energyComponent, 0);
            return Mth.clamp(rawEnergy, 0, this.getMaxEnergyStored());
        }

        @Override
        public int getMaxEnergyStored() {
            return switch (tier) {
                case 1 -> NuminaCommonConfig.batteryBasicMaxEnergy;
                case 2 -> NuminaCommonConfig.batteryAdvancedMaxEnergy;
                case 3 -> NuminaCommonConfig.batteryEliteMaxEnergy;
                case 4 -> NuminaCommonConfig.batteryUltimateMaxEnergy;
                default -> 0;
            };
        }

        @Override
        public int getMaxTransfer() {
            return switch (tier) {
                case 1 -> NuminaCommonConfig.batteryBasicMaxTransfer;
                case 2 -> NuminaCommonConfig.batteryAdvancedMaxTransfer;
                case 3 -> NuminaCommonConfig.batteryEliteMaxTransfer;
                case 4 -> NuminaCommonConfig.batteryUltimateMaxTransfer;
                default -> 0;
            };
        }

        @Override
        public boolean canExtract() {
            return this.getMaxTransfer() > 0;
        }

        @Override
        public boolean canReceive() {
            return this.getMaxTransfer() > 0;
        }

        /**
         * Writes a new energy value to the data component. Clamps to [0, capacity]
         *
         * @param energy The new energy value
         */
        protected void setEnergy(int energy) {
            int realEnergy = Mth.clamp(energy, 0, getMaxEnergyStored());
            this.parent.set(this.energyComponent, realEnergy);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 0).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        // prevent integer overflow by using double in calculation
        double retVal = NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() * 13.0D / iEnergyStorage.getMaxEnergyStored()).orElse(1D);
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
