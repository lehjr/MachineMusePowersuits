package com.github.lehjr.powersuits.item.module.tool;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    public FlintAndSteelModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClick;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClick = new RightClickie(module, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClick.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            /**
             * Called when this item is used when targetting a Block
             */
            @Override
            public ActionResultType onItemUse(ItemUseContext context) {
                int energyConsumption = getEnergyUsage();
                PlayerEntity player = context.getPlayer();
                if (ElectricItemUtils.getPlayerEnergy(player) < energyConsumption ) {
                    return ActionResultType.FAIL;
                }

                World world = context.getWorld();
                BlockPos blockpos = context.getPos();
                BlockState blockstate = world.getBlockState(blockpos);
                if (CampfireBlock.canBeLit(blockstate)) {
                    world.playSound(player, blockpos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                    world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                    if (player != null) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
                    }
                    return ActionResultType.func_233537_a_(world.isRemote());
                } else {
                    BlockPos blockpos1 = blockpos.offset(context.getFace());
                    if (AbstractFireBlock.canLightBlock(world, blockpos1, context.getPlacementHorizontalFacing())) {
                        world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                        BlockState blockstate1 = AbstractFireBlock.getFireForPlacement(world, blockpos1);
                        world.setBlockState(blockpos1, blockstate1, 11);
                        ItemStack itemstack = context.getItem();
                        if (player instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1, itemstack);
                            ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
                        }
                        return ActionResultType.func_233537_a_(world.isRemote());
                    } else {
                        return ActionResultType.FAIL;
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }
    }
}