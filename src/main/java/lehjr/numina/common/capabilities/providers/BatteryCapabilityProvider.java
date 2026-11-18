package lehjr.numina.common.capabilities.providers;

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.registration.NuminaItems;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BatteryCapabilityProvider implements ICapabilityProvider {

    public final PowerModule powerModule;
    private final ModuleEnergyWrapper energyStorage;

    private final LazyOptional<IPowerModule> powerModuleHolder;
    private final LazyOptional<IEnergyStorage> energyStorageHolder;
    final int maxEnergy;
    final int tier;
    final int maxTransfer;

    public BatteryCapabilityProvider(final ItemStack module) {//, int tier, int maxEnergy, int maxTransfer) {
        tier = getTier(module);
        maxEnergy = getMaxEnergy(module);
        maxTransfer = getMaxTransfer(module);

        powerModule = new PowerModule(module, ModuleCategory.ENERGY_STORAGE, ModuleTarget.ALLITEMS) {

            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public String getModuleGroup() {
                return "batteries";
            }

            @Override
            public boolean isModuleOnline() {
                return true;
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
        };

        powerModuleHolder = LazyOptional.of(() -> powerModule);

        this.energyStorage = new ModuleEnergyWrapper(
                module,
                maxEnergy,
                maxTransfer
        );

        energyStorageHolder = LazyOptional.of(() -> {
            energyStorage.loadCapValues();
            return energyStorage;
        });
    }

    static int getTier(ItemStack module) {
        if(module.is(NuminaItems.BASIC_BATTERY.get())) {
            return 1;
        }
        if(module.is(NuminaItems.ADVANCED_BATTERY.get())) {
            return 2;
        }
        if(module.is(NuminaItems.ELITE_BATTERY.get())) {
            return 3;
        }
        if(module.is(NuminaItems.ULTIMATE_BATTERY.get())) {
            return 4;
        }
        return 0;
    }

    static int getMaxEnergy(ItemStack module) {
        if(module.is(NuminaItems.BASIC_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxEnergy1;
        }
        if(module.is(NuminaItems.ADVANCED_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxEnergy2;
        }
        if(module.is(NuminaItems.ELITE_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxEnergy3;
        }
        if(module.is(NuminaItems.ULTIMATE_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxEnergy4;
        }
        return 0;
    }

    static int getMaxTransfer(ItemStack module) {
        if(module.is(NuminaItems.BASIC_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxTransfer1;
        }
        if(module.is(NuminaItems.ADVANCED_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxTransfer2;
        }
        if(module.is(NuminaItems.ELITE_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxTransfer3;
        }
        if(module.is(NuminaItems.ULTIMATE_BATTERY.get())) {
            return NuminaCommonConfig.batteryMaxTransfer4;
        }
        return 0;
    }

    /** ICapabilityProvider ----------------------------------------------------------------------- */
    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
        final LazyOptional<T> capabilityPowerModule = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
        if (capabilityPowerModule.isPresent()) {
            return capabilityPowerModule;
        }

        final LazyOptional<T> energyCapability = ForgeCapabilities.ENERGY.orEmpty(capability, energyStorageHolder);
        if (energyCapability.isPresent()) {
            return energyCapability;
        }
        return LazyOptional.empty();
    }

    public void setMaxEnergy(){
        energyStorage.deserializeNBT(IntTag.valueOf(maxEnergy));
        energyStorage.onValueChanged();
    }

    class ModuleEnergyWrapper extends EnergyStorage implements CapabilityUpdate {
        @Nonnull ItemStack module;
        public ModuleEnergyWrapper(@Nonnull ItemStack module, int maxEnergy, int maxTransfer) {
            super(maxEnergy, maxTransfer);
            this.module = module;
        }

        /**
         * CapabilityUpdate --------------------------------------------------------------------------
         */
        @Override
        public void loadCapValues() {
            final CompoundTag nbt = TagUtils.getModuleTag(module);
            if (nbt.contains(TagConstants.ENERGY, Tag.TAG_INT)) {
                deserializeNBT(nbt.get(TagConstants.ENERGY));
            }
        }

        @Override
        public void onValueChanged() {
            final CompoundTag nbt = TagUtils.getModuleTag(module);
            if (nbt != null && ForgeCapabilities.ENERGY != null) { // capability is null during game loading
                nbt.put(TagConstants.ENERGY, serializeNBT());
            }
        }

        /**
         * IEnergyStorage ----------------------------------------------------------------------------
         */
        @Override
        public int receiveEnergy(final int maxReceive, final boolean simulate) {
            final int energyReceived = super.receiveEnergy(maxReceive, simulate);
            if (!simulate && energyReceived != 0) {
                energy += energyReceived;
                onValueChanged();
            }
            return energyReceived;
        }

        @Override
        public int extractEnergy(final int maxExtract, final boolean simulate) {
            final int energyExtracted = super.extractEnergy(maxExtract, simulate);
            if (!simulate && energyExtracted != 0) {
                energy -= energyExtracted;
                onValueChanged();
            }
            return energyExtracted;
        }

        @Override
        public int getMaxEnergyStored() {
            return maxEnergy;
        }
    }
}
