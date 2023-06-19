package lehjr.numina.common.capabilities;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.constants.TagConstants;
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

    public BatteryCapabilityProvider(final ItemStack module, int tier, int maxEnergy, int maxTransfer) {
        powerModule = new PowerModule(module, ModuleCategory.ENERGY_STORAGE, ModuleTarget.ALLITEMS, NuminaSettings::getModuleConfig) {
            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public String getModuleGroup() {
                return "Battery";
            }

            {
                addBaseProperty(TagConstants.MAX_ENERGY, maxEnergy, "FE");
                addBaseProperty(TagConstants.MAX_TRAMSFER, maxTransfer, "FE");
            }};

        powerModuleHolder = LazyOptional.of(() -> powerModule);

        this.energyStorage = new ModuleEnergyWrapper(
                module,
                (int) powerModule.applyPropertyModifiers(TagConstants.MAX_ENERGY),
                (int) powerModule.applyPropertyModifiers(TagConstants.MAX_TRAMSFER)
        );



        energyStorageHolder = LazyOptional.of(() -> {
            energyStorage.loadCapValues();
            return energyStorage;
        });
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
        energyStorage.deserializeNBT(IntTag.valueOf(energyStorage.getMaxEnergyStored()));
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
            if (!canReceive()) {
                return 0;
            }
            final int energyReceived = (int)Math.min(getMaxEnergyStored() - energy,
                    Math.min(powerModuleHolder.map(pm->pm.applyPropertyModifiers(TagConstants.MAX_TRAMSFER)).orElse(0D), maxReceive));

            if (!simulate && energyReceived != 0) {
                energy += energyReceived;
                onValueChanged();
            }
            return energyReceived;
        }

        @Override
        public int extractEnergy(final int maxExtract, final boolean simulate) {
            if (!canExtract()) {
                return 0;
            }

            final int energyExtracted = Math.min(energy, Math.min((int) powerModule.applyPropertyModifiers(TagConstants.MAX_TRAMSFER), maxExtract));

            if (!simulate && energyExtracted != 0) {
                energy -= energyExtracted;
                onValueChanged();
            }
            return energyExtracted;
        }

        @Override
        public int getMaxEnergyStored() {
            double ret = powerModuleHolder.map(pm->pm.applyPropertyModifiers(TagConstants.MAX_ENERGY)).orElse(0D);
            return (int)ret;
        }

        @Override
        public boolean canExtract() {
            return (int) powerModule.applyPropertyModifiers(TagConstants.MAX_TRAMSFER) > 0;
        }

        @Override
        public boolean canReceive() {
            return (int) powerModule.applyPropertyModifiers(TagConstants.MAX_TRAMSFER) > 0;
        }
    }
}
