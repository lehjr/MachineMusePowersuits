package lehjr.powersuits.common.capabilities.item.armor;

import lehjr.numina.common.capability.inventory.modularitem.ModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.config.MPSCommonConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorModularItemWrapper extends ModularItem {
    ArmorItem.Type type;

    public PowerArmorModularItemWrapper(@NotNull ItemStack modularItem, int tier) {
        this(modularItem, tier, getActualSize(modularItem, tier));
    }

    public PowerArmorModularItemWrapper(@Nonnull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, size);
        this.type = ItemUtils.getArmorType(modularItem);
        ArmorItem.Type type = ItemUtils.getArmorType(getModularItemStack());
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();

        switch (type) {
            case HELMET -> {
                switch (getTier()) {
                    case 1 -> {
                        // Note on ranged wrapper. The max slot is actually max plus 1.
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 2 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 3 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 4 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }
                }
            }

            case CHESTPLATE -> {
                switch (getTier()) {
                    case 1 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 2 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 3 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 4 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }
                }
            }

            case LEGGINGS -> {
                switch (getTier()) {
                    case 1 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 2 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 3 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 4 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }
                }

            }

            case BOOTS -> {
                switch (getTier()) {
                    case 1 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 2 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 3 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }

                    case 4 -> {
                        rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                        if (getSlots() > 1) {
                            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                            if (getSlots() > 2) {
                                rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                                if (getSlots() > 3) {
                                    rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                                }
                            }
                        }
                        setRangedWrapperMap(rangedWrapperMap);
                    }
                }
            }
        }
    }


    public static int getActualSize(ItemStack modularItem, int tier) {
        if (MPSCommonConfig.COMMON_SPEC.isLoaded()) {
            ArmorItem.Type type = ItemUtils.getArmorType(modularItem);
            switch (type) {
                case HELMET -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorHelmInventorySlots1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorHelmInventorySlots2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorHelmInventorySlots3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorHelmInventorySlots4;
                        }
                    }
                }
                case CHESTPLATE -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorChestPlateInventorySlots1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorChestPlateInventorySlots2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorChestPlateInventorySlots3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorChestPlateInventorySlots4;
                        }
                    }
                }
                case LEGGINGS -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorLeggingsInventorySlots1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorLeggingsInventorySlots2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorLeggingsInventorySlots3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorLeggingsInventorySlots4;
                        }
                    }
                }
                case BOOTS -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorBootsInventorySlots1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorBootsInventorySlots2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorBootsInventorySlots3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorBootsInventorySlots4;
                        }
                    }
                }
            }
        }
        return 4;
    }
}

/**
 * Armor tiers:
 * ----------------
 * 1) primitive (maybe stone with few slots... like an early stage thing)
 * 2) iron age
 * 3) modern
 * 4) post-modern age (advanced materials)
 */




//        final ArmorItem.Type targetType;
//        final ModularItem modularItem;
//        final LazyOptional<IItemHandler> modularItemHolder;
//
//        final ArmorModelSpecNBT modelSpec;
//        final LazyOptional<IModelSpec> modelSpecHolder;
//
//        final LazyOptional<IHeatStorage> heatHolder;
//
//        final LazyOptional<IFluidHandlerItem> fluidHolder;
//
//        double maxHeat;
//
//        public PowerArmorCap(@Nonnull ItemStack itemStackIn, ArmorItem.Type type) {
//            this.itemStack = itemStackIn;
//            this.targetType = type;
//            Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
//            switch(targetType) {
//                case HELMET: {
//                    this.modularItem = new ModularItem(itemStack, 18) {{
//                        rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
//                        rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
//                        setRangedWrapperMap(rangedWrapperMap);
//                    }};
//
//                    this.maxHeat = MPSSettings.getMaxHeatHelmet();
//                    break;
//                }
//
//                case CHESTPLATE: {
//                    this.modularItem = new ModularItem(itemStack, 18) {{
//                        rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
//                        rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
//                        this.setRangedWrapperMap(rangedWrapperMap);
//                    }};
//                    this.maxHeat = MPSSettings.getMaxHeatChestplate();
//                    break;
//                }
//
//                case LEGGINGS: {
//                    this.modularItem = new ModularItem(itemStackIn, 10) {{
//                        rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
//                        rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
//                        this.setRangedWrapperMap(rangedWrapperMap);
//                    }};
//                    this.maxHeat = MPSSettings.getMaxHeatLegs();
//                    break;
//                }
//
//                case BOOTS: {
//                    this.modularItem = new ModularItem(itemStack, 8) {{
//                        rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
//                        rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
//                        rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 2, this.getSlots()));
//                        this.setRangedWrapperMap(rangedWrapperMap);
//                    }};
//                    this.maxHeat = MPSSettings.getMaxHeatBoots();
//                    break;
//                }
//
//                default:
//                    this.modularItem = new ModularItem(itemStack, 8);
//
//            }
//
//            this.modularItemHolder = LazyOptional.of(()-> {
//                modularItem.loadCapValues();
//                return modularItem;
//            });
//
//            this.modelSpec = new ArmorModelSpecNBT(itemStackIn);
//            this.modelSpecHolder = LazyOptional.of(()-> modelSpec);
//
//
//            heatHolder = LazyOptional.of(() -> {
//                modularItem.loadCapValues();
//
//                final HeatItemWrapper heatStorage = new HeatItemWrapper(itemStack, maxHeat, modularItem.getStackInSlot(0).getCapability(NuminaCapabilities.POWER_MODULE));
//                heatStorage.loadCapValues();
//                return heatStorage;
//            });
//
//            this.fluidHolder = LazyOptional.of(()-> {
//                if (targetType == ArmorItem.Type.CHESTPLATE) {
//                    modularItem.loadCapValues();
//                    return modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(new EmptyFluidHandler());
//                } else {
//                    return new EmptyFluidHandler();
//                }
//            });
//        }
//
//        @Nonnull
//        @Override
//        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//            if (cap == null) {
//                return LazyOptional.empty();
//            }
//
//            final LazyOptional<T> modularItemCapability = ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, modularItemHolder);
//            if (modularItemCapability.isPresent()) {
//                return modularItemCapability;
//            }
//
//            final LazyOptional<T> modelSpecCapability = NuminaCapabilities.RENDER.orEmpty(cap, modelSpecHolder);
//            if (modelSpecCapability.isPresent()) {
//                return modelSpecCapability;
//            }
//
//            final LazyOptional<T> heatCapability = NuminaCapabilities.HEAT.orEmpty(cap, heatHolder);
//            if (heatCapability.isPresent()) {
//                return heatCapability;
//            }
//
//            // update item handler to gain access to the battery module if installed
//            if (cap == ForgeCapabilities.ENERGY) {
//                modularItem.loadCapValues();
//                // armor first slot is armor plating, second slot is energy
//                return modularItem.getStackInSlot(1).getCapability(cap, side);
//            }
//
//            final LazyOptional<T> fluidCapability = ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, fluidHolder);
//            if (fluidCapability.isPresent()) {
//                return fluidCapability;
//            }
//
//            // Try this and see if it works
//            for (int i=0; i < modularItem.getSlots(); i++) {
//                ItemStack module = modularItem.getStackInSlot(i);
//                if (!module.isEmpty()) {
//                    if (module.getCapability(cap).isPresent()) {
//                        return module.getCapability(cap);
//                    }
//                }
//            }
//            return LazyOptional.empty();
//        }
//
//        class EmptyFluidHandler extends FluidHandlerItemStack {
//            public EmptyFluidHandler() {
//                super(ItemStack.EMPTY, 0);
//            }
//        }




