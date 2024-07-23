package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class WaterElectrolyzerModule extends AbstractPowerModule {

    public static class Ticker extends PlayerTickModule {

        public Ticker(ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
        }

        @Override
        public boolean isAllowed() {
            return super.isAllowed();
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item) {
            super.onPlayerTickInactive(player, level, item);
        }

            @Override
            public void onPlayerTickActive(Player player, Level level, ItemStack item) {
                double energy = ElectricItemUtils.getPlayerEnergy(player);
                double energyConsumption = Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
                if (energy > energyConsumption && player.getAirSupply() < 10) {
                    if ((level.isClientSide()) && NuminaClientConfig.useSounds) {
                        player.playSound(MPSSoundDictionary.SOUND_EVENT_ELECTROLYZER.get(), 1.0f, 1.0f);
                    }
                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
                    player.setAirSupply(300);
                }
            }
    }
}