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

// FixMe
public class SwimAssistModule extends AbstractPowerModule {
    // NeoForgeMod.SWIM_SPEED


    public static class Toggler extends ToggleableModule implements IThruster {
        public Toggler(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.swimAssistModuleEnergyConsumptionThrustMultiplier, "FE");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, MovementModuleConfig.swimAssistModuleThrustBoostMultiplier, "m/s");
        }

        @Override
        public int getTier() {
            return 1;
        }

        @Override
        public String getModuleGroup() {
            return "";
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.swimAssistModuleIsAllowed;
        }


        @Override
        public double getEnergyUsage() {
            return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public double getDesiredThrust() {
            return applyPropertyModifiers(MPSConstants.SWIM_BOOST_AMOUNT);
        }

        @Override
        public void playSound(Player player, Level level, double percentDesiredThrust) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get(), SoundSource.PLAYERS, 1.0f, 1.0f, true);
            }
        }

        @Override
        public void stopSound(Player player, Level level) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get());
            }
        }
    }
}
