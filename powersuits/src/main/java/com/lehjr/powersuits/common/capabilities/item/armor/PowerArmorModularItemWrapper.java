package com.lehjr.powersuits.common.capabilities.item.armor;

import com.lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorModularItemWrapper extends ModularItem {
    ArmorItem.Type type;

    public PowerArmorModularItemWrapper(@Nonnull ItemStack modularItem, int tier) {
        this(modularItem, tier, getActualSize(modularItem, tier));
    }

    public PowerArmorModularItemWrapper(@Nonnull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, size);
        this.type = ItemUtils.getArmorType(modularItem);
        ArmorItem.Type type = ItemUtils.getArmorType(getModularItemStack());
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();

        switch (type) {
            case HELMET -> {
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
            case CHESTPLATE -> {
                rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                if (getSlots() > 1) {
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                    if (getSlots() > 2) {
                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                        // TODO?: cooling system sub category?
                        if (getSlots() > 3) {
                            rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, this.getSlots()));
                        }
                    }
                }
                setRangedWrapperMap(rangedWrapperMap);
            }

            case LEGGINGS -> {
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

            case BOOTS -> {
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

    public static int getActualSize(ItemStack modularItem, int tier) {
        if (ArmorConfig.ARMOR_CONFIG_SPEC.isLoaded()) {
            ArmorItem.Type type = ItemUtils.getArmorType(modularItem);
            switch (type) {
                case HELMET -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.helmInventorySlots1;
                        }
                        case 2 -> {
                            return ArmorConfig.helmInventorySlots2;
                        }
                        case 3 -> {
                            return ArmorConfig.helmInventorySlots3;
                        }
                        case 4 -> {
                            return ArmorConfig.helmInventorySlots4;
                        }
                    }
                }
                case CHESTPLATE -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.chestPlateInventorySlots1;
                        }
                        case 2 -> {
                            return ArmorConfig.chestPlateInventorySlots2;
                        }
                        case 3 -> {
                            return ArmorConfig.chestPlateInventorySlots3;
                        }
                        case 4 -> {
                            return ArmorConfig.chestPlateInventorySlots4;
                        }
                    }
                }
                case LEGGINGS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.leggingsInventorySlots1;
                        }
                        case 2 -> {
                            return ArmorConfig.leggingsInventorySlots2;
                        }
                        case 3 -> {
                            return ArmorConfig.leggingsInventorySlots3;
                        }
                        case 4 -> {
                            return ArmorConfig.leggingsInventorySlots4;
                        }
                    }
                }
                case BOOTS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.bootsInventorySlots1;
                        }
                        case 2 -> {
                            return ArmorConfig.bootsInventorySlots2;
                        }
                        case 3 -> {
                            return ArmorConfig.bootsInventorySlots3;
                        }
                        case 4 -> {
                            return ArmorConfig.bootsInventorySlots4;
                        }
                    }
                }
            }
        }
        return 4;
    }
}
