package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.thruster.IThruster;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.module.MovementModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class JetPackModule extends AbstractPowerModule {
    public static class Toggler extends ToggleableModule implements IThruster {
        public Toggler(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jetPackModuleEnergyConsumptionBase, "RF/t");
            addBaseProperty(MPSConstants.JETPACK_THRUST, MovementModuleConfig.jetPackThrustBase, "N");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jetPackEnergyConsumptionThrustMultiplier, "FE");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, MovementModuleConfig.jetPackThrustMultiplier);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.jetPackModuleIsAllowed;
        }

        @Override
        public double getEnergyUsage() {
            return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public double getDesiredThrust() {
            return applyPropertyModifiers(MPSConstants.JETPACK_THRUST);
        }

        @Override
        public void stopSound(Player player, Level level) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
            }
        }

        @Override
        public void playSound(Player player, Level level, double percentDesiredThrust) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (percentDesiredThrust * 6.25), 1.0f, true);
            }
        }
    }
}
