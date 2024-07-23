package lehjr.powersuits.common.capabilities.item.armor;

import lehjr.numina.common.capability.heat.AbstractModularItemHeatWrapper;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class PowerArmorHeatWrapper extends AbstractModularItemHeatWrapper {
    public PowerArmorHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        super(itemStack, tier);
    }

    @Override
    public double getBaseMaxHeat(ItemStack modularItem, int tier) {
        if (ArmorConfig.MPS_ARMOR_CONFIG_SPEC.isLoaded()) {
            ArmorItem.Type type = ItemUtils.getArmorType(modularItem);
            switch (type) {
                case HELMET -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.armorHelmMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.armorHelmMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.armorHelmMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.armorHelmMaxHeat4;
                        }
                    }
                }
                case CHESTPLATE -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.armorChestPlateMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.armorChestPlateMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.armorChestPlateMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.armorChestPlateMaxHeat4;
                        }
                    }
                }
                case LEGGINGS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.armorLeggingsMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.armorLeggingsMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.armorLeggingsMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.armorLeggingsMaxHeat4;
                        }
                    }
                }
                case BOOTS -> {
                    switch (tier) {
                        case 1 -> {
                            return ArmorConfig.armorBootsMaxHeat1;
                        }
                        case 2 -> {
                            return ArmorConfig.armorBootsMaxHeat2;
                        }
                        case 3 -> {
                            return ArmorConfig.armorBootsMaxHeat3;
                        }
                        case 4 -> {
                            return ArmorConfig.armorBootsMaxHeat4;
                        }
                    }
                }
            }
        }
        return 4;
    }

}
