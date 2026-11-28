package lehjr.powersuits.common.capability.tool;

import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.heat.ModularItemHeatWrapper;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.config.PowerFistConfig;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class PowerFistCap implements ICapabilityProvider {
    final ItemStack itemStack;
    final EquipmentSlot targetSlot;
    final PowerFistModeChangingWrapper modularItem;
    final LazyOptional<IModeChangingItem> modularItemHolder;

    final PowerFistRenderWrapper modelSpec;
    final LazyOptional<IModelSpec> modelSpecHolder;

    final ModularItemHeatWrapper heatStorage;
    final LazyOptional<IHeatStorage> heatHolder;

    public PowerFistCap(ItemStack itemStackIn, int tier) {
        this.itemStack = itemStackIn;
        this.targetSlot = EquipmentSlot.MAINHAND;
        int slots = getSlots(tier);
        double maxHeat = getMaxHeat(tier);

        this.modularItem = new PowerFistModeChangingWrapper(itemStack, tier, slots);

        this.modularItemHolder = LazyOptional.of(()-> {
            modularItem.loadCapValues();
            return modularItem;
        });

        this.modelSpec = new PowerFistRenderWrapper(itemStack);
        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);

        this.heatStorage = new ModularItemHeatWrapper(itemStack, maxHeat);
        heatHolder = LazyOptional.of(() -> {
            modularItem.loadCapValues();
            heatStorage.loadCapValues();
            return heatStorage;
        });
    }

    static int getSlots(int tier) {
        return switch (tier) {
            case 1 -> PowerFistConfig.powerFistInventorySlots1;
            case 2 -> PowerFistConfig.powerFistInventorySlots2;
            case 3 -> PowerFistConfig.powerFistInventorySlots3;
            case 4 -> PowerFistConfig.powerFistInventorySlots4;
            default -> 0;
        };
    }

    static double getMaxHeat(int tier) {
        return switch (tier) {
            case 1 -> PowerFistConfig.powerFistMaxHeat1;
            case 2 -> PowerFistConfig.powerFistMaxHeat2;
            case 3 -> PowerFistConfig.powerFistMaxHeat3;
            case 4 -> PowerFistConfig.powerFistMaxHeat4;
            default -> 0;
        };
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> modularItemCapability = NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM.orEmpty(cap, modularItemHolder);
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
            return modularItem.getStackInSlot(0).getCapability(cap, side);
        }

        return LazyOptional.empty();
    }
}
