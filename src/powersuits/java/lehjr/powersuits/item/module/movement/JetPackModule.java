/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.item.module.movement;

import lehjr.numina.config.NuminaSettings;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.util.client.control.PlayerMovementInputWrapper;
import lehjr.numina.util.client.sound.Musique;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.constants.MPSRegistryNames;
import lehjr.powersuits.event.MovementManager;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class JetPackModule extends AbstractPowerModule {
    public JetPackModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.JETPACK_ENERGY, 0, "RF/t");
                addBaseProperty(MPSConstants.JETPACK_THRUST, 0, "N");
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_ENERGY, 15000);
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, 0.16F);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.updateFromNBT();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack torso) {
                if (player.isInWater()) {
                    return;
                }

                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

                ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
                boolean hasFlightControl = helmet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(m->
                        m.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false);
                double jetEnergy = 0;
                double thrust = 0;
                jetEnergy += applyPropertyModifiers(MPSConstants.JETPACK_ENERGY);
                thrust += applyPropertyModifiers(MPSConstants.JETPACK_THRUST);

                if ((jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) &&
                        ((hasFlightControl && thrust > 0) || (playerInput.jumpKey))) {
                        thrust = MovementManager.INSTANCE.thrust(player, thrust, hasFlightControl);

                        if(!player.level.isClientSide()) {
                            if ((player.level.getGameTime() % 5) == 0) {
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy * 5));
                            }
                        } else if (NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JETPACK, SoundCategory.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                        }
                    } else {
                        onPlayerTickInactive(player, torso);
                    }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, ItemStack item) {
                if (player.level.isClientSide && NuminaSettings.useSounds()) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK);
                }
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}