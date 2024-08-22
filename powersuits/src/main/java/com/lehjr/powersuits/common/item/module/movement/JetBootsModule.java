package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.client.sound.MPSSoundDictionary;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.event.MovementManager;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class JetBootsModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0);
            addBaseProperty(MPSConstants.JETBOOTS_THRUST, 0);
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 750, "FE");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETBOOTS_THRUST, 0.08F);
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, ItemStack item) {
            if (player.isInWater())
                return;

            boolean hasFlightControl = false;
            IModularItem modularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (modularItem != null) {
                hasFlightControl = modularItem.isModuleOnline(MPSConstants.FLIGHT_CONTROL_MODULE);
            }

            double jetEnergy = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            double thrust = applyPropertyModifiers(MPSConstants.JETBOOTS_THRUST);

            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            // if player has enough energy to fly
            if (jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) {
                if (hasFlightControl && thrust > 0) {
                    thrust = MovementManager.thrust(player, thrust, true);
                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
                    }
                    ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy), false);
                } else if (playerInput.jumpKey && player.getDeltaMovement().y < 0.5) {
                    thrust = MovementManager.thrust(player, thrust, false);
                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
                    }
                    ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy), false);
                } else {
                    if ((level.isClientSide) && NuminaClientConfig.useSounds) {
                        Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
                    }
                }
            } else {
                if (level.isClientSide && NuminaClientConfig.useSounds) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
                }
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, ItemStack item) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
            }
        }
    }
}