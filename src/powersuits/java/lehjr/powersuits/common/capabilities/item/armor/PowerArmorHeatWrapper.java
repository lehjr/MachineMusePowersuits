package lehjr.powersuits.common.capabilities.item.armor;

import lehjr.numina.common.capability.heat.AbstractModularItemHeatWrapper;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.config.MPSCommonConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PowerArmorHeatWrapper extends AbstractModularItemHeatWrapper {
    public PowerArmorHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        super(itemStack, tier);
    }

    @Override
    public double getBaseMaxHeat(ItemStack modularItem, int tier) {
        if (MPSCommonConfig.COMMON_SPEC.isLoaded()) {
            ArmorItem.Type type = ItemUtils.getArmorType(modularItem);
            switch (type) {
                case HELMET -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorHelmMaxHeat1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorHelmMaxHeat2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorHelmMaxHeat3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorHelmMaxHeat4;
                        }
                    }
                }
                case CHESTPLATE -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorChestPlateMaxHeat1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorChestPlateMaxHeat2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorChestPlateMaxHeat3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorChestPlateMaxHeat4;
                        }
                    }
                }
                case LEGGINGS -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorLeggingsMaxHeat1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorLeggingsMaxHeat2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorLeggingsMaxHeat3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorLeggingsMaxHeat4;
                        }
                    }
                }
                case BOOTS -> {
                    switch (tier) {
                        case 1 -> {
                            return MPSCommonConfig.armorBootsMaxHeat1;
                        }
                        case 2 -> {
                            return MPSCommonConfig.armorBootsMaxHeat2;
                        }
                        case 3 -> {
                            return MPSCommonConfig.armorBootsMaxHeat3;
                        }
                        case 4 -> {
                            return MPSCommonConfig.armorBootsMaxHeat4;
                        }
                    }
                }
            }
        }
        return 4;
    }

}
