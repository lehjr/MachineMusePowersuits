package com.lehjr.powersuits.common.capabilities.item.armor;

import com.lehjr.numina.common.capabilities.heat.AbstractModularItemHeatWrapper;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class PowerArmorHeatWrapper extends AbstractModularItemHeatWrapper {
    public PowerArmorHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        super(itemStack, tier);
    }

    @Override
    public double getBaseMaxHeat(ItemStack modularItem, int tier) {
        if (ArmorConfig.ARMOR_CONFIG_SPEC.isLoaded()) {
            ArmorItem.Type type = ItemUtils.getArmorType(modularItem);
            switch (type) {
                case HELMET -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.helmMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.helmMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.helmMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.helmMaxHeat4;
                        }
                    }
                }
                case CHESTPLATE -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.chestPlateMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.chestPlateMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.chestPlateMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.chestPlateMaxHeat4;
                        }
                    }
                }
                case LEGGINGS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.leggingsMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.leggingsMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.leggingsMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.leggingsMaxHeat4;
                        }
                    }
                }
                case BOOTS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.bootsMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.bootsMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.bootsMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.bootsMaxHeat4;
                        }
                    }
                }
            }
        }
        return 4;
    }

}
