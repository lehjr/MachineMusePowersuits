package lehjr.powersuits.common.capability.armor;

import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorModularItemWrapper extends ModularItem {
    ArmorItem.Type type;

    public PowerArmorModularItemWrapper(ItemStack modularItem, ArmorItem.Type type, int tier) {
        this(modularItem, type, tier, getActualSize(tier, type));
    }

    public PowerArmorModularItemWrapper(ItemStack modularItem, ArmorItem.Type type, int tier, int size) {
        super(modularItem, tier, size);
        this.type = ItemUtils.getArmorType(modularItem);
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
        int numSlots = getSlots();

        switch (type) {
            case HELMET -> {

                // Note on ranged wrapper. The max slot is actually max plus 1.
                rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                if (numSlots > 1) {
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                    if (numSlots > 2) {
                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                        if (numSlots > 3) {
                            rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, numSlots));
                        }
                    }
                }
                setRangedWrapperMap(rangedWrapperMap);
            }
            case CHESTPLATE -> {
                rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                if (numSlots > 1) {
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                    if (numSlots > 2) {
                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                        // TODO?: cooling system sub category?
                        if (numSlots > 3) {
                            rangedWrapperMap.put(ModuleCategory.FLUID_STORAGE, new RangedWrapper(this, 3, 4));
                            if(numSlots > 4) {
                                rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 4, numSlots));
                            }
                        }
                    }
                }
                setRangedWrapperMap(rangedWrapperMap);
            }

            case LEGGINGS -> {
                rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                if (numSlots > 1) {
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                    if (numSlots > 2) {
                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                        if (numSlots > 3) {
                            rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, numSlots));
                        }
                    }
                }
                setRangedWrapperMap(rangedWrapperMap);
            }

            case BOOTS -> {
                rangedWrapperMap.put(ModuleCategory.ARMOR, new RangedWrapper(this, 0, 1));
                if (numSlots > 1) {
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 1, 2));
                    if (numSlots > 2) {
                        rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION, new RangedWrapper(this, 2, 3));
                        if (numSlots > 3) {
                            rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 3, numSlots));
                        }
                    }
                }
                setRangedWrapperMap(rangedWrapperMap);
            }
        }
    }

    public static int getActualSize(int tier, ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> switch (tier) {
                    case 1 -> ArmorConfig.helmInventorySlots1;
                    case 2 -> ArmorConfig.helmInventorySlots2;
                    case 3 -> ArmorConfig.helmInventorySlots3;
                    case 4 -> ArmorConfig.helmInventorySlots4;
                    default -> 0;
                };
                case CHESTPLATE -> switch (tier) {
                    case 1 -> ArmorConfig.chestPlateInventorySlots1;
                    case 2 -> ArmorConfig.chestPlateInventorySlots2;
                    case 3 -> ArmorConfig.chestPlateInventorySlots3;
                    case 4 -> ArmorConfig.chestPlateInventorySlots4;
                    default -> 0;
                };
                case LEGGINGS -> switch (tier) {
                    case 1 -> ArmorConfig.leggingsInventorySlots1;
                    case 2 -> ArmorConfig.leggingsInventorySlots2;
                    case 3 -> ArmorConfig.leggingsInventorySlots3;
                    case 4 -> ArmorConfig.leggingsInventorySlots4;
                    default -> 0;
                };
                case BOOTS -> switch (tier) {
                    case 1 -> ArmorConfig.bootsInventorySlots1;
                    case 2 -> ArmorConfig.bootsInventorySlots2;
                    case 3 -> ArmorConfig.bootsInventorySlots3;
                    case 4 -> ArmorConfig.bootsInventorySlots4;
                    default -> 0;
                };
            };
        }
}
