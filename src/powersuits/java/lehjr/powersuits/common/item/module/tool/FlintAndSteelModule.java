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

package lehjr.powersuits.common.item.module.tool;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by User: Andrew2448
 * 10:48 PM 6/11/13
 */
public class FlintAndSteelModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
            }};
            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }


        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            /**
             * Called when this item is used when targetting a Block
             */
            @Override
            public InteractionResult useOn(UseOnContext context) {
//                int energyConsumption = getEnergyUsage();
//                Player player = context.getPlayer();
//                if (ElectricItemUtils.getPlayerEnergy(player) < energyConsumption ) {
//                    return InteractionResult.FAIL;
//                }
//
//                Level world = context.getLevel();
//                BlockPos blockpos = context.func_195995_a();
//                BlockState blockstate = world.getBlockState(blockpos);
//                if (CampfireBlock.func_241470_h_(blockstate)) {
//                    world.func_184133_a(player, blockpos, SoundEvents.field_187649_bu, SoundSource.BLOCKS, 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
//                    world.func_180501_a(blockpos, blockstate.func_206870_a(BlockStateProperties.field_208190_q, Boolean.valueOf(true)), 11);
//                    if (player != null) {
//                        ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
//                    }
//                    return InteractionResult.func_233537_a_(world.isClientSide());
//                } else {
//                    BlockPos blockpos1 = blockpos.func_177972_a(context.func_196000_l());
//                    if (AbstractFireBlock.func_241465_a_(world, blockpos1, context.func_195992_f())) {
//                        world.func_184133_a(player, blockpos1, SoundEvents.field_187649_bu, SoundSource.BLOCKS, 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
//                        BlockState blockstate1 = AbstractFireBlock.func_235326_a_(world, blockpos1);
//                        world.func_180501_a(blockpos1, blockstate1, 11);
//                        ItemStack itemstack = context.func_195996_i();
//                        if (player instanceof ServerPlayer) {
//                            CriteriaTriggers.field_193137_x.func_193173_a((ServerPlayer)player, blockpos1, itemstack);
//                            ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
//                        }
//                        return InteractionResult.func_233537_a_(world.isClientSide());
//                    } else {
                        return InteractionResult.FAIL;
//                    }
//                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}