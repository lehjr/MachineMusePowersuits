package com.github.lehjr.powersuits.item.module.tool;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.github.lehjr.numina.util.capabilities.module.powermodule.*;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.helper.ToolHelpers;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.items.CapabilityItemHandler;

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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IBlockBreakingModule blockBreaking;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.blockBreaking = new BlockBreaker(module, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.blockBreaking.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
//            this.blockBreaking.addBaseProperty(MPSConstants.HARVEST_SPEED, 10, "x");
//            this.blockBreaking.addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, 9500);
//            this.blockBreaking.addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, 52);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> blockBreaking));
        }

        class BlockBreaker extends PowerModule implements IBlockBreakingModule {
            public BlockBreaker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean canHarvestBlock(@Nonnull ItemStack powerFist, BlockState state, PlayerEntity player, BlockPos pos, int playerEnergy) {
                AtomicBoolean canHarvest = new AtomicBoolean(false);
                powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(modeChanging -> {
                    if (modeChanging instanceof IModeChangingItem) {
                        ItemStack pickaxeModule = ((IModeChangingItem) modeChanging).getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE_REGNAME);
                        if (!pickaxeModule.isEmpty()) {
                            int energyUsage = pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
                                if (m instanceof IBlockBreakingModule) {
                                    return ((IBlockBreakingModule) m).getEnergyUsage();
                                }
                                return 0;
                            }).orElse(0);
                            canHarvest.set(pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
                                if (m instanceof IBlockBreakingModule) {
                                    return !((IBlockBreakingModule) m).canHarvestBlock(powerFist, state, player, pos, playerEnergy) &&
                                            playerEnergy >= energyUsage && ToolHelpers.isToolEffective(player.getEntityWorld(), pos, getEmulatedTool());
                                }
                                return false;
                            }).orElse(false));
                        }
                    }
                });
                return canHarvest.get();
            }

            @Override
            public boolean onBlockDestroyed(ItemStack powerFist, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (this.canHarvestBlock(powerFist, state, (PlayerEntity) entityLiving, pos, playerEnergy)) {
                    AtomicInteger energyUsage = new AtomicInteger(0);
                    powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(modeChanging -> {
                        if (modeChanging instanceof IModeChangingItem) {
                            ItemStack pickaxeModule = ((IModeChangingItem) modeChanging).getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE_REGNAME);
                            if (!pickaxeModule.isEmpty()) {
                                energyUsage.set(pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
                                    if (m instanceof IBlockBreakingModule) {
                                        return ((IBlockBreakingModule) m).getEnergyUsage();
                                    }
                                    return 0;
                                }).orElse(0));
                            }
                        }
                    });
                    ElectricItemUtils.drainPlayerEnergy((PlayerEntity) entityLiving, energyUsage.get());
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
                PlayerEntity player = event.getPlayer();
                ItemStack powerFist = player.getHeldItemMainhand();
                AtomicDouble newSpeed = new AtomicDouble(event.getNewSpeed());
                powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(modeChanging -> {
                    if (modeChanging instanceof IModeChangingItem) {
                        ItemStack pickaxeModule = ((IModeChangingItem) modeChanging).getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE_REGNAME);
                        if (!pickaxeModule.isEmpty()) {
                            newSpeed.set(newSpeed.get() *
                                    pickaxeModule.getCapability(PowerModuleCapability.POWER_MODULE).map(m ->
                                            m.applyPropertyModifiers(MPSConstants.HARVEST_SPEED)).orElse(1D));
                        }
                    }
                });
                event.setNewSpeed((float) newSpeed.get());
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }
    }
}