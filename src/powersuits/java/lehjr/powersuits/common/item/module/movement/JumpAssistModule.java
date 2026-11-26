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

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.config.module.MovementModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JumpAssistModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module) {
                super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionBase, "FE");
                addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionPowerMultiplier);
                addBaseProperty(MPSConstants.JUMP_BOOST, MovementModuleConfig.jumpAssistModuleJumpBoostBase, "%");
                addTradeoffProperty(MPSConstants.POWER, MPSConstants.JUMP_BOOST, MovementModuleConfig.jumpAssistModuleJumpBoostPowerMultiplier);
                addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionCompensationMultiplier);
                addBaseProperty(MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.jumpAssistModuleExhaustionCompensationBase, "%");
                addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.jumpAssistModuleExhaustionCompensationCompensationMultiplier);
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
                return MovementModuleConfig.jumpAssistModuleIsAllowed;
            }


            @Override
            public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
                IPlayerKeyStates keyStates = player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).resolve().orElse(null);
                if (keyStates != null && keyStates.getJumpKeyState()) {
                    double multiplier = MovementManager.INSTANCE.getPlayerJumpMultiplier(player);
                    if (multiplier > 0) {
                        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.15 * Math.min(multiplier, 1), 0));
                        MovementManager.INSTANCE.setPlayerJumpTicks(player, multiplier - 1);
                    }
                    PlayerUtils.resetFloatKickTicks(player);
//                player.getAbilities().setFlyingSpeed(player.getSpeed() * .2f); // FixMe? What was this for?
                } else {
                    MovementManager.INSTANCE.setPlayerJumpTicks(player, 0);
                }
                PlayerUtils.resetFloatKickTicks(player);
                return false;
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}