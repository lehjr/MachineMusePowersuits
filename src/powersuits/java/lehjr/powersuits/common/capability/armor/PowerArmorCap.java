package lehjr.powersuits.common.capability.armor;

import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.heat.ModularItemHeatWrapper;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.capability.legacy.PowerArmorLegacyCap;
import lehjr.powersuits.common.config.ArmorConfig;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PowerArmorCap implements ICapabilityProvider {
    final ItemStack itemStack;
    final ArmorItem.Type targetType;
    final PowerArmorModularItemWrapper modularItem;
    final LazyOptional<IModularItem> modularItemHolder;

    final PowerArmorRenderWrapper modelSpec;
    final LazyOptional<IModelSpec> modelSpecHolder;

    final LazyOptional<IHeatStorage> heatHolder;

    final LazyOptional<IFluidHandlerItem> fluidHolder;

    int tier;
    double maxHeat;

    public PowerArmorCap(@Nonnull ItemStack itemStackIn, ArmorItem.Type type) {
        this.itemStack = itemStackIn;
        this.targetType = type;
        this.tier = getTier(itemStackIn, type);

        this.modularItem = new PowerArmorModularItemWrapper(itemStackIn, type, tier);

        this.modularItemHolder = LazyOptional.of(()-> {
            modularItem.loadCapValues();
            return modularItem;
        });

        this.modelSpec = new PowerArmorRenderWrapper(itemStackIn);
        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);


        heatHolder = LazyOptional.of(() -> {
            modularItem.loadCapValues();

            final ModularItemHeatWrapper heatStorage = new PowerArmorHeatWrapper(itemStack, type, tier);
            heatStorage.loadCapValues();
            return heatStorage;
        });

        this.fluidHolder = LazyOptional.of(()-> {
            if (targetType == ArmorItem.Type.CHESTPLATE) {
                modularItem.loadCapValues();
                return modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(new EmptyFluidHandler());
            } else {
                return new EmptyFluidHandler();
            }
        });
    }

    static int getTier(ItemStack stack, ArmorItem.Type type) {
        if(type == ArmorItem.Type.HELMET) {
            if(stack.is(MPSItems.POWER_ARMOR_HELMET_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.POWER_ARMOR_HELMET_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.POWER_ARMOR_HELMET_3.get())) {
                return 3;
            }
            if(stack.is(MPSItems.POWER_ARMOR_HELMET_4.get())) {
                return 4;
            }

        } else if(type == ArmorItem.Type.CHESTPLATE) {
            if(stack.is(MPSItems.POWER_ARMOR_CHESTPLATE_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.POWER_ARMOR_CHESTPLATE_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.POWER_ARMOR_CHESTPLATE_3.get())) {
                return 3;
            }
            if(stack.is(MPSItems.POWER_ARMOR_CHESTPLATE_4.get())) {
                return 4;
            }

        } else if(type == ArmorItem.Type.LEGGINGS) {
            if(stack.is(MPSItems.POWER_ARMOR_LEGGINGS_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.POWER_ARMOR_LEGGINGS_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.POWER_ARMOR_LEGGINGS_3.get())) {
                return 3;
            }
            if(stack.is(MPSItems.POWER_ARMOR_LEGGINGS_4.get())) {
                return 4;
            }

        } else if(type == ArmorItem.Type.BOOTS) {
            if(stack.is(MPSItems.POWER_ARMOR_BOOTS_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.POWER_ARMOR_BOOTS_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.POWER_ARMOR_BOOTS_3.get())) {
                return 3;
            }
            if(stack.is(MPSItems.POWER_ARMOR_BOOTS_4.get())) {
                return 4;
            }
        }
        return 0;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> modularItemCapability = NuminaCapabilities.Inventory.MODULAR_ITEM.orEmpty(cap, modularItemHolder);
        if (modularItemCapability.isPresent()) {
            return modularItemCapability;
        }

        final LazyOptional<T> modelSpecCapability = NuminaCapabilities.RENDER.orEmpty(cap, modelSpecHolder);
        if (modelSpecCapability.isPresent()) {
            return modelSpecCapability;
        }

        final LazyOptional<T> heatCapability = NuminaCapabilities.HEAT.orEmpty(cap, heatHolder);
        if (heatCapability.isPresent()) {
            return heatCapability;
        }

        // update item handler to gain access to the battery module if installed
        if (cap == ForgeCapabilities.ENERGY) {
            modularItem.loadCapValues();
            // armor first slot is armor plating, second slot is energy
            return modularItem.getStackInSlot(1).getCapability(cap, side);
        }

        final LazyOptional<T> fluidCapability = ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, fluidHolder);
        if (fluidCapability.isPresent()) {
            return fluidCapability;
        }

        // Try this and see if it works
        for (int i=0; i < modularItem.getSlots(); i++) {
            ItemStack module = modularItem.getStackInSlot(i);
            if (!module.isEmpty()) {
                if (module.getCapability(cap).isPresent()) {
                    return module.getCapability(cap);
                }
            }
        }
        return LazyOptional.empty();
    }

    class EmptyFluidHandler extends FluidHandlerItemStack {
        public EmptyFluidHandler() {
            super(ItemStack.EMPTY, 0);
        }
    }
}