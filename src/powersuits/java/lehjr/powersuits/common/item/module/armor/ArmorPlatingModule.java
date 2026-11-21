package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.config.module.ArmorModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArmorPlatingModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        private final ArmorPlatingCapabilityWrapper powerModule;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        ItemStack module;

        public CapProvider(final ItemStack module) {
            this.module = module;
            powerModule = new ArmorPlatingCapabilityWrapper(module, getTier(module));
            powerModuleHolder = LazyOptional.of(() -> powerModule);
        }

        public static class ArmorPlatingCapabilityWrapper extends PowerModule {
            int tier;

            public ArmorPlatingCapabilityWrapper(ItemStack module, int tier) {
                super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);
                this.tier = tier;
                switch (tier) {
                    case 1 -> {
                        addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, ArmorModuleConfig.ironPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                        addBaseProperty(NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.ironPlatingMaxHeat);
                        addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.ironPlatingKnockBackResistance);
                        addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.ironPlatingArmorToughness);
                    }
                    case 2-> {
                        addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,  ArmorModuleConfig.diamondPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                        addBaseProperty(NuminaConstants.MAXIMUM_HEAT,  ArmorModuleConfig.diamondPlatingMaxHeat);
                        addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,  ArmorModuleConfig.diamondPlatingKnockBackResistance);
                        addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,  ArmorModuleConfig.diamondPlatingArmorToughness);
                    }
                    case 3 -> {
                        addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, ArmorModuleConfig.netheritePlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                        addBaseProperty(NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.netheritePlatingMaxHeat);
                        addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.netheritePlatingKnockBackResistance);
                        addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.netheritePlatingArmorToughness);
                    }
                }
            }

            @Override
            public String getModuleGroup() {
                return "Armor";
            }

            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public boolean isAllowed() {
                return switch (tier) {
                    case 1-> ArmorModuleConfig.ironPlatingIsAllowed;
                    case 2-> ArmorModuleConfig.diamondPlatingIsAllowed;
                    case 3-> ArmorModuleConfig.netheritePlatingIsAllowed;
                    default -> false;
                };
            }

            @Override
            public boolean isModuleOnline() {
                return true;
            }
        }

        static int getTier(ItemStack stack) {
            if(stack.is(MPSItems.IRON_PLATING_MODULE.get())) {
                return 1;
            }
            if(stack.is(MPSItems.DIAMOND_PLATING_MODULE.get())) {
                return 2;
            }
            if (stack.is(MPSItems.NETHERITE_PLATING_MODULE.get())) {
                return 3;
            }
            return 0;
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