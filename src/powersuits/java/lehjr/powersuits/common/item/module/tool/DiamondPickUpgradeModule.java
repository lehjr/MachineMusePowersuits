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

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DiamondPickUpgradeModule extends AbstractPowerModule {

    public DiamondPickUpgradeModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final BlockBreaker blockBreaking;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.blockBreaking = new BlockBreaker(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
            }};
//            this.blockBreaking.addBaseProperty(MPSConstants.HARVEST_SPEED, 10, "x");
//            this.blockBreaking.addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.DIAMOND_PICK_ENERGY, 9500);
//            this.blockBreaking.addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, 52);
            powerModuleHolder = LazyOptional.of(() -> blockBreaking);
        }

        class BlockBreaker extends PowerModule implements IBlockBreakingModule {
            public BlockBreaker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean canHarvestBlock(@Nonnull ItemStack powerFist, BlockState state, Player player, BlockPos pos, int playerEnergy) {
                AtomicBoolean canHarvest = new AtomicBoolean(false);
                NuminaLogger.logDebug("FIXME!!!!");
//                powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                        .filter(IModeChangingItem.class::isInstance)
//                        .map(IModeChangingItem.class::cast)
//                        .ifPresent(modeChanging -> {
//                        ItemStack pickaxeModule = modeChanging.getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE);
//                        if (!pickaxeModule.isEmpty()) {
//                            int energyUsage = pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
//                                if (m instanceof IBlockBreakingModule) {
//                                    return ((IBlockBreakingModule) m).getEnergyUsage();
//                                }
//                                return 0;
//                            }).orElse(0);
//                            canHarvest.set(pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
//                                if (m instanceof IBlockBreakingModule) {
//                                    return !((IBlockBreakingModule) m).canHarvestBlock(powerFist, state, player, pos, playerEnergy) &&
//                                            playerEnergy >= energyUsage && ToolHelpers.isToolEffective(player.getCommandSenderWorld(), pos, getEmulatedTool());
//                                }
//                                return false;
//                            }).orElse(false));
//                        }
//                });
                return canHarvest.get();
            }

            @Override
            public boolean mineBlock(@NotNull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                    AtomicInteger energyUsage = new AtomicInteger(0);
                    powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                            .filter(IModeChangingItem.class::isInstance)
                            .map(IModeChangingItem.class::cast)
                            .ifPresent(modeChanging -> {
                                ItemStack pickaxeModule = modeChanging.getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE);
                                if (!pickaxeModule.isEmpty()) {
                                    energyUsage.set(pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE)
                                            .filter(IBlockBreakingModule.class::isInstance)
                                            .map(IBlockBreakingModule.class::cast)
                                            .map(m -> m.getEnergyUsage()).orElse(0));
                                }
                            });
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, energyUsage.get());
                    return true;
                }
                return false;
            }

            @Override
            public ItemStack getEmulatedTool() {
                return new ItemStack(Items.DIAMOND_PICKAXE);
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                Player player = event.getPlayer();
                ItemStack powerFist = player.getMainHandItem();
                AtomicDouble newSpeed = new AtomicDouble(event.getNewSpeed());
                powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modeChanging -> {
                            ItemStack pickaxeModule = modeChanging.getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE);
                            if (!pickaxeModule.isEmpty()) {
                                newSpeed.set(newSpeed.get() *
                                        pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m ->
                                                m.applyPropertyModifiers(MPSConstants.HARVEST_SPEED)).orElse(1D));
                            }
                        });
                event.setNewSpeed((float) newSpeed.get());
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
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}