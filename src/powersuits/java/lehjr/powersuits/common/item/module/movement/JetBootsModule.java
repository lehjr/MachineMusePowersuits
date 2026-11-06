package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.module.MovementModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class JetBootsModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule implements IThruster {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jetBootsModuleEnergyConsumptionBase, "RF/t");
            addBaseProperty(MPSConstants.JETBOOTS_THRUST, MovementModuleConfig.jetBootsThrustBase, "N");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jetBootsEnergyConsumptionThrustMultiplier, "FE");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETBOOTS_THRUST, MovementModuleConfig.jetBootsThrustMultiplier);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.jetBootsModuleIsAllowed;
        }

        @Override
        public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
            if (player.isInWater())
                return false;

            boolean hasFlightControl = false;
            IModularItem modularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (modularItem != null) {
                hasFlightControl = modularItem.isModuleOnline(MPSConstants.FLIGHT_CONTROL_MODULE);
            }

            double jetEnergy = getEnergyUsage();
            double jetbootsThrust = getDesiredThrust();
            double thrust = 0;

            NuminaLogger.logDebug("---------------------------------------------------------------------------------------");

            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            // if player has enough energy to fly
            if (jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) {
                // hover if flight control installed and thrust setting more than 0
                if (hasFlightControl && jetbootsThrust > 0) {
                    // handle this in movemement manager... check for glider being active and
                    // does playerInput.forwardKey && player.isFlyFalling() fit here?

                    thrust = MovementManager.thrust(player, jetbootsThrust, true);

                    NuminaLogger.logDebug("jetboots active by flight control");

                    NuminaLogger.logDebug("jetbootsThrust: " +  jetbootsThrust);
                    NuminaLogger.logDebug("actual thrust to apply: " + thrust);

                // if jumping
                } else if (playerInput.jumpKey /*&& player.getDeltaMovement().y < 0.5 */) {
                    // getting thrust for client side here just for sound



                    if(level.isClientSide() || player.getDeltaMovement().y < 0.5) {
                        thrust = MovementManager.thrust(player, jetbootsThrust, false);
                    } else if (!(player.getDeltaMovement().y < 0.5)) {
                        NuminaLogger.logDebug("jetboots y movement already too fast: " + player.getDeltaMovement().y);
                    }



                    NuminaLogger.logDebug("jetboots jump activation ");

                    NuminaLogger.logDebug("jetbootsThrust: " +  jetbootsThrust);
                    NuminaLogger.logDebug("actual thrust to apply: " + thrust);


//                } else if(playerInput.jumpKey) {
                    NuminaLogger.logDebug("jumpkey is true, player.getDeltaMovement().y: " + player.getDeltaMovement().y);
                } else {
                    NuminaLogger.logDebug("jetboots no activation ");
                }
            }





            if(level.isClientSide && NuminaClientConfig.useSounds) {
                NuminaLogger.logDebug("jetboots thrust client side: " +  thrust);

                if(thrust > 0 ) {
                    NuminaLogger.logDebug("should PLAY sound");
                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
                } else {
                    NuminaLogger.logDebug("should STOP sound");

                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
                }
            } else {
                if(thrust > 0 ) {
                    ElectricItemUtils.drainPlayerEnergy(player, jetEnergy * getDesiredThrust()/thrust, false);
                }
            }

            NuminaLogger.logDebug("====================================================================================");
            return false;
        }

        @Override
        public boolean onPlayerTickInactive(Player player, Level level, ItemStack item,  int moduleIndex) {
            stopSound(player, level);
            return false;
        }


        @Override
        public double getEnergyUsage() {
            return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public double getDesiredThrust() {
            return applyPropertyModifiers(MPSConstants.JETBOOTS_THRUST);
        }

        @Override
        public void stopSound(Player player, Level level) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
            }
        }

        @Override
        public void playSound(Player player, Level level, double percentDesiredThrust) {

        }
    }
}
