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
        public boolean isAllowed() {
            return MovementModuleConfig.swimAssistModuleIsAllowed;
        }

//        @Override
//        public boolean onPlayerTickActive(Player player, Level level, @Nonnull ItemStack itemStack, int moduleIndex) {
////                if (player.isSwimming()) { // doesn't work when strafing without "swimming"
//            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//            if((player.isInLiquid() && !player.isPassenger())) {
////                && (playerInput.strafeLeftKey ||
////                playerInput.strafeRightKey ||
////                playerInput.forwardKey ||
////                playerInput.reverseKey ||
////                playerInput.jumpKey ||
////                player.isCrouching())) {
//                double moveRatio = 0;
//                if (playerInput.forwardKey) {
//                    moveRatio += 1.0;
//                }
//                if (playerInput.strafeLeftKey || playerInput.strafeRightKey) {
//                    moveRatio += 1.0;
//                }
//                if (playerInput.jumpKey || player.isCrouching()) {
//                    moveRatio += 0.2 * 0.2;
//                }
//
//                if(moveRatio > 0) {
//                    double swimAssistRate = getDesiredThrust() * 0.05 * moveRatio;
//                    double swimEnergyConsumption = getEnergyUsage();
//
//                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
//
//                    if (swimEnergyConsumption < playerEnergy) {
//                        if (level.isClientSide && NuminaClientConfig.useSounds) {
//
//                            // every 20 ticks
//                        } else if ((level.getGameTime() % 5) == 0) {
//                            ElectricItemUtils.drainPlayerEnergy(player, (int) (swimEnergyConsumption) * 5, false);
//                        }
//                        double thrust = MovementManager.thrust(player, swimAssistRate, true);
//
//                        NuminaLogger.logDebug("swim Thrust: " +  getDesiredThrust());
//                        NuminaLogger.logDebug("swim assist rate calcuated: " +  swimAssistRate);
//                        NuminaLogger.logDebug("actual swim thrust to apply: " + thrust);
//
//  //                    SprintAssistModule.setMovementModifier(itemStack, getModule(), swimAssistRate * 100000, MPSConstants.SWIM_SPEED);
//                    } else {
//                        onPlayerTickInactive(player, level, itemStack, moduleIndex);
//                    }
//                }
//            } else {
//                onPlayerTickInactive(player, level, itemStack, moduleIndex);
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack itemStack, int moduleIndex) {
//            stopSound(player, level);
////            SprintAssistModule.setMovementModifier(itemStack, getModule(), 0, MPSConstants.SWIM_SPEED);
//            return false;
//        }

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
