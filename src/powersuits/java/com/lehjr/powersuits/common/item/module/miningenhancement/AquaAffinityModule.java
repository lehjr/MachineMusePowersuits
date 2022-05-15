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

package com.lehjr.powersuits.common.item.module.miningenhancement;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.miningenhancement.MiningEnhancement;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;


// Note: tried as an enchantment, but failed to function properly due to how block breaking code works
public class AquaAffinityModule extends AbstractPowerModule {
    public AquaAffinityModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        MiningEnhancement miningEnhancement;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new BlockBreaker(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.AQUA_ENERGY , 0, "FE");
                addBaseProperty(MPSConstants.AQUA_HARVEST_SPEED, 0.2F, "%");
                addTradeoffProperty(MPSConstants.AQUA_POWER, MPSConstants.AQUA_ENERGY , 1000);
                addTradeoffProperty(MPSConstants.AQUA_POWER, MPSConstants.AQUA_HARVEST_SPEED, 0.8F);
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CapabilityPowerModule.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> miningEnhancement));
        }

        class BlockBreaker extends MiningEnhancement implements IBlockBreakingModule {
            public BlockBreaker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean canHarvestBlock(@Nonnull ItemStack stack, BlockState state, Player player, BlockPos pos, int playerEnergy) {
                return false;
            }

            @Override
            public boolean onBlockDestroyed(ItemStack itemStack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (this.canHarvestBlock(itemStack, state, (Player) entityLiving, pos, playerEnergy)) {
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage());
                    return true;
                }
                return false;
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                Player player = event.getPlayer();
                if (event.getNewSpeed() > 1 && (player.isUnderWater() || !player.isOnGround())
                        && ElectricItemUtils.getPlayerEnergy(player) > getEnergyUsage()) {
                    event.setNewSpeed((float) (event.getNewSpeed() * 5 * applyPropertyModifiers(MPSConstants.AQUA_HARVEST_SPEED)));
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.AQUA_ENERGY );
            }

            @Nonnull
            @Override
            public ItemStack getEmulatedTool() {
                return ItemStack.EMPTY; // FIXME?
            }
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return false;
    }
}