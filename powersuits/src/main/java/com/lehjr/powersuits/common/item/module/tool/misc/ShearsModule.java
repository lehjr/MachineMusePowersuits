package com.lehjr.powersuits.common.item.module.tool.misc;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

// FIXME IShearable ?? pretty much a dead thing now?
public class ShearsModule extends AbstractPowerModule {
//    static final ArrayList<Material> materials =
//            new ArrayList<Material>() {{
//                add(Material.PLANT);
//                add(Material.WATER_PLANT);
//                add(Material.REPLACEABLE_PLANT);
//                add(Material.REPLACEABLE_WATER_PLANT);
//                add(Material.WEB);
//                add(Material.WOOL);
//                add(Material.LEAVES);
//
//            }};


    class BlockBreaker extends RightClickModule implements IBlockBreakingModule {
        public BlockBreaker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target) {
            super(module, category, target);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            addBaseProperty(MPSConstants.HARVEST_SPEED , 8, "x");
        }

        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (level.isClientSide() || state.is(BlockTags.FIRE)) {
                return false;
            }

            if(ElectricItemUtils.getPlayerEnergy(entityLiving) > getEnergyUsage() && getEmulatedTool().isCorrectToolForDrops(state)) {
                getEmulatedTool().mineBlock(level, state, pos, (Player)entityLiving);
                ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @Override
        public @Nonnull ItemStack getEmulatedTool() {
            return new ItemStack(Items.SHEARS);
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED )));
        }

        @Override
        public InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
            if (entity instanceof IShearable target && ElectricItemUtils.getPlayerEnergy(player) > getEnergyUsage()) {
                BlockPos pos = entity.blockPosition();
                if (target.isShearable(player, getEmulatedTool(), entity.level(), pos)) {
                    Level level = entity.level();
                    if (level.isClientSide) {
                        return InteractionResultHolder.success(itemStackIn);
                    }
                    for (ItemStack drop : target.onSheared(player, getEmulatedTool(), level, pos)) {
                        target.spawnShearedDrop(level, pos, drop);
                    }
                    entity.gameEvent(GameEvent.SHEAR, player);
                    ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);
                    return InteractionResultHolder.consume(itemStackIn);
                }

            }

            return InteractionResultHolder.pass(itemStackIn);
        }
    }
}
