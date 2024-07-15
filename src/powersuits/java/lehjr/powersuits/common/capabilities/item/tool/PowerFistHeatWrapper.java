package lehjr.powersuits.common.capabilities.item.tool;

import lehjr.numina.common.capability.heat.AbstractModularItemHeatWrapper;
import lehjr.powersuits.common.config.MPSCommonConfig;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PowerFistHeatWrapper extends AbstractModularItemHeatWrapper {
    public PowerFistHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        super(itemStack, tier);
    }

    @Override
    public double getBaseMaxHeat(ItemStack itemStack, int tier) {
        if (MPSCommonConfig.COMMON_SPEC.isLoaded()) {
            switch (tier) {
                case 1 -> {
                    return MPSCommonConfig.powerFistMaxHeat1;
                }
                case 2 -> {
                    return MPSCommonConfig.powerFistMaxHeat2;
                }
                case 3 -> {
                    return MPSCommonConfig.powerFistMaxHeat3;
                }
                case 4 -> {
                    return MPSCommonConfig.powerFistMaxHeat4;
                }
            }
        }
        return 0;
    }
}
