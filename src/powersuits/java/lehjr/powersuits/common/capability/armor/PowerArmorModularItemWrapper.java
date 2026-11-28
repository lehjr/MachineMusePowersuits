package lehjr.powersuits.common.capability.armor;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RangedWrapper;

import java.util.HashMap;
import java.util.Map;

public class PowerArmorModularItemWrapper extends ModularItem {
    ArmorItem.Type type;

    public PowerArmorModularItemWrapper(ItemStack modularItem, ArmorItem.Type type, int tier, int size) {
        super(modularItem, tier, size);
        NuminaLogger.logDebug("size: " + size);

        this.type = type;
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
        int numSlots = getSlots();
        if(size > 0){
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
    }
}
