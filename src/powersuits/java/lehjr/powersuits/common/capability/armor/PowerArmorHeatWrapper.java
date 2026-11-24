package lehjr.powersuits.common.capability.armor;

import lehjr.numina.common.capabilities.heat.ModularItemHeatWrapper;
import lehjr.powersuits.common.config.ArmorConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class PowerArmorHeatWrapper extends ModularItemHeatWrapper {
    public PowerArmorHeatWrapper(@Nonnull ItemStack itemStack, ArmorItem.Type type, int tier) {
        super(itemStack, getBaseMaxHeat(tier, type));
    }

    static double getBaseMaxHeat(int tier, ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> switch (tier) {
                case 1 -> ArmorConfig.helmMaxHeat1;
                case 2 -> ArmorConfig.helmMaxHeat2;
                case 3 -> ArmorConfig.helmMaxHeat3;
                case 4 -> ArmorConfig.helmMaxHeat4;
                default -> 0;
            };
            case CHESTPLATE -> switch (tier) {
                case 1 -> ArmorConfig.chestPlateMaxHeat1;
                case 2 -> ArmorConfig.chestPlateMaxHeat2;
                case 3 -> ArmorConfig.chestPlateMaxHeat3;
                case 4 -> ArmorConfig.chestPlateMaxHeat4;
                default -> 0;
            };
            case LEGGINGS -> switch (tier) {
                case 1 -> ArmorConfig.leggingsMaxHeat1;
                case 2 -> ArmorConfig.leggingsMaxHeat2;
                case 3 -> ArmorConfig.leggingsMaxHeat3;
                case 4 -> ArmorConfig.leggingsMaxHeat4;
                default -> 0;
            };
            case BOOTS -> switch (tier) {
                case 1 -> ArmorConfig.bootsMaxHeat1;
                case 2 -> ArmorConfig.bootsMaxHeat2;
                case 3 -> ArmorConfig.bootsMaxHeat3;
                case 4 -> ArmorConfig.bootsMaxHeat4;
                default -> 0;
            };
        };
    }
}
