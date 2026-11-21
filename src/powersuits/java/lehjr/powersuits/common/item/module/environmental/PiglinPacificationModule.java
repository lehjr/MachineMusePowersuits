package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PiglinPacificationModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final  PiglinPacificationCap powerModule;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            powerModule = new  PiglinPacificationCap(module);

            powerModuleHolder = LazyOptional.of(() -> powerModule);
        }


        public static class PiglinPacificationCap extends PowerModule {
            public PiglinPacificationCap(ItemStack module) {
                super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
            }

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean isAllowed() {
                return EnvironmentalModuleConfig.piglinPacificationIsAllowed;
            }

            @Override
            public boolean isModuleOnline() {
                return true;
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}