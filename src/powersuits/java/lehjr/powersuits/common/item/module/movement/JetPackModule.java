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

public class JetPackModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule implements IThruster {
        public Ticker(@Nonnull ItemStack module) {
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
        public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
            if (player.isInWater())
                return false;

            boolean hasFlightControl = false;
            IModularItem modularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (modularItem != null) {
                hasFlightControl = modularItem.isModuleOnline(MPSConstants.FLIGHT_CONTROL_MODULE);
            }

            double jetEnergy = getEnergyUsage();
            double jetPackThrust = applyPropertyModifiers(MPSConstants.JETPACK_THRUST);
            double thrust = 0;

            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            // if player has enough energy to fly
            if (jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) {

                // if already hovering, or if player has flight control installed
                if (hasFlightControl && jetPackThrust > 0) {
                    thrust = MovementManager.thrust(player, jetPackThrust, true);
                    ElectricItemUtils.drainPlayerEnergy(player, (thrust * jetEnergy), false);

                    NuminaLogger.logDebug("jetPack already active");

                    NuminaLogger.logDebug("jetPackThrust: " +  jetPackThrust);
                    NuminaLogger.logDebug("actual thrust to apply: " + thrust);

                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
                    }

                // initiate thrust
                } else if (playerInput.jumpKey && player.getDeltaMovement().y < 0.5) {
                    // max 0.16

                    thrust = MovementManager.thrust(player, jetPackThrust, false);
                    ElectricItemUtils.drainPlayerEnergy(player, (thrust * jetEnergy), false);

                    NuminaLogger.logDebug("jetpacks jump activation ");

                    NuminaLogger.logDebug("jetPackThrust: " +  jetPackThrust);
                    NuminaLogger.logDebug("actual thrust to apply: " + thrust);

                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
                    }

                } else {
                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
                    }
                }

            } else {
                if (level.isClientSide && NuminaClientConfig.useSounds) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
                }
            }
            return false;
        }

//        @Override
//        public boolean onPlayerTickActive(Player player, Level level, ItemStack torso, int moduleIndex) {

//
//            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//            boolean hasFlightControl = false;
//            ItemStack helmet = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD);
//            IModularItem modularItem = helmet.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
//            if(modularItem != null) {
//                hasFlightControl = modularItem.isModuleOnline(MPSConstants.FLIGHT_CONTROL_MODULE);
//            }
//
//            double jetEnergy = 0;
//            double thrust = 0;
//            jetEnergy += applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//            double jetpackThrust = applyPropertyModifiers(MPSConstants.JETPACK_THRUST);
//
//            // todo: change trigger from just jumping to if flyfalling then forwardkey else jump
//
//            if ((jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) &&
//                    ((hasFlightControl && jetpackThrust > 0) || (playerInput.jumpKey))) {
//
//
//                thrust = MovementManager.thrust(player, jetpackThrust, hasFlightControl);
//                ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy), false);
//
//                if(level.isClientSide() && NuminaClientConfig.useSounds) {
//                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
//                }
//
//
//            } else {
//                onPlayerTickInactive(player, level, torso,  moduleIndex);
//            }
//            return false;
//        }

        @Override
        public boolean onPlayerTickInactive(Player player, Level level, ItemStack item,  int moduleIndex) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
            }
            return false;
        }

        @Override
        public double getEnergyUsage() {
            return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public double getDesiredThrust() {
            return applyPropertyModifiers(MPSConstants.JETPACK_THRUST);
        }
    }
}
