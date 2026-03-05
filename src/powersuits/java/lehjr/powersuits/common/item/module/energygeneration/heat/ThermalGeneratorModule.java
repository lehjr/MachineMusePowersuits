package lehjr.powersuits.common.item.module.energygeneration.heat;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
    public static class ThermalGeneratorTickingCapability extends PlayerTickModule {
        final int tier;
        public ThermalGeneratorTickingCapability(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY);
            this.tier = tier;

            ///  FIXME: Passive vs active energy generation (maybe tier based)


            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");



        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NonNull ItemStack item) {
            double currentHeat = HeatUtils.getPlayerHeat(player).currentHeat();
            double maxHeat = HeatUtils.getPlayerHeat(player).maxHeat();
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

        @Override
        public void onPlayerTickInactive(Player player, Level level, @NonNull ItemStack item) {
            super.onPlayerTickInactive(player, level, item);
        }
    }
}
