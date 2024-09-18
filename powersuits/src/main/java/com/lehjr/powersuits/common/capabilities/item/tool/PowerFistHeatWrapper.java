package com.lehjr.powersuits.common.capabilities.item.tool;

import com.lehjr.numina.common.capabilities.heat.AbstractModularItemHeatWrapper;
import com.lehjr.powersuits.common.config.PowerFistConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class PowerFistHeatWrapper extends AbstractModularItemHeatWrapper {
    public PowerFistHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        super(itemStack, tier);
    }

    @Override
    public double getBaseMaxHeat(ItemStack itemStack, int tier) {
        if (PowerFistConfig.POWER_FIST_CONFIG_SPEC.isLoaded()) {
            switch (tier) {
                case 1 -> {
                    return PowerFistConfig.powerFistMaxHeat1;
                }
                case 2 -> {
                    return PowerFistConfig.powerFistMaxHeat2;
                }
                case 3 -> {
                    return PowerFistConfig.powerFistMaxHeat3;
                }
                case 4 -> {
                    return PowerFistConfig.powerFistMaxHeat4;
                }
            }
        }
        return 0;
    }
}
