package lehjr.numina.common.capability;

import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class BatteryCapabilityProvider implements ICapabilityProvider {





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
                addBaseProperty(NuminaConstants.MAX_ENERGY, maxEnergy, "FE");
                addBaseProperty(NuminaConstants.MAX_TRAMSFER, maxTransfer, "FE");
            }};

        powerModuleHolder = Optional.of(powerModule);

        this.energyStorage = new ModuleEnergyWrapper(
                module,
                (int) powerModule.applyPropertyModifiers(NuminaConstants.MAX_ENERGY),
                (int) powerModule.applyPropertyModifiers(NuminaConstants.MAX_TRAMSFER)
        );

        energyStorageHolder = Lazy.of(() -> {
            energyStorage.loadCapValues();
            return energyStorage;
        });
    }

    @Nullable
    @Override
    public Object getCapability(Object capability, Object context) {
        final LazyOptional<T> capabilityPowerModule = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
        if (capabilityPowerModule.isPresent()) {
            return capabilityPowerModule;
        }

        final LazyOptional<T> energyCapability = Optional.of(Capabilities.EnergyStorage.ITEM).orEmpty(capability, energyStorageHolder);
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


        /**
         * CapabilityUpdate --------------------------------------------------------------------------
         */
        @Override
        public void loadCapValues() {
            final CompoundTag nbt = TagUtils.getModuleTag(module);
            if (nbt.contains(NuminaConstants.ENERGY, Tag.TAG_INT)) {
                deserializeNBT(nbt.get(NuminaConstants.ENERGY));
            }
        }

        @Override
        public void onValueChanged() {
            final CompoundTag nbt = TagUtils.getModuleTag(module);
            if (nbt != null) { // capability is null during game loading
                nbt.put(NuminaConstants.ENERGY, serializeNBT());
            }
        }



}
