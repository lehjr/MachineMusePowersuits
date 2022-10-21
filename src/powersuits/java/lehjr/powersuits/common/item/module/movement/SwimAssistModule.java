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

package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class SwimAssistModule extends AbstractPowerModule {
    public SwimAssistModule() {
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
            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_ENERGY, 1000, "FE");
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, 1, "m/s");
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
            public void onPlayerTickActive(PlayerEntity player, ItemStack itemStack) {
//                if (player.isSwimming()) { // doesn't work when strafing without "swimming"
                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
                if((player.isInWater() && !player.isPassenger()) && (playerInput.strafeKey!=0 || playerInput.forwardKey || playerInput.jumpKey || player.isCrouching())) {
                    double moveRatio = 0;
                    if (playerInput.forwardKey) {
                        moveRatio += 1.0;
                    }
                    if (playerInput.strafeKey != 0) {
                        moveRatio += 1.0;
                    }
                    if (playerInput.jumpKey || player.isCrouching()) {
                        moveRatio += 0.2 * 0.2;
                    }
                    double swimAssistRate = applyPropertyModifiers(MPSConstants.SWIM_BOOST_AMOUNT) * 0.05 * moveRatio;
                    double swimEnergyConsumption = applyPropertyModifiers(MPSConstants.SWIM_ENERGY);

                    int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    if (swimEnergyConsumption < playerEnergy) {
                        if (player.level.isClientSide && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.SWIM_ASSIST, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                        } else if (
                            // every 20 ticks
                                (player.level.getGameTime() % 5) == 0) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (swimEnergyConsumption) * 5);
                        }
                        MovementManager.INSTANCE.thrust(player, swimAssistRate, true);
//                            setMovementModifier(getModuleStack(), swimAssistRate * 100000, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
                    } else {
                        onPlayerTickInactive(player, itemStack);
                    }
                } else {
                    onPlayerTickInactive(player, itemStack);
                }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, @Nonnull ItemStack itemStack) {
                if (player.level.isClientSide && NuminaSettings.useSounds()) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SWIM_ASSIST);
                }
                SprintAssistModule.setMovementModifier(getModuleStack(), 0, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
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