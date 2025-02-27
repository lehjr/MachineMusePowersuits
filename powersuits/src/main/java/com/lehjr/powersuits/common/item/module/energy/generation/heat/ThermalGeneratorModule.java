package com.lehjr.powersuits.common.item.module.energy.generation.heat;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.HeatUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
    public static class ThermalGeneratorTickingCapability extends PlayerTickModule {
        public ThermalGeneratorTickingCapability(ItemStack module) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY);

//            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
//            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
        }

        @Override
        public boolean isAllowed() {
            return super.isAllowed();
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NotNull ItemStack item) {
            super.onPlayerTickActive(player, level, item);

            double currentHeat = HeatUtils.getPlayerHeat(player);
            double maxHeat = HeatUtils.getPlayerMaxHeat(player);
            if (player.level().getGameTime() % 20 == 0) {
                if (player.isOnFire()) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (4 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
                } else if (currentHeat >= 200) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (2 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
                } else if ((currentHeat / maxHeat) >= 0.5) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) applyPropertyModifiers(MPSConstants.ENERGY_GENERATION), false);
                }
            }
        }
    }
}
